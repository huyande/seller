<!DOCTYPE html>
<html>
<#include "/include/head.ftl">
<body>
<#include "/include/support.ftl">
<#include "/include/header.ftl">
<div class="g-doc" id="settleAccount">

    <#if !ordersList?exists || !ordersList?has_content>
        div class="m-tab m-tab-fw m-tab-simple f-cb" >
        <h2>购物车为空</h2>
    <#else>
    <div class="m-tab m-tab-fw m-tab-simple f-cb" >
        <h2>已添加到购物车的内容</h2>
        <p id="jsonText" style="display: none">${jsonText}</p>
    </div>
    <table id="newTable" class="m-table m-table-row n-table g-b3">
        <tr>
            <th>内容名称</th>
            <th>数量</th>
            <th>单价（元）</th>
        </tr>
        <#list ordersList as orders>
            <tr>
                <td>${orders.comTitle}</td>
                <td>
                    <span class="lessNum">-</span>
                    <span class="totalNum">${orders.purchasedQuantity?c}</span>
                    <span class="thisId" style="display: none">${orders.id}</span>
                    <span class="moreNum">+</span>
                </td>
                <td>${orders.perPriceSnapshot?c}</td>
            </tr>
        </#list>
    </table>
    <div id="act-btn"><button class="u-btn u-btn-primary" id="back">退出</button>
        <button class="u-btn u-btn-primary" id="Account">购买</button></div>
    </#if>
</div>
<#include "/include/footer.ftl">
<script type="text/javascript" src="../js/global.js"></script>
<script type="text/javascript" src="../js/settleAccount.js"></script>
</body>
</html>