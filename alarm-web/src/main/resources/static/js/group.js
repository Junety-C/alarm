var search_select="";
var search_input="";

$(function() {
    // group add modal
    $("#group-add").click(function () {
        var group_name = $("#group-name").val();
        if (group_name.trim().length == 0) {
            alert("请输入接收组名称");
            return;
        }
        addGroup(group_name);
    });

    // group delete modal
    $("#group-del").click(function() {
        var group_id = $(this).attr("_val");
        deleteGroup(group_id);
    });

    // receiver add
    $("#receiver-add").click(function() {
        var receiver_id = $("#receivers").val();
        var group_id = $("#receiver-add").attr("_gid");
        addReceiverToGroup(group_id, receiver_id);
    });

    // receiver delete
    $("#receiver-del").click(function() {
        var receiver_id = $(this).attr("_val");
        var group_id = $("#receiver-add").attr("_gid");
        deleteReceiverFromGroup(group_id, receiver_id);
    });

    // init data table
    getGroups();

});

function getGroups(search) {
    $.ajax({
        url: "http://localhost:8088/groups?page="+current_page+"&length="+page_length+"&"+search,
        type: "GET",
        success: function(data){
            if(data["code"] == 2000) {
                var html = "";
                var groups = data["content"]["groups"];
                for(var i = 0; i < groups.length; i++) {
                    var group  = groups[i];
                    if (i == 0) {
                        $("#receiver-add").attr("_gid", group["id"]);
                        html += "<tr class='group' _val='"+group["id"]+"' style='background-color:#eee'>"
                            + "<td><div>"+group["name"]+""
                            + "<button class='btn btn-danger group-del' data-toggle='modal' data-target='#modal-group-del' "
                            + "_val='"+group["id"]+"' style='float:right;margin:0;padding:0;width:26px;'>X</button>"
                            + "</div></td></tr>";
                    } else {
                        html += "<tr class='group' _val='"+group["id"]+"'>"
                            + "<td><div>"+group["name"]+""
                            + "<button class='btn btn-danger group-del' data-toggle='modal' data-target='#modal-group-del' "
                            + "_val='"+group["id"]+"' style='float:right;margin:0;padding:0;width:26px;'>X</button>"
                            + "</div></td></tr>";
                    }
                }
                $(".groups-body").html(html);
                setGroupClickEvent();
                var page_count = parseInt((data["content"]["count"] + page_length - 1)/page_length);
                $(".page-footer").html(setPageButton(page_count, current_page));
                setPageBtnClick();
                setTableTotalSize(data["content"]["count"]);

                // receiver
                html = "";
                var receivers = data["content"]["receivers"];
                for(var i = 0; i < receivers.length; i++) {
                    var receiver  = receivers[i];
                    html += "<tr class='receiver' _val='"+receiver["id"]+"'>"
                        + "<td><div>"+receiver["name"]+""
                        + "<button class='btn btn-danger receiver-del' data-toggle='modal' data-target='#modal-receiver-del' "
                        + "_val='"+receiver["id"]+"' style='float:right;margin:0;padding:0;width:26px;'>X</button>"
                        + "</div></td></tr>";
                }
                $(".receivers-body").html(html);
                $(".receiver-del").click(function() {
                    $("#receiver-del").attr("_val", $(this).attr("_val"));
                });

                // all receiver
                var all_receiver = data["content"]["all_receiver"];
                var receiverList = [];
                for (var i = 0; i < all_receiver.length; i++) {
                    receiverList.push({id: all_receiver[i]["id"], text: all_receiver[i]["name"]+"("+all_receiver[i]["mail"]
                        + ","+all_receiver[i]["phone"]+","+all_receiver[i]["wechat"]+","+all_receiver[i]["qq"]+")"});
                }
                $("#receivers").select2({
                    data: receiverList
                })
            }
        }
    });
}

function setGroupClickEvent() {
    $(".group").click(function () {
        $(".group").css("background-color", "#fff");
        $(this).css("background-color", "#eee");
        var group_id = $(this).attr("_val");
        $("#receiver-add").attr("_gid", group_id);
        getReceiverByGroupId(group_id);
    });
    $(".group-del").click(function() {
        $("#group-del").attr("_val", $(this).attr("_val"));
    });
}

function getReceiverByGroupId(gid) {
    $.ajax({
        url: "http://localhost:8088/groups/"+gid+"/receivers",
        type: "GET",
        success: function(data){
            if(data["code"] == 2000) {
                var html = "";
                var receivers = data["content"]["receivers"];
                for(var i = 0; i < receivers.length; i++) {
                    var receiver  = receivers[i];
                    html += "<tr class='receiver' _val='"+receiver["id"]+"'>"
                        + "<td><div>"+receiver["name"]+""
                        + "<button class='btn btn-danger receiver-del' data-toggle='modal' data-target='#modal-receiver-del' "
                        + "_val='"+receiver["id"]+"' style='float:right;margin:0;padding:0;width:26px;'>X</button>"
                        + "</div></td></tr>";
                }
                $(".receivers-body").html(html);
            }
        }
    });
}

function addGroup(name) {
    $.ajax({
        url: "http://localhost:8088/groups/"+name,
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

function deleteGroup(gid) {
    $.ajax({
        url: "http://localhost:8088/groups/"+gid,
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

function addReceiverToGroup(gid, rid) {
    $.ajax({
        url: "http://localhost:8088/groups/"+gid+"/receivers/"+rid,
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

function deleteReceiverFromGroup(gid, rid) {
    $.ajax({
        url: "http://localhost:8088/groups/"+gid+"/receivers/"+rid,
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

function setPageBtnClick() {
    $(".page-btn").click(function () {
        current_page = $(this).attr("_val");
        getGroups(search_select + "=" + search_input);
    });
}

function setTableTotalSize(count) {
    if (count == null || count == undefined) count = 0;
    $(".table-total-size").text("共有 "+count+" 条数据");
}