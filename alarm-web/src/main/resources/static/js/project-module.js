var search_select="";
var search_input="";

$(function() {
    // project add modal
    $("#project-add").click(function () {
        var project_name = $("#project-name").val();
        if (project_name.trim().length == 0) {
            alert("请输入项目名称");
            return;
        }
        addProject(project_name);
    });

    // project delete modal
    $("#project-del").click(function() {
        var project_id = $(this).attr("_val");
        if(project_id == undefined) {
            console.log("del project fail, project_id=" + project_id);
            return;
        }
        deleteProject(project_id);
    });

    // module add
    $("#module-add").click(function() {
        var module_name = $("#module-name").val();
        if(module_name.trim().length == 0) {
            alert("请输入模块名称");
            return;
        }
        var project_id = $("#module-add").attr("_pid");
        if(project_id == undefined) {
            console.log("add module fail, project_id=" + project_id);
            return;
        }
        addModule(project_id, module_name);
    });

    // module delete modal
    $("#module-del").click(function() {
        var module_id = $(this).attr("_val");
        if(module_id == undefined) {
            console.log("del module fail, module_id=" + module_id);
            return;
        }
        deleteModule(module_id);
    });

    // project member add
    $("#project-member-add").click(function () {
        var user_id = $("#project-member").val();
        var project_id = $("#project-member-add").attr("_pid");
        if (project_id == undefined || user_id == undefined) {
            console.log("add project member fail, project_id=" + project_id + ", user_id=" + user_id);
            return;
        }
        addUserToProject(user_id, project_id);
    });

    // project member delete
    $("#project-member-del").click(function () {
        var user_id = $(this).attr("_val");
        var project_id = $("#project-member-add").attr("_pid");
        if (project_id == undefined || user_id == undefined) {
            console.log("del project member fail, project_id=" + project_id + ", user_id=" + user_id);
            return;
        }
        removeUserFromProject(user_id, project_id);
    });

    // init data table
    getProjects();

});

function getProjects(search) {
    $.ajax({
        url: "/projects?page_no="+current_page+"&page_size="+page_length+"&"+search,
        type: "GET",
        success: function(data){
            if(data["code"] == 2000) {
                // project
                var html = "";
                var projects = data["projects"];
                for(var i = 0; i < projects.length; i++) {
                    var project  = projects[i];
                    if (i == 0) {
                        $("#module-add").attr("_pid", project["id"]);
                        $("#project-member-add").attr("_pid", project["id"]);
                        html += "<tr class='project' _val='"+project["id"]+"' style='background-color:#eee'>"
                            + "<td><div>"+project["name"]+""
                            + "<button class='btn btn-danger project-del' data-toggle='modal' data-target='#modal-project-del' "
                            + "_val='"+project["id"]+"' style='float:right;margin:0;padding:0;width:26px;'>X</button>"
                            + "</div></td></tr>";
                    } else {
                        html += "<tr class='project' _val='"+project["id"]+"'>"
                            + "<td><div>"+project["name"]+""
                            + "<button class='btn btn-danger project-del' data-toggle='modal' data-target='#modal-project-del' "
                            + "_val='"+project["id"]+"' style='float:right;margin:0;padding:0;width:26px;'>X</button>"
                            + "</div></td></tr>";
                    }
                }
                $(".projects-body").html(html);
                setProjectClickEvent();
                var page_count = parseInt((data["count"] + page_length - 1)/page_length);
                $(".page-footer").html(setPageButton(page_count, current_page));
                setPageBtnClick();
                setTableTotalSize(data["count"]);

                // module
                html = "";
                var modules = data["modules"];
                for(var i = 0; i < modules.length; i++) {
                    var module  = modules[i];
                    html += "<tr class='module' _val='"+module["id"]+"'>"
                        + "<td><div>"+module["name"]+""
                        + "<button class='btn btn-danger module-del' data-toggle='modal' data-target='#modal-module-del' "
                        + "_val='"+module["id"]+"' style='float:right;margin:0;padding:0;width:26px;'>X</button>"
                        + "</div></td></tr>";
                }
                $(".modules-body").html(html);
                $(".module-del").click(function() {
                    $("#module-del").attr("_val", $(this).attr("_val"));
                });

                // project member
                html = "";
                var members = data["project_members"];
                for(var i = 0; i < members.length; i++) {
                    var member  = members[i];
                    html += "<tr class='member' _val='"+member["id"]+"'>"
                        + "<td><div>"+member["name"]+"("+member["username"]+")"
                        + "<button class='btn btn-danger member-del' data-toggle='modal' data-target='#modal-member-del' "
                        + "_val='"+member["id"]+"' style='float:right;margin:0;padding:0;width:26px;'>X</button>"
                        + "</div></td></tr>";
                }
                $(".members-body").html(html);
                $(".member-del").click(function() {
                    $("#project-member-del").attr("_val", $(this).attr("_val"));
                });

                // all user
                var all_user = data["all_user"];
                var userList = [];
                for (var i = 0; i < all_user.length; i++) {
                    userList.push({id: all_user[i]["id"], text: all_user[i]["name"]+"("+all_user[i]["username"]+")"});
                }
                $("#project-member").select2({
                    data: userList
                })
            }
        }
    });
}

function setProjectClickEvent() {
    $(".project").click(function () {
        $(".project").css("background-color", "#fff");
        $(this).css("background-color", "#eee");
        var project_id = $(this).attr("_val");
        $("#module-add").attr("_pid", project_id);
        $("#project-member-add").attr("_pid", project_id);
        getModuleByProjectId(project_id);
        getMemberByProjectId(project_id);
    });
    $(".project-del").click(function() {
        $("#project-del").attr("_val", $(this).attr("_val"));
    });
}

function getModuleByProjectId(pid) {
    $.ajax({
        url: "/projects/"+pid+"/modules",
        type: "GET",
        success: function(data){
            if(data["code"] == 2000) {
                var html = "";
                var modules = data["modules"];
                for(var i = 0; i < modules.length; i++) {
                    var module  = modules[i];
                    html += "<tr class='module' _val='"+module["id"]+"'>"
                        + "<td><div>"+module["name"]+""
                        + "<button class='btn btn-danger module-del' data-toggle='modal' data-target='#modal-module-del' "
                        + "_val='"+module["id"]+"' style='float:right;margin:0;padding:0;width:26px;'>X</button>"
                        + "</div></td></tr>";
                }
                $(".modules-body").html(html);
                $(".module-del").click(function() {
                    $("#module-del").attr("_val", $(this).attr("_val"));
                });
            }
        }
    });
}

function getMemberByProjectId(pid) {
    $.ajax({
        url: "/projects/"+pid+"/members",
        type: "GET",
        success: function(data){
            if(data["code"] == 2000) {
                var html = "";
                var members = data["members"];
                for(var i = 0; i < members.length; i++) {
                    var member  = members[i];
                    html += "<tr class='member' _val='"+member["id"]+"'>"
                        + "<td><div>"+member["name"]+"("+member["username"]+")"
                        + "<button class='btn btn-danger member-del' data-toggle='modal' data-target='#modal-member-del' "
                        + "_val='"+member["id"]+"' style='float:right;margin:0;padding:0;width:26px;'>X</button>"
                        + "</div></td></tr>";
                }
                $(".members-body").html(html);
                $(".member-del").click(function() {
                    $("#project-member-del").attr("_val", $(this).attr("_val"));
                });
            }
        }
    });
}

function addProject(name) {
    $.ajax({
        url: "/projects/"+name,
        type: "POST",
        data: JSON.stringify({}),
        dataType: "json",
        contentType: "application/json;charset=utf-8",
        success: function(data){
            if (data["code"] != 2000) {
                alert("创建失败");
            }
            location.replace(location.href);
        }
    });
}

function deleteProject(pid) {
    $.ajax({
        url: "/projects/"+pid,
        type: "DELETE",
        data: JSON.stringify({}),
        dataType: "json",
        contentType: "application/json;charset=utf-8",
        success: function(data){
            if(data["code"] != 2000) {
                alert("删除失败");
            }
            location.replace(location.href);
        }
    });
}

function addModule(pid, name) {
    $.ajax({
        url: "/projects/"+pid+"/modules/"+name,
        type: "POST",
        data: JSON.stringify({}),
        dataType: "json",
        contentType: "application/json;charset=utf-8",
        success: function(data){
            if (data["code"] != 2000) {
                alert("创建失败");
            }
            location.replace(location.href);
        }
    });
}

function deleteModule(mid) {
    $.ajax({
        url: "/modules/"+mid,
        type: "DELETE",
        data: JSON.stringify({}),
        dataType: "json",
        contentType: "application/json;charset=utf-8",
        success: function(data){
            if(data["code"] != 2000) {
                alert("删除失败");
            }
            location.replace(location.href);
        }
    });
}

function addUserToProject(uid, pid) {
    $.ajax({
        url: "/users/"+uid+"/to/projects/"+pid,
        type: "POST",
        data: JSON.stringify({}),
        dataType: "json",
        contentType: "application/json;charset=utf-8",
        success: function(data){
            if (data["code"] != 2000) {
                alert("创建失败");
            }
            getProjects();
            //location.replace(location.href);
        }
    });
}

function removeUserFromProject(uid, pid) {
    $.ajax({
        url: "/users/"+uid+"/from/projects/"+pid,
        type: "DELETE",
        data: JSON.stringify({}),
        dataType: "json",
        contentType: "application/json;charset=utf-8",
        success: function(data){
            if(data["code"] != 2000) {
                alert("删除失败");
            }
            getProjects();
            //location.replace(location.href);
        }
    });
}

function setPageBtnClick() {
    $(".page-btn").click(function () {
        current_page = $(this).attr("_val");
        getProjects(search_select + "=" + search_input);
    });
}

function setTableTotalSize(count) {
    if (count == null || count == undefined) count = 0;
    $(".table-total-size").text("共有 "+count+" 条数据");
}