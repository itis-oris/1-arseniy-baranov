package semestrovaya.rakoedov4.service;

import semestrovaya.rakoedov4.dao.StageDAO;
import semestrovaya.rakoedov4.entity.Stage;

import java.sql.SQLException;
import java.util.List;

public class StageService {

    StageDAO stageDAO;

    public StageService(StageDAO stageDAO) {
        this.stageDAO = stageDAO;
    }

    public List<Stage> findAll() throws SQLException {
        return stageDAO.findAll();
    }
}
