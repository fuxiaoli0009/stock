<!DOCTYPE html>
<head>
    <meta charset="UTF-8"/>
    <title>上传文件</title>
</head>
<body>
<form method="post" enctype="multipart/form-data" id="form" action="http://localhost:8081/excel/parse">
    <input type="file" name="filename"/>
    <input type="submit" value="提交上传"/>
</form>