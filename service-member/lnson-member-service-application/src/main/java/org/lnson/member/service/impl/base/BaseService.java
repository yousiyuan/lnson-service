package org.lnson.member.service.impl.base;

import com.alibaba.dubbo.config.annotation.Reference;
import org.lnson.member.dao.CustomerDao;
import org.lnson.member.dao.base.BaseDao;
import org.lnson.member.pojo.Customer;
import org.lnson.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseService {

    @Autowired
    protected BaseDao<Customer> customerBaseDao;

    @Autowired
    protected CustomerDao customerDao;

    // 服务启动时的循环依赖问题，将check设置为false可使服务正确启动
    @Reference(group = "order-group", version = "1.0.0")
    protected OrderService orderService;

}
