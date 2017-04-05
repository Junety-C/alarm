$(function () {
    var DEFAULT_INTERVAL = 2000;

    // 队列状态
    $('#channel-status').highcharts({
        chart: {
            type: 'column',
            events: {
                load: function () {
                    var series = this.series[0];
                    setInterval(function () {
                        $.ajax({
                            url: "/monitor/queues/status",
                            type: "GET",
                            success: function(data) {
                                series.setData([data["mail"], data["sms"], data["qq"], data["wechat"]], true, true);
                            }
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
            data: [0, 0, 0, 0]
        }]
    });


    // 系统吞吐量
    Highcharts.setOptions({
        global: {
            useUTC: false
        }
    });
    $('#throughput-status').highcharts({
        chart: {
            type: 'areaspline',
            animation: Highcharts.svg,
            marginRight: 10,
            events: {
                load: function () {
                    var series = this.series[0];
                    setInterval(function () {
                        $.ajax({
                            url: "/monitor/request/quantity",
                            type: "GET",
                            success: function(data) {
                                var x = (new Date()).getTime(),
                                    y = data["quantity"];
                                series.addPoint([x, y], true, true);
                            }
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
                text: '值'
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