package semestrovaya.rakoedov4.dao;

import semestrovaya.rakoedov4.entity.Stage;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StageDAO {

    private final DataSource dataSource;

    public StageDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Stage> findAll() throws SQLException {
        List<Stage> stages = new ArrayList<>();
        String sql = "SELECT * FROM stage";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                stages.add(new Stage(
                        resultSet.getInt("stage_id"),
                        resultSet.getString("name"),
                        resultSet.getString("description")
                ));
            }
        }
        return stages;
    }
}
