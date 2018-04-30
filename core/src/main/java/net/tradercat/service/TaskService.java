package net.tradercat.service;

import net.tradercat.dao.TaskDAO;
import net.tradercat.domain.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.trianglex.common.database.DataSource;
import org.trianglex.common.database.mybatis.Page;

import java.util.List;

import static net.tradercat.constant.DataSourceNames.TRADERCAT;

@Service
public class TaskService {

    @Autowired
    private TaskDAO taskDAO;

    @DataSource(TRADERCAT)
    public List<Task> getTaskByPaginate(Page<Task> page, Integer userId) {
        return taskDAO.getTaskByPaginate(page, userId);
    }

}
