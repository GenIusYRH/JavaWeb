package dao.basic;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public abstract class BasicDAO<T> {

    private Class clas = null;
    {
        Type genericSuperclass = this.getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
        Type[] genericClass = parameterizedType.getActualTypeArguments();
        clas = (Class) genericClass[0];
    }

    //增删改
    public int update(Connection connection,String sql,Object... argv) throws SQLException {
        QueryRunner runner = new QueryRunner();
        return runner.update(connection,sql,argv);
    }

    //查询
    public List<T> select(Connection connection, String sql, Object... argv) throws SQLException {

        QueryRunner runner = new QueryRunner();

        BeanListHandler<T> handler = new BeanListHandler<T>(clas);

        return runner.query(connection, sql, handler, argv);

    }

    public <E> E selectSpecial(Connection connection,String sql, Object... argv) throws SQLException{
        QueryRunner runner = new QueryRunner();

        ScalarHandler<E> handler = new ScalarHandler<>();

        return runner.query(connection, sql, handler, argv);


    }




}
