<html>
<head>
    <meta charset="utf-8">
    <title>Fuxiaoli-Stock</title>
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css"  rel="stylesheet" >
    <script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="#home" data-toggle="tab">主页</a></li>
    <li><a href="#hugeFallList2" data-toggle="tab">-85%</a></li>
    <li><a href="#hugeFallList" data-toggle="tab">-90%</a></li>
    <li><a href="#canBuyList" data-toggle="tab">买入<#if (canBuyList?size>0)><button type="button" class="btn btn-xs btn-danger ">${canBuyList?size}</button></#if></a></li>
    <li><a href="#addStock" data-toggle="tab">添加</a></li>
</ul>
<div class="tab-content">
    <div class="tab-pane fade in active" id="home">
        <table id="tableId" class="table table-condensed table-striped table-hover">
            <thead>
            <tr class="success">
                <th width="2%"></th>
                <th width="6%">代码</th>
                <th width="10%">名称</th>
                <th width="6%">最新</th>
                <th width="6%">涨跌</th>
                <th width="6%">期望</th>
                <th width="6%">买入差</th>
                <th width="6%">最大跌</th>
                <th width="5%">PB/PE</th>
                <th width="41%">备注</th>
                <th width="6%">操作</th>
            </tr>
            </thead>
            <tbody>
            <#list viewList?sort_by("buyRate") as stockInfo>
            <tr id='${stockInfo.code}'>
                <td>${stockInfo_index+1}</td>
                <td>${stockInfo.code}</td>
                <td>${stockInfo.name}</td>
                <td>${stockInfo.realTimePrice}</td>
                <td>${stockInfo.ratePercent}</td>
                <td>${stockInfo.buyPrice}</td>
                <td>${stockInfo.buyRate}</td>
                <td>${stockInfo.maxRate}</td>
                <td><small><small><a href="https://www.jisilu.cn/data/stock/${stockInfo.code}" target="_blank">PE/PB</a></small></small></td>
                <td><small><small>${stockInfo.description}</small></small></td>
                <td><small><small><button type="button" class="btn btn-primary btn-xs" onclick="deleteStock('${stockInfo.code}')">删除</button></small></small></td>
            </tr>
            </#list>
            </tbody>
        </table>
    </div>

    <div class="tab-pane fade in active" id="hugeFallList2">
        <table id="tableId" class="table table-condensed table-striped table-hover">
            <thead>
            <tr class="success">
                <th width="2%"></th>
                <th width="6%">代码</th>
                <th width="10%">名称</th>
                <th width="6%">最新</th>
                <th width="6%">涨跌</th>
                <th width="6%">期望</th>
                <th width="6%">买入差</th>
                <th width="6%">最大跌</th>
                <th width="5%">PB/PE</th>
                <th width="41%">备注</th>
                <th width="6%">操作</th>
            </tr>
            </thead>
            <tbody>
            <#list hugeFallList2?sort_by("buyRate") as stockInfo>
            <tr id='${stockInfo.code}'>
                <td>${stockInfo_index+1}</td>
                <td>${stockInfo.code}</td>
                <td>${stockInfo.name}</td>
                <td>${stockInfo.realTimePrice}</td>
                <td>${stockInfo.ratePercent}</td>
                <td>${stockInfo.buyPrice}</td>
                <td>${stockInfo.buyRate}</td>
                <td>${stockInfo.maxRate}</td>
                <td><small><small><a href="https://www.jisilu.cn/data/stock/${stockInfo.code}" target="_blank">PE/PB</a></small></small></td>
                <td><small><small>${stockInfo.description}</small></small></td>
                <td><small><small><button type="button" class="btn btn-primary btn-xs" onclick="deleteStock('${stockInfo.code}')">删除</button></small></small></td>
            </tr>
            </#list>
            </tbody>
        </table>
    </div>

    <div class="tab-pane fade in active" id="hugeFallList">
        <table id="tableId" class="table table-condensed table-striped table-hover">
            <thead>
            <tr class="success">
                <th width="2%"></th>
                <th width="6%">代码</th>
                <th width="10%">名称</th>
                <th width="6%">最新</th>
                <th width="6%">涨跌</th>
                <th width="6%">期望</th>
                <th width="6%">买入差</th>
                <th width="6%">最大跌</th>
                <th width="5%">PB/PE</th>
                <th width="41%">备注</th>
                <th width="6%">操作</th>
            </tr>
            </thead>
            <tbody>
            <#list hugeFallList?sort_by("buyRate") as stockInfo>
            <tr id='${stockInfo.code}'>
                <td>${stockInfo_index+1}</td>
                <td>${stockInfo.code}</td>
                <td>${stockInfo.name}</td>
                <td>${stockInfo.realTimePrice}</td>
                <td>${stockInfo.ratePercent}</td>
                <td>${stockInfo.buyPrice}</td>
                <td>${stockInfo.buyRate}</td>
                <td>${stockInfo.maxRate}</td>
                <td><small><small><a href="https://www.jisilu.cn/data/stock/${stockInfo.code}" target="_blank">PE/PB</a></small></small></td>
                <td><small><small>${stockInfo.description}</small></small></td>
                <td><small><small><button type="button" class="btn btn-primary btn-xs" onclick="deleteStock('${stockInfo.code}')">删除</button></small></small></td>
            </tr>
            </#list>
            </tbody>
        </table>
    </div>

    <div class="tab-pane" id="canBuyList">
        <table id="" class="table table-condensed table-striped table-hover">
            <thead>
            <tr>
                <th width="2%"></th>
                <th width="6%">代码</th>
                <th width="10%">名称</th>
                <th width="8%">实时价</th>
                <th width="8%">期望价</th>
                <th width="6%">买入差</th>
                <th width="6%">最大跌</th>
                <th width="6%">PB/PE</th>
                <th width="48%">备注</th>
            </tr>
            </thead>
            <tbody>
            <#list canBuyList?sort_by("buyRate") as stockInfo>
            <tr id='${stockInfo.code}'>
                <td>${stockInfo_index+1}</td>
                <td>${stockInfo.code}</td>
                <td>${stockInfo.name}</td>
                <td>${stockInfo.realTimePrice}</td>
                <td>${stockInfo.buyPrice}</td>
                <td>${stockInfo.buyRate}</td>
                <td>${stockInfo.maxRate}</td>
                <td><small><small><a href="https://www.jisilu.cn/data/stock/${stockInfo.code}" target="_blank">PE/PB</a></small></small></td>
                <td><small><small>${stockInfo.description}</small></small></td>
            </tr>
            </#list>
            </tbody>
        </table>
    </div>

    <div class="tab-pane" id="addStock">
        <form class="form-horizontal" role="form">
            <div class="form-group">
                <label for="stockCode" class="col-sm-2 control-label">代码</label>
                <div class="col-sm-8">
                    <input type="text" class="form-control" id="stockCode" name="stockCode" placeholder="601006">
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-8">
                    <button type="submit" class="btn btn-default" onclick="addStock()">保存</button>
                </div>
            </div>
        </form>
    </div>
</div>
<div>
    <hr/>
</div>
</body>
</html>
<script>
    $(function(){
        $('#tableId').on('dblclick','td',function(){
            var tdIndex = $(this).index();        //获取td索引
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
                        url: "http://39.105.142.63:8080/stock/update",
                        type: 'get',
                        async: true,
                        data: {
                            code:id,
                            value:oldVal,
                            tdIndex:tdIndex
                        },
                        dataType:'json',
                        success: function (data) {
                            alert("更新成功");
                        }
                    });
                }
                //closest：是从当前元素开始，沿Dom树向上遍历直到找到已应用选择器的一个匹配为止。
                $(this).closest('td').text(oldVal);
            });
        });
    });

    function deleteStock(code){
        var statu = confirm("确认删除？");
        if(!statu){
            return false;
        }
        $.ajax({
            url: "http://39.105.142.63:8080/stock/delete",
            type: 'get',
            async: true,
            data: {
                code:code
            },
            dataType:'json',
            success: function (data) {
                console.log(data);
            }
        });
    }
    function addStock(){
        var stockCode = $("#stockCode").val();
        if(stockCode==""){
            alert("代码不能为空");
            return false;
        }
        var statu = confirm("确认提交？");
        if(!statu){
            return false;
        }
        $.ajax({
            url: "http://39.105.142.63:8080/stock/add",
            type: 'get',
            async: true,
            data: {
                code:stockCode
            },
            dataType:'json',
            success: function (data) {
                console.log(data);
            }
        });
    }
</script>