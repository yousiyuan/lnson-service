package org.lnson.order.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import org.lnson.member.pojo.Customer;
import org.lnson.order.pojo.Product;
import org.lnson.order.service.OrderService;
import org.lnson.order.service.impl.base.BaseService;
import org.lnson.service.common.JsonUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Service(interfaceClass = OrderService.class, group = "order-group", version = "1.0.0")
public class OrderServiceImpl extends BaseService implements OrderService {
    @Override
    public List<Product> list() {
        // 测试调用会员服务
        List<Customer> list = memberService.list();
        System.out.println(JsonUtils.to(list));
        return productBaseDao.selectAll();
    }

    @Override
    public Product get() {
        return productBaseDao.selectByPrimaryKey(10);
    }

    @Override
    public Integer post() {
        return null;
    }

    @Override
    public Integer put() {
        return null;
    }

    @Override
    public Integer delete() {
        return null;
    }
}
