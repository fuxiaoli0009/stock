<html>
<head>
    <meta charset="utf-8">
    <title>Fuxiaoli</title>
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css"  rel="stylesheet" >
    <script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<h5>上证：${szRatePercent}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;创指：${czRatePercent}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;科创：${starAverageRatePercent}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;自选：${hsAverageRatePercent}</h5>
<ul class="nav nav-tabs">
    <li class="active"><a href="#hs" data-toggle="tab">主页-${source}</a></li>
    <li><a href="#starStock" data-toggle="tab">科创</a></li>
    <li><a href="#hk" data-toggle="tab">港股</a></li>
    <li><a href="#addStock" data-toggle="tab">添加</a></li>
</ul>

<div class="tab-content" id="totalId">
    <div class="tab-pane fade in active" id="hs">
        <table id="tableId" class="table table-condensed table-striped table-hover">
            <thead>
            <tr class="success">
                <th width="2%"></th>
                <th width="15%">股票</th>
                <th width="4%">最新</th>
                <th width="4%">涨跌</th>
                <th width="4%">期望</th>
                <th width="12%">买入差（最大跌）</th>
                <th width="4%">PB/PE</th>
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
                <td>&nbsp;</td>
                <td>${stock.code} ${stock.name}</td>
                <td>${stock.realTimePrice}</td>
                <td>${stock.ratePercent}</td>
                <td id='${stock.code}' ondblclick="updateColumn($(this), 'buyprice')">${stock.buyPrice}</td>
                <td>${stock.buyRate}（${stock.maxRate}）</td>
                <td><small><small><a href="${stock.PBPEUrl}" target="_blank">PE/PB</a></small></small></td>
                <td><small><small><button type="button" class="btn btn-primary btn-xs" onclick="deleteStock('${stock.code}')">删除</button></small></small></td>
            </tr>
            <#if stock.buyRateDouble<0>
            <tr id='${stock.code}' class='danger'>
			<#elseif (stock.buyRateDouble>=0 && stock.buyRateDouble<0.05)>
			<tr id='${stock.code}' class='warning'>
			<#elseif (stock.buyRateDouble>=0.05 && stock.buyRateDouble<0.1)>
			<tr id='${stock.code}' class='info'>
			<#else>
			<tr id='${stock.code}'>
			</#if>
                <td id='${stock.code}' colspan="8" ondblclick="updateColumn($(this), 'description')">&nbsp;&nbsp;${stock.description}</td>
            </tr>
            </#list>
            </tbody>
        </table>
    </div>
    
    <!-- 科创 -->
    <div class="tab-pane" id="starStock">
        <table id="tableId" class="table table-condensed table-striped table-hover">
            <thead>
            <tr class="success">
            	<th width="2%"></th>
                <th width="15%">股票</th>
                <th width="4%">最新</th>
                <th width="4%">涨跌</th>
                <th width="4%">期望</th>
                <th width="12%">买入差（最大跌）</th>
                <th width="4%">PB/PE</th>
                <th width="6%">操作</th>
            </tr>
            </thead>
            <tbody>
            <#list starStocks as stock>
            <#if stock.buyRateDouble<0>
            <tr id='${stock.code}' class='danger'>
			<#elseif (stock.buyRateDouble>=0 && stock.buyRateDouble<0.05)>
			<tr id='${stock.code}' class='warning'>
			<#elseif (stock.buyRateDouble>=0.05 && stock.buyRateDouble<0.1)>
			<tr id='${stock.code}' class='info'>
			<#else>
			<tr id='${stock.code}'>
			</#if>
				<td>&nbsp;</td>
                <td>${stock.code} ${stock.name}</td>
                <td>${stock.realTimePrice}</td>
                <td>${stock.ratePercent}</td>
                <td id='${stock.code}' ondblclick="updateColumn($(this), 'buyprice')">${stock.buyPrice}</td>
                <td>${stock.buyRate}（${stock.maxRate}）</td>
                <td><small><small><a href="${stock.PBPEUrl}" target="_blank">PE/PB</a></small></small></td>
                <td><small><small><button type="button" class="btn btn-primary btn-xs" onclick="deleteStock('${stock.code}')">删除</button></small></small></td>
            </tr>
            <#if stock.buyRateDouble<0>
            <tr id='${stock.code}' class='danger'>
			<#elseif (stock.buyRateDouble>=0 && stock.buyRateDouble<0.05)>
			<tr id='${stock.code}' class='warning'>
			<#elseif (stock.buyRateDouble>=0.05 && stock.buyRateDouble<0.1)>
			<tr id='${stock.code}' class='info'>
			<#else>
			<tr id='${stock.code}'>
			</#if>
                <td id='${stock.code}' colspan="8" ondblclick="updateColumn($(this), 'description')">&nbsp;&nbsp;${stock.description}</td>
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
                <th width="15%">股票</th>
                <th width="4%">最新</th>
                <th width="4%">涨跌</th>
                <th width="4%">期望</th>
                <th width="12%">买入差（最大跌）</th>
                <th width="4%">PB/PE</th>
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
				<td>&nbsp;</td>
                <td>${stock.code} ${stock.name}</td>
                <td>${stock.realTimePrice}</td>
                <td>${stock.ratePercent}</td>
                <td id='${stock.code}' ondblclick="updateColumn($(this), 'buyprice')">${stock.buyPrice}</td>
                <td>${stock.buyRate}（${stock.maxRate}）</td>
                <td><small><small><a href="${stock.PBPEUrl}" target="_blank">PE/PB</a></small></small></td>
                <td><small><small><button type="button" class="btn btn-primary btn-xs" onclick="deleteStock('${stock.code}')">删除</button></small></small></td>
            </tr>
            <#if stock.buyRateDouble<0>
            <tr id='${stock.code}' class='danger'>
			<#elseif (stock.buyRateDouble>=0 && stock.buyRateDouble<0.05)>
			<tr id='${stock.code}' class='warning'>
			<#elseif (stock.buyRateDouble>=0.05 && stock.buyRateDouble<0.1)>
			<tr id='${stock.code}' class='info'>
			<#else>
			<tr id='${stock.code}'>
			</#if>
                <td id='${stock.code}' colspan="8" ondblclick="updateColumn($(this), 'description')">&nbsp;&nbsp;${stock.description}</td>
            </tr>
            </#list>
            </tbody>
        </table>
    </div>

    <div class="tab-pane" id="addStock">
        <form method="post" enctype="multipart/form-data" id="form" action="http://118.24.211.79:8081/excel/parse">
		    <input type="file" name="filename"/>
		    <input type="submit" value="提交上传"/>
		</form>
		格式：type、code、name、maxValue
    </div>
</div>
<div>
    <hr/>
</div>
</body>
</html>
<script>
    //var homeUrl = "http://127.0.0.1:8081/api/";
    var homeUrl = "http://39.105.142.63:8081/api/";
    function updateColumn(tdObject, column){
        var id = tdObject.attr("id");
        var oldVal = tdObject.text();
        console.log("id:"+id+", oldVal:"+oldVal);
        var input = "<input type='text' style='width:100%' id='tmpId' value='" + oldVal + "' >";
        tdObject.text('');
        tdObject.append(input);
        $('#tmpId').focus();
        $('#tmpId').blur(function(){
            var newVal = $(this).val();
            if(newVal != oldVal){
                oldVal = $(this).val();
                // 调用后台逻辑修改
                $.ajax({
                    url: homeUrl + "update",
                    type: 'get',
                    async: true,
                    data: {
                        code:id,
                        value:oldVal,
                        column:column
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
    }
    
    function deleteStock(code){
        var statu = confirm("确认删除【"+code+"】"+"?");
        if(!statu){
            return false;
        }
        $.ajax({
            url: homeUrl + "delete",
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
    
</script>