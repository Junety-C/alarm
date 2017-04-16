var search_select="";
var search_input="";

$(function() {
    // data search
    $("#search-submit").click(function() {
        search_select = $("#search-select option:selected").attr("_val");
        search_input = $("#search-input").val();
        getUserList(search_select + "=" + search_input);
    });

    // user create modal
    $("#user-create").click(function () {
        var user_account = $("#user-account").val();
        if (user_account.trim().length == 0) {
            alert("请输入用户账号");
            return;
        }
        var user_name = $("#user-name").val();
        if (user_name.trim().length == 0) {
            alert("请输入用户名称");
            return;
        }
        var user_data = {
            account: user_account,
            name: user_name,
            mail: $("#user-mail").val(),
            phone: $("#user-phone").val(),
            wechat: $("#user-wechat").val(),
            qq: $("#user-qq").val()
        };
        createUser(user_data);
    });

    // user delete modal
    $("#user-del").click(function() {
        var user_id = $("#current-id").attr("_uid");
        if (user_id == undefined) {
            console.log("del user fail, user_id="+user_id);
            return;
        }
        deleteUser(user_id);
    });

    // user update modal
    $("#user-update").click(function () {
        var user_name = $("#user-name-update").val();
        if (user_name.trim().length == 0) {
            alert("请输入用户名称");
            return;
        }
        var user_data = {
            id: $("#current-id").attr("_uid"),
            name: user_name,
            mail: $("#user-mail-update").val(),
            phone: $("#user-phone-update").val(),
            wechat: $("#user-wechat-update").val(),
            qq: $("#user-qq-update").val()
        };
        updateUser(user_data);
    });

    // init data table
    getUserList();

});

function getUserList(search) {
    $.ajax({
        url: "/users?page_no="+current_page+"&page_size="+page_length+"&"+search,
        type: "GET",
        success: function(data){
            if(data["code"] == 2000) {
                console.log(data);
                var i, html = "";
                var user_list = data["user_list"];
                if (data["current_user"]["type"] == 0) {
                    // admin
                    for (i = 0; i < user_list.length; i++) {
                        var user = user_list[i];
                        html += "<tr><td>" + user["account"] + "</td>"
                            + "<td>" + user["name"] + "</td>"
                            + "<td>" + user["mail"] + "</td>"
                            + "<td>" + user["phone"] + "</td>"
                            + "<td>" + user["wechat"] + "</td>"
                            + "<td>" + user["qq"] + "</td>"
                            + "<td><button class='btn btn-info user-update' _uid='" + user["id"] + "' "
                            + "data-toggle='modal' data-target='#modal-user-update' "
                            + "style='padding:0;margin:0;width:40px;height:26px;'>编辑</button>"
                            + "&nbsp;&nbsp; <button class='btn btn-danger user-del' _uid='" + user["id"] + "' "
                            + "data-toggle='modal' data-target='#modal-user-del' "
                            + "style='padding:0;margin:0;width:40px;height:26px;'>删除</button></td></tr>";
                    }
                } else {
                    // normal user
                    for (i = 0; i < user_list.length; i++) {
                        var user = user_list[i];
                        html += "<tr><td>" + user["account"] + "</td>"
                            + "<td>" + user["name"] + "</td>"
                            + "<td>" + user["mail"] + "</td>"
                            + "<td>" + user["phone"] + "</td>"
                            + "<td>" + user["wechat"] + "</td>"
                            + "<td>" + user["qq"] + "</td>";
                        if (user["id"] == data["current_user"]["id"]) {
                            html += "<td><button class='btn btn-info user-update' _uid='" + user["id"] + "' "
                                + "data-toggle='modal' data-target='#modal-user-update' "
                                + "style='padding:0;margin:0;width:40px;height:26px;'>编辑</button></td></tr>";
                        } else {
                            html += "<td></td></tr>";
                        }
                    }
                }
                $(".user-list").html(html);
                setUserClickEvent();
                var page_count = parseInt((data["user_count"] + page_length - 1)/page_length);
                $(".page-footer").html(setPageButton(page_count, current_page));
                setPageBtnClick();
                setTableTotalSize(data["user_count"]);
            }
        }
    });
}

function setUserClickEvent() {
    $(".user-del").click(function() {
        $("#current-id").attr("_uid", $(this).attr("_uid"));
    });

    $(".user-update").click(function() {
        var user_id = $(this).attr("_uid");
        $("#current-id").attr("_uid", user_id);
        getUserById(user_id);
    });
}

function getUserById(uid) {
    $.ajax({
        url: "/users/"+uid,
        type: "GET",
        success: function(data){
            if(data["code"] == 2000) {
                var user = data["user"];
                $("#user-account-update").val(user["name"]);
                $("#user-name-update").val(user["name"]);
                $("#user-mail-update").val(user["mail"]);
                $("#user-phone-update").val(user["phone"]);
                $("#user-wechat-update").val(user["wechat"]);
                $("#user-qq-update").val(user["qq"]);
            }
        }
    });
}

function createUser(user_data) {
    $.ajax({
        url: "/users",
        type: "POST",
        data: JSON.stringify(user_data),
        dataType: "json",
        contentType: "application/json;charset=utf-8",
        success: function(data){
            if (data["code"] != 2000) {
                alert("新建用户失败");
            }
            location.replace(location.href);
        }
    });
}

function deleteUser(uid) {
    $.ajax({
        url: "/users/"+uid,
        type: "DELETE",
        data: JSON.stringify({}),
        dataType: "json",
        contentType: "application/json;charset=utf-8",
        success: function(data){
            if(data["code"] != 2000) {
                alert("删除用户失败");
                return;
            }
            getUserList(search_select + "=" + search_input);
        }
    });
}

function updateUser(user_data) {
    $.ajax({
        url: "/users",
        type: "PUT",
        data: JSON.stringify(user_data),
        dataType: "json",
        contentType: "application/json;charset=utf-8",
        success: function(data){
            if(data["code"] != 2000) {
                alert("更新用户信息失败");
                return;
            }
            getUserList(search_select + "=" + search_input);
        }
    });
}

function setPageBtnClick() {
    $(".page-btn").click(function () {
        current_page = $(this).attr("_val");
        getUserList(search_select + "=" + search_input);
    });
}

function setTableTotalSize(count) {
    if (count == null || count == undefined) count = 0;
    $(".table-total-size").text("共有 "+count+" 条数据");
}