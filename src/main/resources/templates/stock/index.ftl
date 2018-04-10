<html>
    <head>
        <meta charset="utf-8">
        <title>Fuxiaoli-Stock</title>
        <link href="https://cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
    <div class="container">
        <div class="row clearfix">
            <div class="col-md-12 column">
                <div class="tabbable">
                    <ul class="nav nav-tabs">
                        <li class="active">
                            <a href="#panel-640107" data-toggle="tab">我的自选</a>
                        </li>
                        <#--<li>
                            <a href="#panel-165955" data-toggle="tab">Section 2</a>
                        </li>-->
                    </ul>
                <table class="table table-condensed table-striped table-hover">
                    <thead>
                    <tr>
                        <th>编号</th>
                        <th>股票代码</th>
                        <th>股票名称</th>
                        <th>实时价格</th>
                        <th>期望价格</th>
                        <th>买入还差</th>
                        <th>ss</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list viewList?sort_by("buyRate") as stockInfo>
                    <tr>
                        <td>${stockInfo_index+1}</td>
                        <td>${stockInfo.code}</td>
                        <td>${stockInfo.name}</td>
                        <td>${stockInfo.realtimePrice}</td>
                        <td>${stockInfo.buyPrice}</td>
                        <td>${stockInfo.buyRate}</td>
                        <td><a href="https://www.jisilu.cn/data/stock/${stockInfo.code}" target="_blank">历史PE/PB</a> </td>
                    </tr>
                    </#list>
                    </tbody>
                </table>
                </div>
            </div>
        </div>
    </div>
    </body>
</html>