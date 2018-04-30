package net.tradercat.service;

import net.tradercat.dao.TaskTypeDAO;
import net.tradercat.domain.TaskType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.trianglex.common.database.DataSource;
import org.trianglex.common.database.mybatis.Page;

import java.util.List;

import static net.tradercat.constant.DataSourceNames.TRADERCAT;

@Service
public class TaskTypeService {

    @Autowired
    private TaskTypeDAO taskTypeDAO;

    @DataSource(TRADERCAT)
    void getTaskTypeByPaginate(Page<TaskType> page) {
        taskTypeDAO.getTaskTypeByPaginate(page);
    }

    @DataSource(TRADERCAT)
    List<TaskType> getTaskTypes() {
        return taskTypeDAO.getTaskTypes();
    }

    @DataSource(TRADERCAT)
    public boolean addTaskType(TaskType taskType) {
        return taskTypeDAO.addTaskType(taskType) > 0;
    }

    @DataSource(TRADERCAT)
    public boolean updateTaskTypeById(TaskType taskType) {
        return taskTypeDAO.updateTaskTypeById(taskType) > 0;
    }

}
