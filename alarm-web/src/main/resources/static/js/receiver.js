var search_select="name";
var search_input="";

$(function() {
    // data search
    $("#search-submit").click(function() {
        search_select = $("#search-select option:selected").attr("_val");
        search_input = $("#search-input").val();
        getReceivers(search_select + "=" + search_input);
    });

    // receiver add modal
    $("#receiver-add").click(function () {
        var receiver_name = $("#receiver-name").val();
        if (receiver_name.trim().length == 0) {
            alert("请输入接收人名称");
            return;
        }
        var receiver_data = {
            name: receiver_name,
            mail: $("#receiver-mail").val(),
            phone: $("#receiver-phone").val(),
            wechat: $("#receiver-wechat").val(),
            qq: $("#receiver-qq").val()
        }
        addReceiver(receiver_data);
    });

    // receiver delete modal
    $("#receiver-del").click(function() {
        var receiver_id = $(this).attr("_val");
        deleteReceiver(receiver_id);
    });

    // receiver add modal
    $("#receiver-update").click(function () {
        var receiver_name = $("#receiver-name-update").val();
        if (receiver_name.trim().length == 0) {
            alert("请输入接收人名称");
            return;
        }
        var receiver_data = {
            id: $(this).attr("_val"),
            name: receiver_name,
            mail: $("#receiver-mail-update").val(),
            phone: $("#receiver-phone-update").val(),
            wechat: $("#receiver-wechat-update").val(),
            qq: $("#receiver-qq-update").val()
        };
        updateReceiver(receiver_data);
    });

    // init data table
    getReceivers();

});

function getReceivers(search) {
    $.ajax({
        url: "/receivers?page="+current_page+"&length="+page_length+"&"+search,
        type: "GET",
        success: function(data){
            if(data["code"] == 2000) {
                var html = "";
                var receivers = data["content"]["receivers"];
                for(var i = 0; i < receivers.length; i++) {
                    var receiver  = receivers[i];
                    html += "<tr><td>"+receiver["name"]+"</td>"
                        + "<td>"+receiver["mail"]+"</td>"
                        + "<td>"+receiver["phone"]+"</td>"
                        + "<td>"+receiver["wechat"]+"</td>"
                        + "<td>"+receiver["qq"]+"</td>"
                        + "<td><button class='btn btn-info receiver-update' _val='"+receiver["id"]+"' "
                        + "data-toggle='modal' data-target='#modal-receiver-update' "
                        + "style='padding:0;margin:0;width:40px;height:26px;'>编辑</button>"
                        + "&nbsp;&nbsp; <button class='btn btn-danger receiver-del' _val='"+receiver["id"]+"' "
                        + "data-toggle='modal' data-target='#modal-receiver-del' "
                        + "style='padding:0;margin:0;width:40px;height:26px;'>删除</button></td></tr>";
                }
                $(".receivers-body").html(html);
                setReceiverClickEvent();
                var page_count = parseInt((data["content"]["count"] + page_length - 1)/page_length);
                $(".page-footer").html(setPageButton(page_count, current_page));
                setPageBtnClick();
                setTableTotalSize(data["content"]["count"]);
            }
        }
    });
}

function setReceiverClickEvent() {

    $(".receiver-del").click(function() {
        $("#receiver-del").attr("_val", $(this).attr("_val"));
    });

    $(".receiver-update").click(function() {
        var receiver_id = $(this).attr("_val");
        $("#receiver-update").attr("_val", receiver_id);
        getReceiverById(receiver_id);
    });
}

function getReceiverById(rid) {
    $.ajax({
        url: "/receivers/"+rid,
        type: "GET",
        success: function(data){
            if(data["code"] == 2000) {
                var receiver = data["content"]["receiver"];
                $("#receiver-name-update").val(receiver["name"]);
                $("#receiver-mail-update").val(receiver["mail"]);
                $("#receiver-phone-update").val(receiver["phone"]);
                $("#receiver-wechat-update").val(receiver["wechat"]);
                $("#receiver-qq-update").val(receiver["qq"]);
            }
        }
    });
}

function addReceiver(receiver_data) {
    $.ajax({
        url: "/receivers",
        type: "POST",
        data: JSON.stringify(receiver_data),
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

function deleteReceiver(rid) {
    $.ajax({
        url: "/receivers/"+rid,
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

function updateReceiver(receiver_data) {
    $.ajax({
        url: "/receivers",
        type: "PUT",
        data: JSON.stringify(receiver_data),
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
        getReceivers(search_select + "=" + search_input);
    });
}

function setTableTotalSize(count) {
    if (count == null || count == undefined) count = 0;
    $(".table-total-size").text("共有 "+count+" 条数据");
}