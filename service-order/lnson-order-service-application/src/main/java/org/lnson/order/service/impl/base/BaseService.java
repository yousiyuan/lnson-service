package org.lnson.order.service.impl.base;

import com.alibaba.dubbo.config.annotation.Reference;
import org.lnson.member.service.MemberService;
import org.lnson.order.dao.ProductDao;
import org.lnson.order.dao.base.BaseDao;
import org.lnson.order.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseService {

    @Autowired
    protected BaseDao<Product> productBaseDao;

    @Autowired
    protected ProductDao productDao;

    @Reference(group = "member-group", version = "1.0.0")
    protected MemberService memberService;

}
