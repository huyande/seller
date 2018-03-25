package com.netease.configuration;

import com.netease.interceptor.BuyerCheckInterceptor;
import com.netease.interceptor.LoginInterceptor;
import com.netease.interceptor.SellerCheckInterceptor;
import com.netease.interceptor.TicketCheckInterceptor;
import com.netease.utils.StringAndFileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.File;
import java.util.ArrayList;

/**
 * @author zhanglbjames@163.com
 * @version Created on 18-3-19.
 */
@PropertySource(value = {"classpath:conf.properties"}, encoding = "utf-8")
@Component
public class SellerWebConfiguration extends WebMvcConfigurerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(SellerWebConfiguration.class);

    @Autowired
    TicketCheckInterceptor ticketCheckInterceptor;

    @Autowired
    LoginInterceptor loginInterceptor;

    @Autowired
    BuyerCheckInterceptor buyerCheckInterceptor;

    @Autowired
    SellerCheckInterceptor sellerCheckInterceptor;

    @Value(("${STATIC_STORAGE_MAPPER_PATH}"))
    private String STATIC_STORAGE_MAPPER_PATH;

    /**
     * 拦截器
     */

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        ArrayList<String> onlyAllowBuyerPath = new ArrayList<String>();
        onlyAllowBuyerPath.add("/page/notbuy");
        onlyAllowBuyerPath.add("/orders/*");

        ArrayList<String> onlyAllowSellerPath = new ArrayList<String>();
        onlyAllowSellerPath.add("/commodity/api/*");
        onlyAllowSellerPath.add("/commodity/page/edit/*");
        onlyAllowSellerPath.add("/commodity/page/create");

        ArrayList<String> onlyAllowLoginUserPath = new ArrayList<String>();
        onlyAllowLoginUserPath.addAll(onlyAllowBuyerPath);
        onlyAllowLoginUserPath.addAll(onlyAllowSellerPath);

        registry.addInterceptor(ticketCheckInterceptor);//对所有请求进行拦截
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns(onlyAllowLoginUserPath.toArray(new String[0]));
        registry.addInterceptor(buyerCheckInterceptor)
                .addPathPatterns(onlyAllowBuyerPath.toArray(new String[0]));
        registry.addInterceptor(sellerCheckInterceptor).
                addPathPatterns(onlyAllowSellerPath.toArray(new String[0]));

        super.addInterceptors(registry);
    }

    /**
     * 追加图片静态存取路径配置
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        String path = getPath();
        if (path != null) {
            // **表示匹配以请求静态资源路径STATIC_STORAGE_PATH开头的所有请求映射
            registry.addResourceHandler(STATIC_STORAGE_MAPPER_PATH + "**")
                    // 指定文件真实存储路径，
                    // file表示是系统文件目录，
                    // classpath指的是项目classpath路径
                    .addResourceLocations("file:" + path);
            logger.info("静态文件路径创建成功 path:" + path);
        } else
            logger.error("静态路径创建失败");
    }

    /**
     * 在用户目录创建与静态跟路径同名的目录
     * Linux/win路径处理
     */
    private String getPath() {
        String separator = File.separator;
        String storagePath = System.getProperty("user.home") +
                StringAndFileUtils.replaceAllCharVersion( //先把默认的分割符去掉
                        STATIC_STORAGE_MAPPER_PATH, '/',
                        separator.charAt(0));

        File dir = new File(storagePath);
        boolean createdDir = true;
        if (dir.exists()) {
            if (!dir.isDirectory()) {
                dir.delete();
                createdDir = dir.mkdirs();
            }
        } else {
            createdDir = dir.mkdirs();
        }
        return createdDir ? storagePath : null;
    }

}
