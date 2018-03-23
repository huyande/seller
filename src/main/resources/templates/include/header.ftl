<div class="n-head">
    <div class="g-doc f-cb">
        <div class="user">
        <#if user?exists>
            <#if user.type==1>卖家<#else>买家</#if>你好，<span class="name">${user.name}</span>！<a href="/api/logout">[退出]</a>
        <#else>
            请<a href="/page/login">[登录]</a>
        </#if>
        </div>
        <ul class="nav">
            <li><a href="/">首页</a></li>
            <#if user?exists && user.type==0>
            <li><a href="/orders/page/purchased">账务</a></li>
            <li><a href="/orders/page/shoppingcar">购物车</a></li>
            </#if>
            <#if user?exists && user.type==1>
            <li><a href="/commodity/page/create">发布</a></li>
            </#if>
        </ul>
    </div>
</div>