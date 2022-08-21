package dao.impl;

import bean.Fruit;
import connection.ConnectionFactor;
import dao.FruitDAO;
import dao.basic.BasicDAO;
import message.Page;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class FruitDAOImpl extends BasicDAO<Fruit> implements FruitDAO {


    @Override
    public boolean addFruit(Connection connection, Fruit fruit) throws SQLException {
        String sql = "insert into t_fruit(fname,price,fcount,remark) values (?,?,?,?)";
        String name = fruit.getName();
        int price = fruit.getPrice();
        int count = fruit.getCount();
        String remark = fruit.getRemark();
        int cols = update(connection, sql, name, price, count, remark);
        if(cols > 0)
            return true;
        else
            return false;
    }

    @Override
    public boolean delFruitById(Connection connection, int id) throws SQLException {

        String sql = "delete from t_fruit where fid = ?";
        int cols = update(connection, sql, id);
        if(cols > 0)
            return true;
        else
            return false;
    }

    @Override
    public boolean delFruitByName(Connection connection, String name) throws SQLException {

        String sql = "delete from t_fruit where fname = ?";
        int cols = update(connection, sql, name);
        if(cols > 0)
            return true;
        else
            return false;
    }

    @Override
    public boolean setFruitById(Connection connection, Fruit fruit) throws SQLException {

        String sql = "update t_fruit set fname = ? , price = ? , fcount = ? , remark = ? where fid = ?";
        int id = fruit.getId();
        String name = fruit.getName();
        int price = fruit.getPrice();
        int count = fruit.getCount();
        String remark = fruit.getRemark();
        int cols = update(connection,sql,name,price,count,remark,id);
        if(cols > 0)
            return true;
        else
            return false;
    }

    @Override
    public boolean setFruitByName(Connection connection, Fruit fruit) throws SQLException {

        String sql = "update t_fruit set price = ? , fcount = ? , remark = ? where fname = ?";
        int id = fruit.getId();
        String name = fruit.getName();
        int price = fruit.getPrice();
        int count = fruit.getCount();
        String remark = fruit.getRemark();
        int cols = update(connection,sql,price,count,remark,name);
        if(cols > 0)
            return true;
        else
            return false;
    }

    @Override
    public Fruit getFruitById(Connection connection, int id) throws SQLException {
        String sql = "select fid `id`,fname `name`,price,fcount `count`,remark from t_fruit where fid = ?";
        Fruit fruit = null;
        List<Fruit> list = select(connection, sql, id);
        if(list == null)
            return null;
        fruit = list.get(0);
        return fruit;
    }

    @Override
    public Fruit getFruitByName(Connection connection, String name) throws SQLException {
        String sql = "select fid `id`,fname `name`,price,fcount `count`,remark from t_fruit where fname = ?";
        Fruit fruit = null;
        List<Fruit> list = select(connection, sql, name);
        if(list == null)
            return null;
        fruit = list.get(0);
        return fruit;
    }

    @Override
    public List<Fruit> getAllFruit(Connection connection) throws SQLException {
        String sql = "select fid `id`,fname `name`,price,fcount `count`,remark from t_fruit";
        Fruit fruit = null;
        List<Fruit> list = select(connection, sql);
        return list;
    }

    @Override
    public int getFruitCount(Connection connection,Page page) throws Exception {
        String sql = null;
        String keyword = null;
        if(page.getKeyword() == null)
            page.setKeyword("");
        sql = "select count(*) from t_fruit where fname like ?";
        keyword = "%" + page.getKeyword() + "%";
        return ((Long)selectSpecial(connection,sql,keyword)).intValue();
    }

    @Override
    public List<Fruit> getOnePageFruit(Connection connection, Page page) throws Exception {
        String sql = null;
        String keyword = null;
        List<Fruit> list = null;
        if(page.getKeyword() == null) {
            page.setKeyword("");
        }
        sql = "select fid `id`,fname `name`,price,fcount `count`,remark from t_fruit where fname like ? limit ?,5";
        keyword = "%" + page.getKeyword() + "%";
        list = select(connection, sql, keyword,(page.getPageNo() - 1) * 5);
        return list;
    }
}
