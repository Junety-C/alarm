var search_select="";
var search_input="";

$(function() {
    // data search
    $("#search-submit").click(function() {
        search_select = $("#search-select option:selected").attr("_val");
        search_input = $("#search-input").val();
        getLogs(search_select + "=" + search_input);
    });

    // init data table
    getLogs();
});

function getLogs(search) {
    $.ajax({
        url: "/logs?page_no="+current_page+"&page_size="+page_length+"&"+search,
        type: "GET",
        success: function(data){
            if(data["code"] == 2000) {
                var html = "";
                var logs = data["content"]["logs"];
                for(var i = 0; i < logs.length; i++) {
                    var log  = logs[i];
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
                $(".logs-body").html(html);
                var page_count = parseInt((data["content"]["count"] + page_length - 1)/page_length);
                $(".page-footer").html(setPageButton(page_count, current_page));
                setPageBtnClick();
                setTableTotalSize(data["content"]["count"]);
            }
        }
    });
}

function setPageBtnClick() {
    $(".page-btn").click(function () {
        current_page = $(this).attr("_val");
        getLogs(search_select + "=" + search_input);
    });
}

function setTableTotalSize(count) {
    if (count == null || count == undefined) count = 0;
    $(".table-total-size").text("共有 "+count+" 条数据");
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