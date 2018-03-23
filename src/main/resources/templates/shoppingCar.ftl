<!DOCTYPE html>
<html>
<#include "/include/head.ftl">
<body>
<#include "/include/support.ftl">
<#include "/include/header.ftl">
<div class="g-doc" id="settleAccount">
<#assign total = 0>
    <#if !ordersList?exists || !ordersList?has_content>
        <div class="n-result">
            <h3>购物车为空！</h3>
        </div>
    <#else>
    <div class="m-tab m-tab-fw m-tab-simple f-cb" >
        <h2>已添加到购物车的内容</h2>
        <p id="jsonText" style="display: none">${jsonText}</p>
    </div>
    <table id="newTable" class="m-table m-table-row n-table g-b3">
        <thead><tr>
            <th>内容名称</th>
            <th>数量</th>
            <th>单价（元）</th>
        </tr>
        </thead>
        <tbody>
        <#list ordersList as orders>
            <#assign total = total + (orders.perPriceSnapshot * orders.purchasedQuantity)>
            <tr>
                <td>${orders.comTitle}</td>
                <td><span class="totalNum">${orders.purchasedQuantity?c}</span></td>
                <td>${orders.perPriceSnapshot?c}</td>
            </tr>
        </#list>
        </tbody>
        <tfoot>
        <tr>
            <td colspan="2"><div class="total">总计：</div></td>
            <td><span class="v-unit">¥</span><span class="value">${total?c}</span></td>
        </tr>
        </tfoot>
    </table>
    <div id="act-btn">
        <button class="u-btn u-btn-primary" id="back">退出</button>
        <span id="returnUrl" style="display: none">${returnUrl}</span>
        <button class="u-btn u-btn-primary" id="Account" >购买</button>
    </div>
    </#if>
</div>
<#include "/include/footer.ftl">
<script type="text/javascript" src="/js/global.js"></script>
<script type="text/javascript" src="/js/settleAccount.js"></script>
</body>
</html>