$(function() {
	var current_page = 1;
	var page_length = 10;
	$(".page-footer").html(setPageButton(50, current_page));

	$("[data-toggle='tooltip']").tooltip();

})

function setPageButton(count, current_page) {
	var src = "";
	var a, b, c, d;
	b = Math.max(1, parseInt(current_page) - 4);
	a = Math.min(2, b - 1);
	c = Math.min(count, parseInt(current_page) + 4);
	d = Math.max(count - 1, c + 1);

	src = "<div class='btn-group'><button class='btn btn-default' _val='1'><<</button>";
	for(var i = 1; i <= a; i++) {
		src += "<button class='btn btn-default' _val='"+i+"'>"+i+"</button>";
	}
	if(a + 1 < b) src += "<button class='btn btn-default'>...</button>";
	for(var i = b; i <= c; i++) {
		if(i == current_page) src += "<button class='btn btn-default' _val='"+i+"'>"+i+"</button>";
		else src += "<button class='btn btn-default' _val='"+i+"'>"+i+"</button>";
	}
	if(c + 1 < d) src += "<button class='btn btn-default'>...</button>";
	for(var i = d; i <= count; i++) {
		src += "<button class='btn btn-default' _val='"+i+"'>"+i+"</button>";
	}
	src += "<button class='btn btn-default' _val='"+count+"'>>></button></div>";
	return src;
}