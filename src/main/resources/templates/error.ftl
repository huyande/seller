<!DOCTYPE html>
<html>
<#include "/include/head.ftl">
<body>
<#include "/include/support.ftl">
<#include "/include/header.ftl">
<div class="g-doc">
    <#if errorMessage?exists>
    <div class="n-result">
        <h3>服务器可能开了个差！</h3>
        <p>${errorMessage}</p>
    </div>
    </#if>
</div>
<#include "/include/footer.ftl">
</body>
</html>