package com.hot.modules.hot.service;

import com.hot.modules.hot.dao.ProjectDao;
import com.hot.modules.hot.entity.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectDao projectDao;

    public Project get(Integer id) {
        return projectDao.get(id);
    }

    public List<Project> list(Project project) {
        return projectDao.list(project);
    }

    public int save(Project project) {
        if (project.getId() == null || project.getId().equals(0)) {
            return projectDao.insert(project);
        }
        return projectDao.update(project);
    }

    public int delete(Project project) {
        return projectDao.delete(project);
    }

    public int close(String[] ids, String uid) {
        return 1;
    }
}
