<html>
    <head>
        <meta charset="utf-8">
        <title>Fuxiaoli-Stock</title>
        <link href="https://cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">
        <script src="http://code.jquery.com/jquery-1.10.2.js"></script>
        <script>
            $(function(){
                $('#tableId').on('dblclick','td',function(){
                    var id = $(this).parent().attr("id");
                    var oldVal = $(this).text();
                    var input = "<input type='text' style='width:100px' id='tmpId' value='" + oldVal + "' >";
                    $(this).text('');
                    $(this).append(input);
                    $('#tmpId').focus();
                    $('#tmpId').blur(function(){
                        var newVal = $(this).val();
                        if(newVal != oldVal){
                            oldVal = $(this).val();
                            // 调用后台逻辑修改
                            $.ajax({
                                url: "http://127.0.0.1:8080/stock/update",
                                type: 'get',
                                async: true,
                                data: {
                                    code:id,
                                    buyPrice:oldVal
                                },
                                dataType:'json',
                                success: function (data) {

                                }
                            });

                        }
                        //closest：是从当前元素开始，沿Dom树向上遍历直到找到已应用选择器的一个匹配为止。
                        $(this).closest('td').text(oldVal);
                    });
                });
            });
        </script>
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
                <table id="tableId" class="table table-condensed table-striped table-hover">
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
                    <tr id='${stockInfo.code}'>
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