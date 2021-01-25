package com.dao;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author qbanxiaoli
 * @description mybatis通用mapper
 * @create 2018/7/3 13:22
 */
public interface TkMapper<T> extends Mapper<T>, MySqlMapper<T> {

}
