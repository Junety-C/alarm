var DEFAULT_INTERVAL = 2000;

$(function () {

    Highcharts.setOptions({
        global: {
            useUTC: false
        }
    });
    
    createChannelStatusChart();
    createRequestQuantityChart();
    createSendingQuantityChart();
});

function createChannelStatusChart() {
    // 队列状态
    $.getJSON("/monitor/queues/status", function (initData) {
        $('#channel-status').highcharts({
            chart: {
                type: 'column',
                events: {
                    load: function () {
                        var series = this.series[0];
                        setInterval(function () {
                            $.getJSON("/monitor/queues/status", function (data) {
                                series.setData([data["mail"], data["sms"], data["qq"], data["wechat"]], true, true);
                            });
                        }, DEFAULT_INTERVAL);
                    }
                }
            },
            title: {
                text: '队列状态监测'
            },
            xAxis: {
                categories: [
                    'mail',
                    'sms',
                    'qq',
                    'wechat'
                ]
            },
            yAxis: {
                min: 0,
                title: {
                    text: '队列大小'
                }
            },
            tooltip: {
                pointFormat: '{series.name}:{point.y}'
            },
            plotOptions: {
                column: {
                    pointPadding: 0.1,
                    borderWidth: 0
                }
            },
            series: [{
                name:"待发送",
                data: [initData["mail"], initData["sms"], initData["qq"], initData["wechat"]]
            }]
        });
    });
}

function createRequestQuantityChart() {
    // 系统吞吐量
    var prev_quantity, current_quantity;
    $.getJSON("/monitor/request/quantity", function (initData) {
        prev_quantity = initData["quantity"];
        $('#throughput-status').highcharts({
            chart: {
                type: 'areaspline',
                animation: Highcharts.svg,
                marginRight: 10,
                events: {
                    load: function () {
                        var series = this.series[0];
                        setInterval(function () {
                            $.getJSON("/monitor/request/quantity", function (data) {
                                current_quantity = data["quantity"];
                                var x = (new Date()).getTime(),
                                    y = current_quantity - prev_quantity;
                                prev_quantity = current_quantity;
                                series.addPoint([x, y], true, true);
                            });
                        }, DEFAULT_INTERVAL);
                    }
                }
            },
            title: {
                text: '服务端吞吐量(每'+DEFAULT_INTERVAL/1000+'秒)'
            },
            xAxis: {
                type: 'datetime',
                tickPixelInterval: 150
            },
            yAxis: {
                title: {
                    text: '吞吐量'
                },
                plotLines: [{
                    value: 0,
                    width: 1,
                    color: '#808080'
                }]
            },
            tooltip: {
                formatter: function () {
                    return '<b>' + this.series.name + ':'+this.y+'</b><br/>' +
                        Highcharts.dateFormat('%H:%M:%S', this.x);
                }
            },
            legend: {
                enabled: false
            },
            exporting: {
                enabled: false
            },
            series: [{
                name: '吞吐量(次)',
                data: (function () {
                    var data = [];
                    var time = (new Date()).getTime();
                    for (var i = -19; i <= 0; i++) {
                        data.push({x: time + i * DEFAULT_INTERVAL, y: 0});
                    }
                    return data;
                }())
            }]
        }, function(c) {
        });
    });
}

function createSendingQuantityChart() {
    $.getJSON("/monitor/sending/quantity", function (initData) {
        var tbody = "<tr>"
                    + "<td>今天</td>"
                    + "<td>"+(initData["mail_today"] == undefined ? 0 : initData["mail_today"])+"</td>"
                    + "<td>"+(initData["sms_today"] == undefined ? 0 : initData["sms_today"])+"</td>"
                    + "<td>"+(initData["qq_today"] == undefined ? 0 : initData["qq_today"])+"</td>"
                    + "<td>"+(initData["wechat_today"] == undefined ? 0 : initData["wechat_today"])+"</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td>昨天</td>"
                    + "<td>"+(initData["mail_yesterday"] == undefined ? 0 : initData["mail_yesterday"])+"</td>"
                    + "<td>"+(initData["sms_yesterday"] == undefined ? 0 : initData["sms_yesterday"])+"</td>"
                    + "<td>"+(initData["qq_yesterday"] == undefined ? 0 : initData["qq_yesterday"])+"</td>"
                    + "<td>"+(initData["wechat_yesterday"] == undefined ? 0 : initData["wechat_yesterday"])+"</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td>历史</td>"
                    + "<td>"+(initData["mail_total"] == undefined ? 0 : initData["mail_total"])+"</td>"
                    + "<td>"+(initData["sms_total"] == undefined ? 0 : initData["sms_total"])+"</td>"
                    + "<td>"+(initData["qq_total"] == undefined ? 0 : initData["qq_total"])+"</td>"
                    + "<td>"+(initData["wechat_total"] == undefined ? 0 : initData["wechat_total"])+"</td>"
                    + "</tr>";
        $(".sending-quantity").html(tbody);
    });
}