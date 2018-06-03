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
    <li><a href="#canBuyList" data-toggle="tab">买入<#if (canBuyList?size>0)><button type="button" class="btn btn-xs btn-danger ">${canBuyList?size}</button></#if></a></li>
    <li><a href="#deletedList" data-toggle="tab">已删</a></li>
</ul>
<div class="tab-content">
    <div class="tab-pane fade in active" id="home">
        <table id="tableId" class="table table-condensed table-striped table-hover">
            <thead>
            <tr>
                <th width="2%"></th>
                <th width="6%">代码</th>
                <th width="10%">名称</th>
                <th width="7%">实时价</th>
                <th width="7%">期望价</th>
                <th width="6%">买入差</th>
                <th width="6%">最大跌</th>
                <th width="6%">PB/PE</th>
                <th width="38%">备注</th>
                <th width="12%">操作</th>
            </tr>
            </thead>
            <tbody>
            <#list viewList?sort_by("buyRate") as stockInfo>
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
                <td><button type="button" class="btn btn-primary btn-xs">修改</button> | <button type="button" class="btn btn-primary btn-xs" onclick="deleteStock('${stockInfo.code}')">删除</button></small></small></td>
            </tr>
            </#list>
            </tbody>
        </table>
    </div>

    <div class="tab-pane fade in active" id="canBuyList">
        <table id="tableId" class="table table-condensed table-striped table-hover">
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

    <div class="tab-pane fade in active" id="deletedList">
        <table id="tableId" class="table table-condensed table-striped table-hover">
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
                <th width="38%">备注</th>
                <th width="10%">操作</th>
            </tr>
            </thead>
            <tbody>
            <#if (deletedList?size>0)>
            <#list deletedList?sort_by("buyRate") as stockInfo>
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
                <td><button type="button" class="btn btn-primary btn-xs">撤销</button></small></small></td>
            </tr>
            </#list>
            </#if>
            </tbody>
        </table>
    </div>
</div>
<hr/>
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
                        url: "http://139.224.176.159:8080/stock/update",
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
        $.ajax({
            url: "http://139.224.176.159:8080/stock/delete",
            type: 'get',
            async: true,
            data: {
                code:code
            },
            dataType:'json',
            success: function (data) {
                alert("删除成功");
            }
        });
    }
</script>