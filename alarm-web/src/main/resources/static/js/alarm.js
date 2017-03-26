var search_select="code";
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
        getModuleByProjectId($(this).val());
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
            if ($(this).attr("checked")) {
                config[$(this).val()] = true;
            } else {
                config[$(this).val()] = false;
            }
        });

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

    // init data table
    getAlarms();
});

function getAlarms(search) {
    $.ajax({
        url: "http://localhost:8088/alarms?page="+current_page+"&length="+page_length+"&"+search,
        type: "GET",
        success: function(data){
            if(data["code"] == 2000) {
                var html = "";
                var alarms = data["content"]["alarms"];
                for(var i = 0; i < alarms.length; i++) {
                    var alarm  = alarms[i];
                    html += "<tr>"
                        + "<td>"+alarm["alarm"]["code"]+"</td>"
                        + "<td>"+alarm["alarm"]["name"]+"</td>"
                        + "<td>"+alarm["group"]["name"]+"</td>"
                        + "<td>"+alarm["project"]["name"]+"</td>"
                        + "<td>"+alarm["module"]["name"]+"</td>"
                        + "<td>"+alarm["config"]+"</td>"
                        + "<td></td>"
                        + "</tr>";
                }
                $(".alarms-body").html(html);
                var page_count = parseInt((data["content"]["count"] + page_length - 1)/page_length);
                $(".page-footer").html(setPageButton(page_count, current_page));
                setPageBtnClick();
                setTableTotalSize(data["content"]["count"]);
            }
        }
    });
}

function getCreateInfo() {
    $.ajax({
        url: "http://localhost:8088/alarms/info",
        type: "GET",
        success: function(data){
            if(data["code"] == 2000) {
                // code list
                var codes = data["content"]["codes"];
                var codeList = [{id: 0, text: '自动生成'}];
                for (var i = 0; i < codes.length; i++) {
                    codeList.push({id: i + 1, text: codes[i]});
                }
                $("#alarm-code").select2({
                    data: codeList
                });

                // project list
                var projects = data["content"]["projects"];
                var projectList = [];
                for (var i = 0; i < projects.length; i++) {
                    projectList.push({id: projects[i]["id"], text: projects[i]["name"]});
                }
                $("#alarm-project").select2({
                    data: projectList
                });

                // module list
                var modules = data["content"]["modules"];
                var moduleList = [];
                for (var i = 0; i < modules.length; i++) {
                    moduleList.push({id: modules[i]["id"], text: modules[i]["name"]});
                }
                $("#alarm-module").select2({
                    data: moduleList
                });

                // group list
                var groups = data["content"]["groups"];
                var groupList = [];
                for (var i = 0; i < groups.length; i++) {
                    groupList.push({id: groups[i]["id"], text: groups[i]["name"]});
                }
                $("#alarm-group").select2({
                    data: groupList
                });
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

function getModuleByProjectId(pid) {
    $.ajax({
        url: "http://localhost:8088/projects/"+pid+"/modules",
        type: "GET",
        success: function(data){
            if(data["code"] == 2000) {
                // module list
                var modules = data["content"]["modules"];
                var moduleList = [];
                for (var i = 0; i < modules.length; i++) {
                    moduleList.push({id: modules[i]["id"], text: modules[i]["name"]});
                }
                $("#alarm-module").html("").select2({
                    data: moduleList
                });
            }
        }
    });
}

function addAlarm(alarm_data) {
    $.ajax({
        url: "http://localhost:8088/alarms",
        type: "POST",
        data: JSON.stringify(alarm_data),
        dataType: "json",
        contentType: "application/json;charset=utf-8",
        success: function(data){
            if (data["code"] != 2000) {
                alert("创建失败");
            }
            location.replace(location.href);
        },
        error: function(XMLHttpRequest, textStatus, errorThrown){
            console.log(XMLHttpRequest)
        }
    });
}