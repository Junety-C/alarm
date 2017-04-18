
var group_mapper = {"0":""};

$(function() {

    // group create modal
    $("#group-create").click(function () {
        var group_name = $("#group-name").val();
        if(group_name.trim().length == 0) {
            alert("请输入接收组名称");
            return;
        }
        if(group_name.length > 64) {
            alert("接收组名称过长");
            return;
        }
        var project_id = $("#current-id").attr("_pid");
        if(project_id == undefined) {
            console.log("create group fail, project_id=" + project_id + ", group_name=" + group_name);
            return;
        }
        createGroup(project_id, group_name);
    });

    // group delete modal
    $("#group-del").click(function () {
        var project_id = $("#current-id").attr("_pid");
        var group_id = $("#current-id").attr("_gid");
        if(project_id == undefined || group_id == undefined) {
            console.log("delete group fail, project_id=" + project_id + ", group_id=" + group_id);
            return;
        }
        deleteGroup(project_id, group_id);
    });

    // member add
    $("#member-add").click(function() {
        var member_account = $("#member-account").val();
        if(member_account.trim().length == 0) {
            alert("请输入用户账号");
            return;
        }
        var project_id = $("#current-id").attr("_pid");
        var group_id = $("#current-id").attr("_gid");
        if(project_id == undefined || group_id == undefined || group_id == 0) {
            console.log("add group member fail, project_id=" + project_id + ", group_id=" + group_id);
            return;
        }
        addGroupMember(project_id, group_id, member_account);
    });

    // member delete modal
    $("#member-del").click(function() {
        var project_id = $("#current-id").attr("_pid");
        var group_id = $("#current-id").attr("_gid");
        var user_id = $("#current-id").attr("_uid");
        if(project_id == undefined || group_id == undefined || user_id == undefined) {
            console.log("remove group member fail, project_id=" + project_id + ", group_id" + group_id + ", user_id=" + user_id);
            return;
        }
        removeGroupMember(project_id, group_id, user_id);
    });

    // init data table
    initGroupTable();

});

function initGroupTable() {
    $.ajax({
        url: "/groups?page_no="+current_page+"&page_size="+page_length,
        type: "GET",
        success: function(data){
            if(data["code"] == 2000) {
                // project list
                var i, html = "";
                var project_list = data["project_list"];
                for(i = 0; i < project_list.length; i++) {
                    var project  = project_list[i];
                    if (i == 0) {
                        $("#current-id").attr("_pid", project["id"]);
                        html += "<tr class='project' _pid='"+project["id"]+"' style='background-color:#eee'>"
                            + "<td>"+project["name"]+"</td></tr>";
                    } else {
                        html += "<tr class='project' _pid='"+project["id"]+"'>"
                            + "<td>"+project["name"]+"</td></tr>";
                    }
                }
                $(".project-list").html(html);
                setProjectClickEvent();
                var page_count = parseInt((data["project_count"] + page_length - 1)/page_length);
                $(".page-footer").html(setPageButton(page_count, current_page));
                setPageBtnClick();
                setTableTotalSize(data["project_count"]);

                // group list
                $("#member-list-title").text("接收人列表（）");
                $("#current-id").attr("_gid", 0);
                var group_list = data["group_list"];
                var groupList = [];
                groupList.push({id:0, text:'请选择'});
                for (i = 0; i < group_list.length; i++) {
                    var group = group_list[i];
                    group_mapper[group["id"]] = group["name"];
                    groupList.push({id: group["id"], text: group["name"]});
                }
                $("#group-list").html("").select2({
                    data: groupList
                });
                addGroupSelectEvent();
                $(".member-list").html("");
            }
        }
    });
}

function setProjectClickEvent() {
    $(".project").click(function () {
        $(".project").css("background-color", "#fff");
        $(this).css("background-color", "#eee");
        var project_id = $(this).attr("_pid");
        $("#current-id").attr("_pid", project_id);
        getGroupByProjectId(project_id);
    });
}

function getGroupByProjectId(project_id) {
    $.ajax({
        url: "/projects/"+project_id+"/groups",
        type: "GET",
        success: function(data){
            if(data["code"] == 2000) {
                // group list
                $("#member-list-title").text("接收人列表（）");
                $("#current-id").attr("_gid", 0);
                var group_list = data["group_list"];
                var groupList = [];
                groupList.push({id:0, text:'请选择'});
                for (var i = 0; i < group_list.length; i++) {
                    var group = group_list[i];
                    group_mapper[group["id"]] = group["name"];
                    groupList.push({id: group["id"], text: group["name"]});
                }
                $("#group-list").html("").select2({
                    data: groupList
                });
                addGroupSelectEvent();
                $(".member-list").html("");
            }
        }
    });
}

function createGroup(project_id, group_name) {
    $.ajax({
        url: "/projects/"+project_id+"/groups/"+group_name,
        type: "POST",
        data: JSON.stringify({}),
        dataType: "json",
        contentType: "application/json;charset=utf-8",
        success: function(data){
            if (data["code"] != 2000) {
                alert("创建接收组失败");
                return;
            }
            $("#group-name").val("");
            initGroupTable();
        }
    });
}

function deleteGroup(project_id, group_id) {
    $.ajax({
        url: "/projects/"+project_id+"/groups/"+group_id,
        type: "DELETE",
        data: JSON.stringify({}),
        dataType: "json",
        contentType: "application/json;charset=utf-8",
        success: function(data){
            if(data["code"] != 2000) {
                alert("删除接收组失败");
                return;
            }
            initGroupTable();
        }
    });
}

function addGroupMember(project_id, group_id, member_account) {
    $.ajax({
        url: "/projects/"+project_id+"/groups/"+group_id+"/members/"+member_account,
        type: "POST",
        data: JSON.stringify({}),
        dataType: "json",
        contentType: "application/json;charset=utf-8",
        success: function(data){
            if (data["code"] != 2000) {
                alert("添加项目成员失败，原因:" + data["reason"]);
                return;
            }
            $("#member-account").val("");
            initGroupTable();
        }
    });
}

function removeGroupMember(project_id, group_id, user_id) {
    $.ajax({
        url: "/projects/"+project_id+"/groups/"+group_id+"/members/"+user_id,
        type: "DELETE",
        data: JSON.stringify({}),
        dataType: "json",
        contentType: "application/json;charset=utf-8",
        success: function(data){
            if(data["code"] != 2000) {
                alert("移除项目成员失败");
                return;
            }
            initGroupTable();
        }
    });
}

function getGroupMemberByGroupId(project_id, group_id) {
    $.ajax({
        url: "/projects/"+project_id+"/groups/"+group_id+"/members",
        type: "GET",
        success: function(data){
            if(data["code"] == 2000) {
                var i, html = "";
                // member list
                var member_list = data["member_list"];
                for(i = 0; i < member_list.length; i++) {
                    var member  = member_list[i];
                    html += "<tr><td><div>"+member["name"];
                    if (data["permission_type"] == 0 || data["user"]["type"] == 0) {
                        html += "<button class='btn btn-danger member-del' data-toggle='modal' data-target='#modal-member-del' "
                            + "_uid='"+member["id"]+"' style='float:right;margin:0;padding:0;width:26px;'>X</button>";
                    }
                    html += "</div></td></tr>";
                }
                $(".member-list").html(html);
                $(".member-del").click(function() {
                    $("#current-id").attr("_uid", $(this).attr("_uid"));
                });
            }
        }
    });
}

function addGroupSelectEvent() {
    // get group member group change
    $("#group-list").change(function() {
        var project_id = $("#current-id").attr("_pid");
        var group_id = $("#group-list").val();
        $("#current-id").attr("_gid", group_id);
        $("#member-list-title").text("接收人列表（"+group_mapper[group_id]+"）");

        if (project_id == undefined || group_id == undefined) {
            console.log("get group member fail, project_id=" + project_id + ", group_id=" + group_id);
            return;
        }
        if (group_id == 0) {
            $(".member-list").html("");
            return;
        }
        getGroupMemberByGroupId(project_id, group_id);
    });
}

function setPageBtnClick() {
    $(".page-btn").click(function () {
        current_page = $(this).attr("_val");
        initGroupTable();
    });
}

function setTableTotalSize(count) {
    if (count == null || count == undefined) count = 0;
    $(".table-total-size").text("共有 "+count+" 条数据");
}