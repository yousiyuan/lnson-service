package org.lnson.member.service;

import org.lnson.member.pojo.Customer;

import java.util.List;

public interface MemberService {

    /**
     * 列表
     */
    public abstract List<Customer> list();

    /**
     * 查看
     */
    public abstract Customer get();

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
