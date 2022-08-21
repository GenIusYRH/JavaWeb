package dao.impl;

import bean.Fruit;
import connection.ConnectionFactor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class FruitDAOTest {

    @Test
    public void test() {
        FruitDAOImpl fruitDAO = new FruitDAOImpl();
        Connection connection = null;

        try {
            connection = ConnectionFactor.getConnection();

            String sql = "SELECT count(*) FROM t_fruit";

            QueryRunner runner = new QueryRunner();

            ScalarHandler<Integer> handler = new ScalarHandler<>();

            System.out.println(runner.query(connection, sql, handler));

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                ConnectionFactor.closeResource(connection);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }
}
