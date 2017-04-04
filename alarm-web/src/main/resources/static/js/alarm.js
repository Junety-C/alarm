var search_select="";
var search_input="";

$(function() {
    // data search
    $("#search-submit").click(function() {
        search_select = $("#search-select option:selected").attr("_val");
        search_input = $("#search-input").val();
        getAlarms(search_select + "=" + search_input);
    });

    // alarm-modal init
    $("#alarm-modal").click(function() {
        getCreateInfo();
    });

    // get module list when project change
    $("#alarm-project").change(function() {
        getModuleByProjectId($(this).val(), "#alarm-module");
    });

    // check and add alarm
    $("#alarm-add").click(function() {
        var code = $("#alarm-code").val();
        var name = $("#alarm-name").val();
        if (name == null || name.trim().length == 0) {
            alert("请输入告警名称");
            return;
        }
        var project_id = $("#alarm-project").val();
        var module_id = $("#alarm-module").val();
        var group_id = $("#alarm-group").val();
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
        addAlarm(alarm_data);
    });

    $("#alarm-del").click(function() {
        var alarm_id = $(this).attr("_val");
        deleteAlarm(alarm_id);
    });

    // receiver add modal
    $("#alarm-update").click(function () {
        var alarm_id = $(this).attr("_val");
        var code = $("#alarm-code-update").val();
        var name = $("#alarm-name-update").val();
        if (name == null || name.trim().length == 0) {
            alert("请输入告警名称");
            return;
        }
        var project_id = $("#alarm-project-update").val();
        var module_id = $("#alarm-module-update").val();
        var group_id = $("#alarm-group-update").val();
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
        updateAlarm(alarm_data);
    });

    // init data table
    getAlarms();
});

function getAlarms(search) {
    $.ajax({
        url: "/alarms?page_no="+current_page+"&page_size="+page_length+"&"+search,
        type: "GET",
        success: function(data){
            var html = "";
            var alarms = data["alarms"];
            for(var i = 0; i < alarms.length; i++) {
                var alarm  = alarms[i];
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
                    html += "<td></td>";
                } else {
                    html += "<td></td>";
                }
                html += "<td>"+alarm["alarm"]["routeKey"]+"</td>"
                    + "<td>"+alarm["config"]+"</td>"
                    + "<td><button class='btn btn-info alarm-update' _val='"+alarm["alarm"]["id"]+"' "
                    + "data-toggle='modal' data-target='#modal-alarm-update' "
                    + "style='padding:0;margin:0;width:40px;height:26px;'>编辑</button>"
                    + "&nbsp;&nbsp; <button class='btn btn-danger alarm-del' _val='"+alarm["alarm"]["id"]+"' "
                    + "data-toggle='modal' data-target='#modal-alarm-del' "
                    + "style='padding:0;margin:0;width:40px;height:26px;'>删除</button></td></tr>"
            }
            $(".alarms-body").html(html);
            setAlarmClickEvent();
            var page_count = parseInt((data["count"] + page_length - 1)/page_length);
            $(".page-footer").html(setPageButton(page_count, current_page));
            setPageBtnClick();
            setTableTotalSize(data["count"]);
        }
    });
}

function setAlarmClickEvent() {
    $(".alarm-del").click(function() {
        $("#alarm-del").attr("_val", $(this).attr("_val"));
    });

    $(".alarm-update").click(function() {
        var alarm_id = $(this).attr("_val");
        $("#alarm-update").attr("_val", alarm_id);
        getAlarmById(alarm_id);
    });
}

function addAlarm(alarm_data) {
    $.ajax({
        url: "/alarms",
        type: "POST",
        data: JSON.stringify(alarm_data),
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

function deleteAlarm(aid) {
    $.ajax({
        url: "/alarms/"+aid,
        type: "DELETE",
        data: JSON.stringify({}),
        dataType: "json",
        contentType: "application/json;charset=utf-8",
        success: function(data){
            if(data["code"] != 2000) {
                alert("删除失败");
                return;
            }
            getAlarms(search_select + "=" + search_input);
            //location.replace(location.href);
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
                alert("更新失败");
                return;
            }
            getAlarms(search_select + "=" + search_input);
            //location.replace(location.href);
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
                var alarm = data["alarm"];

                // code list
                var codes = data["codes"];
                var codeList = [{id: 0, text: '自动生成'}];
                for (var i = 0; i < codes.length; i++) {
                    codeList.push({id: codes[i], text: codes[i]});
                }
                $("#alarm-code-update").html("").select2({
                    data: codeList
                }).val(alarm["code"]).trigger("change");

                // project list
                var projects = data["projects"];
                var projectList = [];
                for (var i = 0; i < projects.length; i++) {
                    projectList.push({id: projects[i]["id"], text: projects[i]["name"]});
                }
                $("#alarm-project-update").html("").select2({
                    data: projectList
                }).val(alarm["projectId"]).trigger("change");

                // module list
                var modules = data["modules"];
                var moduleList = [];
                for (var i = 0; i < modules.length; i++) {
                    moduleList.push({id: modules[i]["id"], text: modules[i]["name"]});
                }
                $("#alarm-module-update").html("").select2({
                    data: moduleList
                }).val(alarm["moduleId"]).trigger("change");

                // group list
                var groups = data["groups"];
                var groupList = [];
                for (var i = 0; i < groups.length; i++) {
                    groupList.push({id: groups[i]["id"], text: groups[i]["name"]});
                }
                $("#alarm-group-update").html("").select2({
                    data: groupList
                }).val(alarm["groupId"]).trigger("change");

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

                // get module list when project change
                $("#alarm-project-update").change(function() {
                    getModuleByProjectId($(this).val(), "#alarm-module-update");
                });
            } else {
                alert("获取告警信息失败...");
            }
        }
    });
}

function getCreateInfo() {
    $.ajax({
        url: "/alarms/info",
        type: "GET",
        success: function(data){
            if(data["code"] == 2000) {
                // code list
                var codes = data["codes"];
                var codeList = [{id: 0, text: '自动生成'}];
                for (var i = 0; i < codes.length; i++) {
                    codeList.push({id: codes[i], text: codes[i]});
                }
                $("#alarm-code").select2({
                    data: codeList
                });

                // project list
                var projects = data["projects"];
                var projectList = [];
                for (var i = 0; i < projects.length; i++) {
                    projectList.push({id: projects[i]["id"], text: projects[i]["name"]});
                }
                $("#alarm-project").select2({
                    data: projectList
                });

                // module list
                var modules = data["modules"];
                var moduleList = [];
                for (var i = 0; i < modules.length; i++) {
                    moduleList.push({id: modules[i]["id"], text: modules[i]["name"]});
                }
                $("#alarm-module").select2({
                    data: moduleList
                });

                // group list
                var groups = data["groups"];
                var groupList = [];
                for (var i = 0; i < groups.length; i++) {
                    groupList.push({id: groups[i]["id"], text: groups[i]["name"]});
                }
                $("#alarm-group").select2({
                    data: groupList
                });
            } else {
                alert("获取告警创建信息失败...");
            }
        }
    });
}

function getModuleByProjectId(pid, target) {
    $.ajax({
        url: "/projects/"+pid+"/modules",
        type: "GET",
        success: function(data){
            if(data["code"] == 2000) {
                // module list
                var modules = data["modules"];
                var moduleList = [];
                for (var i = 0; i < modules.length; i++) {
                    moduleList.push({id: modules[i]["id"], text: modules[i]["name"]});
                }
                $(target).html("").select2({
                    data: moduleList
                });
            } else {
                alert("获取项目模块失败...");
            }
        }
    });
}

function setPageBtnClick() {
    $(".page-btn").click(function () {
        current_page = $(this).attr("_val");
        getAlarms(search_select + "=" + search_input);
    });
}

function setTableTotalSize(count) {
    if (count == null || count == undefined) count = 0;
    $(".table-total-size").text("共有 "+count+" 条数据");
}