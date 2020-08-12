package org.lnson.order.service;

import org.lnson.order.pojo.Product;

import java.util.List;

public interface OrderService {

    /**
     * 列表
     */
    public abstract List<Product> list();

    /**
     * 查看
     */
    public abstract Product get();

    /**
     * 创建
     */
    public abstract Integer post();

    /**
     * 编辑
     */
    public abstract Integer put();

    /**
     * 删除
     */
    public abstract Integer delete();

}
