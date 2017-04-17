var current_page = 1;
var page_length = 10;

$(function() {

	$("[data-toggle='tooltip']").tooltip();

	// 菜单页面跳转
	$(".page-item").click(function () {
		var location = $(this).attr("_val");
        window.location = "/" + location;
    });

	$("#logout").click(function () {
		logout();
    });

});

// 分页按钮
function setPageButton(page_count, current_page) {
	var a, b, c, d;
	b = Math.max(1, parseInt(current_page) - 4);
	a = Math.min(2, b - 1);
	c = Math.min(page_count, parseInt(current_page) + 4);
	d = Math.max(page_count - 1, c + 1);

    var src = "<div class='btn-group'><button class='btn btn-default page-btn' _val='1'><<</button>";
	for(var i = 1; i <= a; i++) {
		src += "<button class='btn btn-default page-btn' _val='"+i+"'>"+i+"</button>";
	}
	if(a + 1 < b) src += "<button class='btn btn-default'>...</button>";
	for(var i = b; i <= c; i++) {
		if(i == current_page) src += "<button class='btn btn-info page-btn' _val='"+i+"'>"+i+"</button>";
		else src += "<button class='btn btn-default page-btn' _val='"+i+"'>"+i+"</button>";
	}
	if(c + 1 < d) src += "<button class='btn btn-default'>...</button>";
	for(var i = d; i <= page_count; i++) {
		src += "<button class='btn btn-default page-btn' _val='"+i+"'>"+i+"</button>";
	}
	src += "<button class='btn btn-default page-btn' _val='"+page_count+"'>>></button></div>";
	return src;
}

function logout() {
    $.ajax({
        url: "/logout",
        type: "POST",
        data: JSON.stringify({}),
        dataType: "json",
        contentType: "application/json;charset=utf-8",
        success: function(data){
            if (data["code"] == 2000) {
                window.location = "/login";
            }
        }
    });
}

function formatDate(date)   {
    var year = date.getFullYear();
    var month = date.getMonth()+1;
    var day = date.getDate();
    var hour = date.getHours();
    var minute = date.getMinutes();
    var second = date.getSeconds();
    return year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second;
}