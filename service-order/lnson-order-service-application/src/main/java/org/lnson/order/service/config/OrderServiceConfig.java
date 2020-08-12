package org.lnson.order.service.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
// TODO - SpringBoot2.x整合dubbo
@Import(value = {JacksonConfig.class, RepositoryConfig.class})
@ComponentScan(basePackages = {"org.lnson.service.component", "org.lnson.order.dao"})
@MapperScan(basePackages = {"org.lnson.order.dao.mapper"})
public class OrderServiceConfig implements InitializingBean, DisposableBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("hello world");
    }

    @Override
    public void destroy() {
        System.out.println("bye world");
    }

}
