package dao;

import bean.Fruit;
import connection.ConnectionFactor;
import message.Page;

import java.sql.Connection;
import java.util.List;

public interface FruitDAO {

    //添加水果
    boolean addFruit(Connection connection, Fruit fruit) throws Exception;

    //根据id删除水果
    boolean delFruitById(Connection connection,int id) throws Exception;

    //根据名字删除水果
    boolean delFruitByName(Connection connection,String name) throws Exception;

    //根据id修改水果
    boolean setFruitById(Connection connection,Fruit fruit) throws Exception;

    //根据名字修改水果
    boolean setFruitByName(Connection connection,Fruit fruit) throws Exception;

    //根据id查询水果信息
    Fruit getFruitById(Connection connection,int id) throws Exception;

    //根据名字查询水果信息
    Fruit getFruitByName(Connection connection,String name) throws Exception;

    //查询所有水果
    List<Fruit> getAllFruit(Connection connection) throws Exception;

    //查询总水果数
    int getFruitCount(Connection connection,Page page) throws Exception;

    //查询一页水果
    List<Fruit> getOnePageFruit(Connection connection,  Page page) throws Exception;


}
