package org.lnson.member.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import org.lnson.member.service.impl.base.BaseService;
import org.lnson.member.pojo.Customer;
import org.lnson.member.service.MemberService;
import org.lnson.order.pojo.Product;
import org.lnson.service.common.JsonUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Service(interfaceClass = MemberService.class, group = "member-group", version = "1.0.0")
public class MemberServiceImpl extends BaseService implements MemberService {
    @Override
    public List<Customer> list() {
        return customerBaseDao.selectAll();
    }

    @Override
    public Customer get() {
        //测试调用订单服务
        List<Product> list = orderService.list();
        System.out.println(JsonUtils.to(list));
        return customerBaseDao.selectByPrimaryKey("DUMON");
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
