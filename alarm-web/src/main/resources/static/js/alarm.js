var search_select = "";
var search_input = "";
var regex = /^\+?[1-9][0-9]*$/;

$(function() {
    // data search
    $("#search-submit").click(function() {
        search_select = $("#search-select option:selected").attr("_val");
        search_input = $("#search-input").val();
        getAlarmList(search_select + "=" + search_input);
    });

    // alarm-modal init
    $("#alarm-modal").click(function() {
        getProjectInfo();
    });

    // get module list when project change
    $("#alarm-project").change(function() {
        var project_id = $(this).val();
        if (project_id == 0) {
            return;
        }
        getModuleAndGroupByProjectId(project_id, "#alarm-module", "#alarm-group");
    });

    // create alarm
    $("#alarm-create").click(function() {
        var code = $("#alarm-code").val();
        if (code == null || !regex.test(code)) {
            alert("告警代号不合法");
            return;
        }
        var name = $("#alarm-name").val();
        if (name == null || name.trim().length == 0) {
            alert("请输入告警名称");
            return;
        }
        var project_id = $("#alarm-project").val();
        if (project_id == 0) {
            alert("请选择项目");
            return;
        }
        var module_id = $("#alarm-module").val();
        if (module_id == 0) {
            alert("请选择项目模块");
            return;
        }
        var group_id = $("#alarm-group").val();
        if (group_id == 0) {
            alert("请选择接收组");
            return;
        }
        var route_key = $("#alarm-route-key").val();
        var config = {};
        $("input[name=config]").each(function() {
            config[$(this).val()] = $(this).prop("checked");
        });
        config["debug_interval"] = parseInt($("#debug-interval").val());
        config["debug_times"] = parseInt($("#debug-times").val());
        config["info_interval"] = parseInt($("#info-interval").val());
        config["info_times"] = parseInt($("#info-times").val());
        config["error_interval"] = parseInt($("#error-interval").val());
        config["error_times"] = parseInt($("#error-times").val());

        var alarm_data = {
            code: code,
            name: name,
            projectId: project_id,
            moduleId: module_id,
            groupId: group_id,
            routeKey: route_key,
            config: JSON.stringify(config)
        };
        createAlarm(alarm_data);
    });

    $("#alarm-del").click(function() {
        var alarm_id = $("#current-id").attr("_aid");
        deleteAlarm(alarm_id);
    });

    // alarm update
    $("#alarm-update").click(function () {
        var alarm_id = $("#current-id").attr("_aid");
        if (alarm_id == undefined) {
            console.log("update alarm fail, alarm_id=" + alarm_id);
            return;
        }
        var code = $("#alarm-code-update").val();
        if (code == null || !regex.test(code)) {
            alert("告警代号不合法");
            return;
        }
        var name = $("#alarm-name-update").val();
        if (name == null || name.trim().length == 0) {
            alert("请输入告警名称");
            return;
        }
        var project_id = $("#alarm-project-update").val();
        if (project_id == 0) {
            alert("请选择项目");
            return;
        }
        var module_id = $("#alarm-module-update").val();
        if (module_id == 0) {
            alert("请选择项目模块");
            return;
        }
        var group_id = $("#alarm-group-update").val();
        if (group_id == 0) {
            alert("请选择接收组");
            return;
        }
        var route_key = $("#alarm-route-key-update").val();
        var config = {};
        $("input[name=config-update]").each(function() {
            config[$(this).val()] = $(this).prop("checked");
        });
        config["debug_interval"] = parseInt($("#debug-update-interval").val());
        config["debug_times"] = parseInt($("#debug-update-times").val());
        config["info_interval"] = parseInt($("#info-update-interval").val());
        config["info_times"] = parseInt($("#info-update-times").val());
        config["error_interval"] = parseInt($("#error-update-interval").val());
        config["error_times"] = parseInt($("#error-update-times").val());

        var alarm_data = {
            id: alarm_id,
            code: code,
            name: name,
            projectId: project_id,
            moduleId: module_id,
            groupId: group_id,
            routeKey: route_key,
            config: JSON.stringify(config)
        };

        $("#modal-alarm-update").modal('hide');

        updateAlarm(alarm_data);
    });

    // init data table
    getAlarmList();
});

function getAlarmList(search) {
    $.ajax({
        url: "/alarms?page_no="+current_page+"&page_size="+page_length+"&"+search,
        type: "GET",
        success: function(data){
            var i, html = "";
            var alarm_list = data["alarm_list"];
            for(i = 0; i < alarm_list.length; i++) {
                var alarm  = alarm_list[i];
                html += "<tr>"
                    + "<td>"+alarm["alarm"]["code"]+"</td>"
                    + "<td>"+alarm["alarm"]["name"]+"</td>";
                if (alarm["project"] != undefined) {
                    html += "<td>"+alarm["project"]["name"]+"</td>";
                } else {
                    html += "<td></td>";
                }
                if (alarm["module"] != undefined) {
                    html += "<td>"+alarm["module"]["name"]+"</td>";
                } else {
                    html += "<td></td>";
                }
                if (alarm["group"] != undefined) {
                    html += "<td>"+alarm["group"]["name"]+"</td>";
                } else {
                    html += "<td></td>";
                }
                html += "<td>"+alarm["alarm"]["routeKey"]+"</td>"
                    + "<td>"+alarm["config"]+"</td>"
                    + "<td><button class='btn btn-info alarm-update' _aid='"+alarm["alarm"]["id"]+"' "
                    + "data-toggle='modal' data-target='#modal-alarm-update' "
                    + "style='padding:0;margin:0;width:40px;height:26px;'>编辑</button>"
                    + "&nbsp;&nbsp; <button class='btn btn-danger alarm-del' _aid='"+alarm["alarm"]["id"]+"' "
                    + "data-toggle='modal' data-target='#modal-alarm-del' "
                    + "style='padding:0;margin:0;width:40px;height:26px;'>删除</button></td></tr>"
            }
            $(".alarm-list").html(html);
            setAlarmClickEvent();
            var page_count = parseInt((data["alarm_count"] + page_length - 1)/page_length);
            $(".page-footer").html(setPageButton(page_count, current_page));
            setPageBtnClick();
            setTableTotalSize(data["alarm_count"]);
        }
    });
}

function setAlarmClickEvent() {
    $(".alarm-update").click(function() {
        var alarm_id = $(this).attr("_aid");
        $("#current-id").attr("_aid", alarm_id);
        getAlarmById(alarm_id);
    });

    $(".alarm-del").click(function() {
        $("#current-id").attr("_aid", $(this).attr("_aid"));
    });
}

function createAlarm(alarm_data) {
    $.ajax({
        url: "/alarms",
        type: "POST",
        data: JSON.stringify(alarm_data),
        dataType: "json",
        contentType: "application/json;charset=utf-8",
        success: function(data){
            if (data["code"] != 2000) {
                alert("新建告警失败");
            }
            location.replace(location.href);
        }
    });
}

function deleteAlarm(aid) {
    $.ajax({
        url: "/alarms/"+aid,
        type: "DELETE",
        data: JSON.stringify({}),
        dataType: "json",
        contentType: "application/json;charset=utf-8",
        success: function(data){
            if(data["code"] != 2000) {
                alert("删除告警失败");
                return;
            }
            getAlarmList(search_select + "=" + search_input);
        }
    });
}

function updateAlarm(alarm_data) {
    $.ajax({
        url: "/alarms",
        type: "PUT",
        data: JSON.stringify(alarm_data),
        dataType: "json",
        contentType: "application/json;charset=utf-8",
        success: function(data){
            if (data["code"] != 2000) {
                alert("更新告警失败");
                return;
            }
            getAlarmList(search_select + "=" + search_input);
        }
    });
}

function getAlarmById(aid) {
    $.ajax({
        url: "/alarms/"+aid,
        type: "GET",
        success: function(data){
            if(data["code"] == 2000) {
                $("#alarm-project-update").unbind("change");
                var i, alarm = data["alarm"];

                // project list
                var project_list = data["project_list"];
                var projectList = [];
                projectList.push({id:0, text:'请选择'});
                for (i = 0; i < project_list.length; i++) {
                    var project = project_list[i];
                    projectList.push({id: project["id"], text: project["name"]});
                }
                $("#alarm-project-update").html("").select2({
                    data: projectList
                }).val(alarm["projectId"]).trigger("change");

                // module list
                var module_list = data["module_list"];
                var moduleList = [];
                moduleList.push({id:0, text:'请选择'});
                for (i = 0; i < module_list.length; i++) {
                    var module = module_list[i];
                    moduleList.push({id: module["id"], text: module["name"]});
                }
                $("#alarm-module-update").html("").select2({
                    data: moduleList
                }).val(alarm["moduleId"]).trigger("change");

                // group list
                var group_list = data["group_list"];
                var groupList = [];
                groupList.push({id:0, text:'请选择'});
                for (i = 0; i < group_list.length; i++) {
                    var group = group_list[i];
                    groupList.push({id: group["id"], text: group["name"]});
                }
                $("#alarm-group-update").html("").select2({
                    data: groupList
                }).val(alarm["groupId"]).trigger("change");

                $("#alarm-code-update").val(alarm["code"]);
                $("#alarm-name-update").val(alarm["name"]);
                $("#alarm-route-key-update").val(alarm["routeKey"]);
                var config = JSON.parse(alarm["config"]);
                $("#mail").prop("checked", config["mail"]);
                $("#wechat").prop("checked", config["wechat"]);
                $("#sms").prop("checked", config["sms"]);
                $("#qq").prop("checked", config["qq"]);
                $("#freq_limit").prop("checked", config["freq_limit"]);

                $("#debug-update-interval").val(config["debug_interval"]);
                $("#debug-update-times").val(config["debug_times"]);
                $("#info-update-interval").val(config["info_interval"]);
                $("#info-update-times").val(config["info_times"]);
                $("#error-update-interval").val(config["error_interval"]);
                $("#error-update-times").val(config["error_times"]);

                // get module list and group list when project change
                $("#alarm-project-update").change(function() {
                    var project_id = $(this).val();
                    if (project_id == 0) {
                        return;
                    }
                    getModuleAndGroupByProjectId(project_id, "#alarm-module-update", "#alarm-group-update");
                });
            } else {
                alert("获取告警信息失败...");
            }
        }
    });
}

function getProjectInfo() {
    $.ajax({
        url: "/projects/all",
        type: "GET",
        success: function(data){
            if(data["code"] == 2000) {
                // project list
                var project_list = data["project_list"];
                var projectList = [];
                projectList.push({id:0, text:'请选择'});
                for (var i = 0; i < project_list.length; i++) {
                    var project = project_list[i];
                    projectList.push({id: project["id"], text: project["name"]});
                }
                $("#alarm-project").html("").select2({
                    data: projectList
                });
                $("#alarm-module").html("").select2({
                    data: [{id:0, text:'请选择'}]
                });
                $("#alarm-group").html("").select2({
                    data: [{id:0, text:'请选择'}]
                });
            } else {
                alert("获取项目列表失败...");
            }
        }
    });
}

function getModuleAndGroupByProjectId(pid, targetModule, targerGroup) {
    $.ajax({
        url: "/projects/"+pid+"/config",
        type: "GET",
        success: function(data){
            if(data["code"] == 2000) {
                // module list
                var module_list = data["module_list"];
                var i, moduleList = [];
                moduleList.push({id:0, text:'请选择'});
                for (i = 0; i < module_list.length; i++) {
                    var module = module_list[i];
                    moduleList.push({id: module["id"], text: module["name"]});
                }
                $(targetModule).html("").select2({
                    data: moduleList
                });

                // group list
                var group_list = data["group_list"];
                var groupList = [];
                groupList.push({id:0, text:'请选择'});
                for (i = 0; i < group_list.length; i++) {
                    var group = group_list[i];
                    groupList.push({id: group["id"], text: group["name"]});
                }
                $(targerGroup).html("").select2({
                    data: groupList
                });
            } else {
                alert("获取项目配置信息失败...");
            }
        }
    });
}

function setPageBtnClick() {
    $(".page-btn").click(function () {
        current_page = $(this).attr("_val");
        getAlarmList(search_select + "=" + search_input);
    });
}

function setTableTotalSize(count) {
    if (count == null || count == undefined) count = 0;
    $(".table-total-size").text("共有 "+count+" 条数据");
}