package net.tradercat.dao;

import net.tradercat.domain.TaskType;
import org.trianglex.common.database.mybatis.Page;

import java.util.List;

//@Mapper
public interface TaskTypeDAO {

    List<TaskType> getTaskTypes();

    int addTaskType(TaskType taskType);

    int updateTaskTypeById(TaskType taskType);

    List<TaskType> getTaskTypeByPaginate(Page<TaskType> page);
}
