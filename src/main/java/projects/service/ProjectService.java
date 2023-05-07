package projects.service;

import projects.dao.ProjectDao;
import projects.entity.Project;

public class ProjectService {
    // ProjectDao object for accessing the project database
    private ProjectDao projectDao = new ProjectDao();

    // Add a new project to the database
    public Project addProject(Project project) {
        return projectDao.insertProject(project);


    }
}
