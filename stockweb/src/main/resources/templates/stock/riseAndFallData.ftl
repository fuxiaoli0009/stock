<html>
<head>
    <meta charset="utf-8">
    <title>绘制柱状图</title>
    <style type="text/css">

        ul,li,dl,dd,dt,p{margin:0;padding:0;}
        ul,li{list-style:none;}
        .histogram-container{position:relative;margin-left:60px;margin-top:10px;margin-bottom:25px;}
        .histogram-bg-line{border:#999 solid;border-width:0 1px 1px 1px;border-right-color:#eee;overflow:hidden;width:99%;}
        .histogram-bg-line ul{overflow:hidden;border:#eee solid;border-width:1px 0 0 0;}
        .histogram-bg-line li{float:left;width:20%;/*根据.histogram-bg-line下的ul里面li标签的个数来控制比例*/overflow:hidden;}
        .histogram-bg-line li div{border-right:1px #eee solid;}
        .histogram-content{position:absolute;left:0px;top:0;width:99%;height:100%;}
        .histogram-content ul{height:100%;}
        .histogram-content li{float:left;height:100%;width:12%;/*根据直方图的个数来控制这个width比例*/text-align:center;position:relative;}
        .histogram-box{position:relative;display:inline-block;height:100%;width:20px;}
        .histogram-content li a{position:absolute;bottom:0;right:0;display:block;width:100px;}
        .histogram-content li .name{position:absolute;bottom:-20px;left:0px;white-space:nowrap;display:inline-block;width:100%;font-size:12px;}
        .histogram-y{position:absolute;left:-60px;top:-10px;font:12px/1.8 verdana,arial;}
        .histogram-y li{text-align:right;width:55px;padding-right:5px;color:#333;}
        .histogram-bg-line li div,.histogram-y li{height:60px;/*控制单元格的高度及百分比的高度，使百分数与线条对齐*/}
    </style>
</head>
<body>
<div class="container">
<div class="row clearfix">
    <div class="col-md-12 column">
        <h3 class="text-center text-info">
            沪深股票涨跌比例分布图
        </h3>
    </div>
<div class="histogram-container" id="histogram-container">
    <!--背景方格-->
    <div class="histogram-bg-line">
        <ul>
            <li><div></div></li>
            <li><div></div></li>
            <li><div></div></li>
            <li><div></div></li>
        </ul>
        <ul>
            <li><div></div></li>
            <li><div></div></li>
            <li><div></div></li>
            <li><div></div></li>
        </ul>
        <ul>
            <li><div></div></li>
            <li><div></div></li>
            <li><div></div></li>
            <li><div></div></li>
        </ul>
        <ul>
            <li><div></div></li>
            <li><div></div></li>
            <li><div></div></li>
            <li><div></div></li>
        </ul>
        <ul>
            <li><div></div></li>
            <li><div></div></li>
            <li><div></div></li>
            <li><div></div></li>
        </ul>
    </div>
    <!--柱状条-->
    <div class="histogram-content">
        <ul>
            <li>
                <span class="histogram-box"><a style="height:${stockRiseAndFall.down7To10Percentage};background:gray;" title="${stockRiseAndFall.down7To10}"></a></span>
                <span class="name">-7%~-10%</span>
            </li>
            <li>
                <span class="histogram-box"><a style="height:${stockRiseAndFall.down5To7Percentage};background:orange;" title="${stockRiseAndFall.down5To7}"></a></span>
                <span class="name">-5%~-7%</span>
            </li>
            <li>
                <span class="histogram-box"><a style="height:${stockRiseAndFall.down3To5Percentage};background:green;" title="${stockRiseAndFall.down3To5}"></a></span>
                <span class="name">-3%~-5%</span>
            </li>
            <li>
                <span class="histogram-box"><a style="height:${stockRiseAndFall.down0To3Percentage};background:#2f87d9;" title="${stockRiseAndFall.down0To3}"></a></span>
                <span class="name">-3%~0%</span>
            </li>
            <li>
                <span class="histogram-box"><a style="height:${stockRiseAndFall.up0To3Percentage};background:#2f87d9;" title="${stockRiseAndFall.up0To3}"></a></span>
                <span class="name">0%~3%</span>
            </li>
            <li>
                <span class="histogram-box"><a style="height:${stockRiseAndFall.up3To5Percentage};background:green;" title="${stockRiseAndFall.up3To5}"></a></span>
                <span class="name">3%~5%</span>
            </li>
            <li>
                <span class="histogram-box"><a style="height:${stockRiseAndFall.up5To7Percentage};background:orange;" title="${stockRiseAndFall.up5To7}"></a></span>
                <span class="name">5%~7%</span>
            </li>
            <li>
                <span class="histogram-box"><a style="height:${stockRiseAndFall.up7To10Percentage};background:gray;" title="${stockRiseAndFall.up7To10}"></a></span>
                <span class="name">7%~10%</span>
            </li>
        </ul>
    </div>

</div></div></div>
</body>
</html>