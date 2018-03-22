package com.netease;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement//开启事务管理
@SpringBootApplication
public class SellerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SellerApplication.class, args);
    }
}
