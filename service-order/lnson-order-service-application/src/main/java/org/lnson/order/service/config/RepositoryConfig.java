package org.lnson.order.service.config;

import org.lnson.order.dao.base.BaseDao;
import org.lnson.order.pojo.Customer;
import org.lnson.order.pojo.Product;
import org.springframework.context.annotation.Bean;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public class RepositoryConfig {

    @Bean
    public BaseDao<Product> productBaseDao(Mapper<Product> mapper, MySqlMapper<Product> mySqlMapper, IdsMapper<Product> idsMapper) {
        return new BaseDao<Product>(mapper, mySqlMapper, idsMapper) {
        };
    }

    @Bean
    public BaseDao<Customer> customerBaseDao(Mapper<Customer> mapper, MySqlMapper<Customer> mySqlMapper, IdsMapper<Customer> idsMapper) {
        return new BaseDao<Customer>(mapper, mySqlMapper, idsMapper) {
        };
    }

}
