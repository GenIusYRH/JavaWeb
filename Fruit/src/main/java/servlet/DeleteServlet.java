package servlet;

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

//@WebServlet("/delete")
public class DeleteServlet extends ViewBaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection connection = null;

        try {
            connection = ConnectionFactor.getConnection();

            int id = Integer.parseInt(req.getParameter("id"));
            FruitDAO fruitDAO = new FruitDAOImpl();
            fruitDAO.delFruitById(connection,id);

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
