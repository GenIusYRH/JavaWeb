package servlet;

import message.Page;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/init")
public class InitServlet extends ViewBaseServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        HttpSession session = req.getSession();
        try {
            Page page = new Page();
            session.setAttribute("page",page);

            resp.sendRedirect("Onload");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
