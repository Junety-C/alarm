
$(function() {

    // module create modal
    $("#module-create").click(function() {
        var module_name = $("#module-name").val();
        if(module_name.trim().length == 0) {
            alert("请输入模块名称");
            return;
        }
        var project_id = $("#current-id").attr("_pid");
        if(project_id == undefined) {
            console.log("create module fail, project_id=" + project_id + ", module_name=" + module_name);
            return;
        }
        var module_data = {
            projectId: project_id,
            name: module_name
        };
        createModule(module_data);
    });

    // module delete modal
    $("#module-del").click(function() {
        var module_id = $("#current-id").attr("_mid");
        if(module_id == undefined) {
            console.log("del module fail, module_id=" + module_id);
            return;
        }
        deleteModule(module_id);
    });

    // init data table
    initModuleTable();

});

function initModuleTable() {
    $.ajax({
        url: "/modules?page_no="+current_page+"&page_size="+page_length,
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
                        $("#module-list-title").text("模块列表（"+project["name"]+"）");
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

                // default module list
                html = "";
                var module_list = data["module_list"];
                for(i = 0; i < module_list.length; i++) {
                    var module  = module_list[i];
                    html += "<tr><td><div>"+module["name"];
                    // if (data["permission_type"] == 0 || data["user"]["type"] == 0) {
                        html += "<button class='btn btn-danger module-del' data-toggle='modal' data-target='#modal-module-del' "
                            + "_mid='"+module["id"]+"' style='float:right;margin:0;padding:0;width:26px;'>X</button>";
                    // }
                    html += "</div></td></tr>";
                }
                $(".module-list").html(html);
                $(".module-del").click(function() {
                    $("#current-id").attr("_mid", $(this).attr("_mid"));
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
        $("#module-list-title").text("模块列表（"+project_name+"）");
        getModuleByProjectId(project_id);
    });
}

function getModuleByProjectId(pid) {
    $.ajax({
        url: "/projects/"+pid+"/modules",
        type: "GET",
        success: function(data){
            if(data["code"] == 2000) {
                var i, html = "";
                var module_list = data["module_list"];
                for(i = 0; i < module_list.length; i++) {
                    var module  = module_list[i];
                    html += "<tr><td><div>"+module["name"];
                    // if (data["permission_type"] == 0 || data["user"]["type"] == 0) {
                        html += "<button class='btn btn-danger module-del' data-toggle='modal' data-target='#modal-module-del' "
                            + "_mid='"+module["id"]+"' style='float:right;margin:0;padding:0;width:26px;'>X</button>";
                    // }
                    html += "</div></td></tr>";
                }
                $(".module-list").html(html);
                $(".module-del").click(function() {
                    $("#current-id").attr("_mid", $(this).attr("_mid"));
                });
            }
        }
    });
}

function createModule(module_data) {
    $.ajax({
        url: "/modules",
        type: "POST",
        data: JSON.stringify(module_data),
        dataType: "json",
        contentType: "application/json;charset=utf-8",
        success: function(data){
            if (data["code"] != 2000) {
                alert("添加模块失败，原因:"+data["reason"]);
                return;
            }
            $("#module-name").val("");
            getModuleByProjectId($("#current-id").attr("_pid"));
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
                alert("删除模块失败");
                return;
            }
            getModuleByProjectId($("#current-id").attr("_pid"));
        }
    });
}

function setPageBtnClick() {
    $(".page-btn").click(function () {
        current_page = $(this).attr("_val");
        initModuleTable();
    });
}

function setTableTotalSize(count) {
    if (count == null || count == undefined) count = 0;
    $(".table-total-size").text("共有 "+count+" 条数据");
}