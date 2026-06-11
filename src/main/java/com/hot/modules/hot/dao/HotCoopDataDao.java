package com.hot.modules.hot.dao;

import com.hot.modules.hot.entity.HotCoopData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HotCoopDataDao {

    HotCoopData get(@Param("id") Integer id);

    List<HotCoopData> list(HotCoopData data);

    int insert(HotCoopData data);

    int update(HotCoopData data);

    int delete(HotCoopData data);
}
