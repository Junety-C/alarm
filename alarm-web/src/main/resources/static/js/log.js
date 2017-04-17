var search_select="";
var search_input="";

$(function() {
    // data search
    $("#search-submit").click(function() {
        search_select = $("#search-select option:selected").attr("_val");
        search_input = $("#search-input").val();
        getLogList(search_select + "=" + search_input);
    });

    // init data table
    getLogList();
});

function getLogList(search) {
    $.ajax({
        url: "/logs?page_no="+current_page+"&page_size="+page_length+"&"+search,
        type: "GET",
        success: function(data){
            if(data["code"] == 2000) {
                var html = "";
                var log_list = data["log_list"];
                for(var i = 0; i < log_list.length; i++) {
                    var log  = log_list[i];
                    var status;
                    if(log["status"] == 1) status = "已发送";
                    else if(log["status"] == 2)status = "限频";
                    else if(log["status"]== 3)status = "测试";
                    else status = "创建";

                    html += "<tr><td>"+log["reportId"]+"</td>"
                        + "<td>"+log["code"]+"</td>"
                        + "<td>"+log["alarmName"]+"</td>"
                        + "<td>"+formatDate(new Date(log["createTime"]))+"</td>"
                        + "<td>"+log["projectName"]+"</td>"
                        + "<td>"+log["groupName"]+"</td>"
                        + "<td>"+log["receivers"]+"</td>"
                        + "<td>"+status+"</td>"
                        + "<td>"+log["deliveryStatus"]+"</td>"
                        + "<td>"+log["content"]+"</td></tr>";
                }
                $(".log-list").html(html);
                var page_count = parseInt((data["log_count"] + page_length - 1)/page_length);
                $(".page-footer").html(setPageButton(page_count, current_page));
                setPageBtnClick();
                setTableTotalSize(data["log_count"]);
            }
        }
    });
}

function setPageBtnClick() {
    $(".page-btn").click(function () {
        current_page = $(this).attr("_val");
        getLogList(search_select + "=" + search_input);
    });
}

function setTableTotalSize(count) {
    if (count == null || count == undefined) count = 0;
    $(".table-total-size").text("共有 "+count+" 条数据");
}