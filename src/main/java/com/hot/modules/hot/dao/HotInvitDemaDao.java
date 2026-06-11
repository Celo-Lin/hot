package com.hot.modules.hot.dao;

import com.hot.modules.hot.entity.HotInvitDema;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HotInvitDemaDao {

    HotInvitDema get(@Param("id") Integer id);

    List<HotInvitDema> list(HotInvitDema dema);

    int insert(HotInvitDema dema);

    int update(HotInvitDema dema);

    int delete(@Param("id") Integer id);
}
