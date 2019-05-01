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
    <li class="active"><a href="#hs" data-toggle="tab">主页</a></li>
    <li><a href="#hk" data-toggle="tab">港股</a></li>
    <li><a href="#addStock" data-toggle="tab">添加</a></li>
</ul>

<div class="tab-content">
    <div class="tab-pane fade in active" id="hs">
        <table class="table table-condensed table-striped table-hover">
            <thead>
            <tr class="success">
                <th width="2%"></th>
                <th width="16%">股票</th>
                <th width="4%">最新</th>
                <th width="4%">涨跌</th>
                <th width="4%">期望</th>
                <th width="10%">买入差（最大跌）</th>
                <th width="4%">PB/PE</th>
                <th width="50%">备注</th>
                <th width="6%">操作</th>
            </tr>
            </thead>
            <tbody>
            <#list hsStocks as stock>
            <#if stock.buyRateDouble<0>
            <tr id='${stock.code}' class='danger'>
			<#elseif (stock.buyRateDouble>=0 && stock.buyRateDouble<0.05)>
			<tr id='${stock.code}' class='warning'>
			<#elseif (stock.buyRateDouble>=0.05 && stock.buyRateDouble<0.1)>
			<tr id='${stock.code}' class='info'>
			<#else>
			<tr id='${stock.code}'>
			</#if>
                <td>${stock_index+1}</td>
                <td>${stock.code} ${stock.name}</td>
                <td>${stock.realTimePrice}</td>
                <td>${stock.ratePercent}</td>
                <td>${stock.buyPrice}</td>
                <td>${stock.buyRate}（${stock.maxRate}）</td>
                <td><small><small><a href="${stock.PBPEUrl}" target="_blank">PE/PB</a></small></small></td>
                <td><small><small>${stock.description}</small></small></td>
                <td><small><small><button type="button" class="btn btn-primary btn-xs" onclick="deleteStock('${stock.code}')">删除</button></small></small></td>
            </tr>
            </#list>
            </tbody>
        </table>
    </div>

    <!-- 港股 -->
    <div class="tab-pane" id="hk">
        <table id="tableId" class="table table-condensed table-striped table-hover">
            <thead>
            <tr class="success">
                <th width="2%"></th>
                <th width="16%">股票</th>
                <th width="4%">最新</th>
                <th width="4%">涨跌</th>
                <th width="4%">期望</th>
                <th width="10%">买入差（最大跌）</th>
                <th width="54%">备注</th>
                <th width="6%">操作</th>
            </tr>
            </thead>
            <tbody>
            <#list hkStocks as stock>
            <#if stock.buyRateDouble<0>
            <tr id='${stock.code}' class='danger'>
			<#elseif (stock.buyRateDouble>=0 && stock.buyRateDouble<0.05)>
			<tr id='${stock.code}' class='warning'>
			<#elseif (stock.buyRateDouble>=0.05 && stock.buyRateDouble<0.1)>
			<tr id='${stock.code}' class='info'>
			<#else>
			<tr id='${stock.code}'>
			</#if>
                <td>${stock_index+1}</td>
                <td>${stock.code} ${stock.name}</td>
                <td>${stock.realTimePrice}</td>
                <td>${stock.ratePercent}</td>
                <td>${stock.buyPrice}</td>
                <td>${stock.buyRate}（${stock.maxRate}）</td>
                <td><small><small>${stock.description}</small></small></td>
                <td><small><small><button type="button" class="btn btn-primary btn-xs" onclick="deleteStock('${stock.code}')">删除</button></small></small></td>
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
                    <input type="text" class="form-control" id="stockCode" name="stockCode" placeholder="">
                </div>
            </div>
            <div class="form-group">
                <label for="stockName" class="col-sm-2 control-label">名称</label>
                <div class="col-sm-8">
                    <input type="text" class="form-control" id="stockName" name="stockName" placeholder="">
                </div>
            </div>
            <div class="form-group">
                <label for="maxPrice" class="col-sm-2 control-label">最高价格</label>
                <div class="col-sm-8">
                    <input type="text" class="form-control" id="maxPrice" name="maxPrice" placeholder="">
                </div>
            </div>
            <div class="form-group">
                <label for="buyPrice" class="col-sm-2 control-label">买入价格</label>
                <div class="col-sm-8">
                    <input type="text" class="form-control" id="buyPrice" name="buyPrice" placeholder="">
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-8">
                    <button type="button" class="btn btn-default" onclick="addStock()">保存</button>
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
            url: "http://127.0.0.1:8080/stock/delete",
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
        
        var stockName = $("#stockName").val();
        if(stockName==""){
            alert("名称不能为空");
            return false;
        }
        
        var maxPrice = $("#maxPrice").val();
        if(maxPrice==""){
            alert("最高价格不能为空");
            return false;
        }
        
        var buyPrice = $("#buyPrice").val();
        if(buyPrice==""){
            alert("期待买入价格不能为空");
            return false;
        }
        
        var statu = confirm("确认提交？");
        if(!statu){
            return false;
        }
        
        $.ajax({
            url: "http://127.0.0.1:8080/stock/add",
            type: 'get',
            async: true,
            data: {
                code:stockCode,
                name:stockName,
                maxPrice:maxPrice,
                buyPrice:buyPrice
            },
            dataType:'json',
            success: function (data) {
                console.log(data);
            }
        });
    }
</script>