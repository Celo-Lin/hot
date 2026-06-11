package com.hot.modules.hot.dao;

import com.hot.modules.hot.entity.Project;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProjectDao {

    Project get(@Param("id") Integer id);

    List<Project> list(Project project);

    int insert(Project project);

    int update(Project project);

    int delete(Project project);
}
