package com.hot.modules.hot.dao;

import com.hot.modules.hot.entity.HotCoopProduct;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HotCoopProductDao {

    HotCoopProduct get(@Param("id") Integer id);

    List<HotCoopProduct> list(HotCoopProduct product);

    int insert(HotCoopProduct product);

    int update(HotCoopProduct product);

    int delete(HotCoopProduct product);
}
