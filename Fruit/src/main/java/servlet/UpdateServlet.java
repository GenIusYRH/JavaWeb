package servlet;

import bean.Fruit;
import connection.ConnectionFactor;
import dao.FruitDAO;
import dao.impl.FruitDAOImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/update")
public class UpdateServlet extends ViewBaseServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("utf-8");
        Connection connection = null;

        try {

            connection = ConnectionFactor.getConnection();
            String id = req.getParameter("id");
            String name = req.getParameter("name");
            String price = req.getParameter("price");
            String count = req.getParameter("count");
            String remark = req.getParameter("remark");
            Fruit fruit = new Fruit(Integer.parseInt(id),name,Integer.parseInt(price), Integer.parseInt(count), remark);
            FruitDAO fruitDAO = new FruitDAOImpl();
            fruitDAO.setFruitById(connection,fruit);

            resp.sendRedirect("Onload");


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
