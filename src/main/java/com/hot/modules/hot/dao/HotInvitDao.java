package com.hot.modules.hot.dao;

import com.hot.modules.hot.entity.HotInvit;
import com.hot.modules.hot.entity.HotInvitDema;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HotInvitDao {

    HotInvit get(@Param("id") Integer id);

    List<HotInvit> list(HotInvit invit);

    int insert(HotInvit invit);

    int update(HotInvit invit);

    int delete(@Param("id") Integer id,@Param("userid") String userid);

    void insertDemaList(@Param("id")Integer id, @Param("userid")String userid, @Param("demaList")List<HotInvitDema> demaList);

    void deleteDema(@Param("id")Integer id, @Param("userid")String userid);

    void insertDema(HotInvitDema dema);

    void updateDema(HotInvitDema dema);

    int close(@Param("ids")String[] ids, @Param("userid")String userid);
}
