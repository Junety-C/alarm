
$(function() {
    $("#login").click(function () {
        var username = $("#username").val();
        var password = $("#password").val();

        if(username.trim().length == 0 || password.trim().length == 0) {
            alert("用户名或密码不能为空");
            return;
        }

        var login_data = {
            username: username,
            password: password
        };

        login(login_data);
    });
});

function login(login_data) {
    $.ajax({
        url: "/login",
        type: "POST",
        data: JSON.stringify(login_data),
        dataType: "json",
        contentType: "application/json;charset=utf-8",
        success: function(data){
            if (data["code"] != 2000) {
                alert("用户名或密码错误");
                return;
            }
            window.location = "/home";
        }
    });
}