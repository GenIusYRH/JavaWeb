package servlet;

import bean.Fruit;
import connection.ConnectionFactor;
import dao.FruitDAO;
import dao.impl.FruitDAOImpl;
import message.Page;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

//@WebServlet("/Onload")
public class OnloadServlet extends  ViewBaseServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        Connection connection = null;


        try {

            connection = ConnectionFactor.getConnection();
            Page page = (Page) session.getAttribute("page");


            FruitDAO fruitDAO = new FruitDAOImpl();
            List<Fruit> fruitList = fruitDAO.getOnePageFruit(connection,page);
            session.setAttribute("fruitList",fruitList);


            super.processTemplate("Hello",req,resp);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
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
