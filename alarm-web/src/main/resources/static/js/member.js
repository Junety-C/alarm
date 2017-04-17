
$(function() {

    // member add
    $("#member-add").click(function() {
        var user_account = $("#user-account").val();
        if(user_account.trim().length == 0) {
            alert("请输入用户账号");
            return;
        }
        var project_id = $("#current-id").attr("_pid");
        if(project_id == undefined) {
            console.log("add project member fail, project_id=" + project_id + ", user_account=" + user_account);
            return;
        }
        addProjectMember(project_id, user_account);
    });

    // member delete modal
    $("#member-del").click(function() {
        var user_id = $("#current-id").attr("_uid");
        if(user_id == undefined) {
            console.log("remove project member fail, user_id=" + user_id);
            return;
        }
        var project_id = $("#current-id").attr("_pid");
        if(project_id == undefined) {
            console.log("remove project member fail, project_id=" + project_id + ", user_id=" + user_id);
            return;
        }
        removeProjectMember(project_id, user_id);
    });

    // init data table
    initMemberTable();

});

function initMemberTable() {
    $.ajax({
        url: "/members?page_no="+current_page+"&page_size="+page_length,
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
                        $("#member-list-title").text("成员列表（"+project["name"]+"）");
                        html += "<tr class='project' _pid='"+project["id"]+"' _pname='"+project["name"]+"' style='background-color:#eee'>"
                            + "<td>"+project["name"]+"</td></tr>";
                    } else {
                        html += "<tr class='project' _pid='"+project["id"]+"' _pname='"+project["name"]+"'>"
                            + "<td>"+project["name"]+"</td></tr>";
                    }
                }
                $(".project-list").html(html);
                setProjectClickEvent();
                var page_count = parseInt((data["project_count"] + page_length - 1)/page_length);
                $(".page-footer").html(setPageButton(page_count, current_page));
                setPageBtnClick();
                setTableTotalSize(data["project_count"]);

                // default member list
                html = "";
                var member_list = data["member_list"];
                for(i = 0; i < member_list.length; i++) {
                    var member  = member_list[i];
                    html += "<tr><td><div>"+member["name"];
                    if (member["type"] == 0) html += "（管理员）";
                    else html += "（成员）";
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

function setProjectClickEvent() {
    $(".project").click(function () {
        $(".project").css("background-color", "#fff");
        $(this).css("background-color", "#eee");
        var project_id = $(this).attr("_pid");
        $("#current-id").attr("_pid", project_id);
        var project_name = $(this).attr("_pname");
        $("#member-list-title").text("成员列表（"+project_name+"）");
        getMemberByProjectId(project_id);
    });
}

function getMemberByProjectId(pid) {
    $.ajax({
        url: "/projects/"+pid+"/members",
        type: "GET",
        success: function(data){
            if(data["code"] == 2000) {
                var i, html = "";
                var member_list = data["member_list"];
                for(i = 0; i < member_list.length; i++) {
                    var member  = member_list[i];
                    html += "<tr><td><div>"+member["name"];
                    if (member["type"] == 0) html += "（管理员）";
                    else html += "（成员）";
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

function addProjectMember(project_id, account) {
    $.ajax({
        url: "/projects/"+project_id+"/members/"+account,
        type: "POST",
        data: JSON.stringify({}),
        dataType: "json",
        contentType: "application/json;charset=utf-8",
        success: function(data){
            if (data["code"] != 2000) {
                alert("添加项目成员失败，原因:" + data["reason"]);
                return;
            }
            $("#user-account").val("");
            initMemberTable();
        }
    });
}

function removeProjectMember(project_id, user_id) {
    $.ajax({
        url: "/project/"+project_id+"/members/"+user_id,
        type: "DELETE",
        data: JSON.stringify({}),
        dataType: "json",
        contentType: "application/json;charset=utf-8",
        success: function(data){
            if(data["code"] != 2000) {
                alert("移除项目成员失败");
                return;
            }
            initMemberTable();
        }
    });
}

function setPageBtnClick() {
    $(".page-btn").click(function () {
        current_page = $(this).attr("_val");
        initMemberTable();
    });
}

function setTableTotalSize(count) {
    if (count == null || count == undefined) count = 0;
    $(".table-total-size").text("共有 "+count+" 条数据");
}