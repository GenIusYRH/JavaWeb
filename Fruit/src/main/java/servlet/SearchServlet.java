package servlet;

import message.Page;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/search")
public class SearchServlet extends ViewBaseServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String keyword = req.getParameter("keyword");
        HttpSession session = req.getSession();

        try {
            Page page = (Page) session.getAttribute("page");
            page.setKeyword(keyword);
            page.first();


            resp.sendRedirect("Onload");


        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}
