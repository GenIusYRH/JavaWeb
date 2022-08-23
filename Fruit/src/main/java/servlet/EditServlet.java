package servlet;

import dao.FruitDAO;
import dao.impl.FruitDAOImpl;
import bean.Fruit;
import connection.ConnectionFactor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

//@WebServlet("/edit")
public class EditServlet extends  ViewBaseServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("edit来了");

        Connection connection = null;
        try {
            String id = req.getParameter("id");
            if(id != null) {
                connection = ConnectionFactor.getConnection();
                FruitDAO fruitDAO = new FruitDAOImpl();
                Fruit fruit = fruitDAO.getFruitById(connection, Integer.parseInt(id));

                HttpSession session = req.getSession();
                req.setAttribute("fruit",fruit);

                super.processTemplate("edit",req,resp);

            }




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
