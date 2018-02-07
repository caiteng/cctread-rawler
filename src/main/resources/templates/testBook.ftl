<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="GBK"/>
    <title>Title</title>
    <script src="static/test.js"></script>
</head>
<body>
<#list book? keys as key>
text：${key} , url:${book["${key}"]}
<br>
</#list>
</body>
</html>