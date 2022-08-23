package servlet;

import message.Page;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//@WebServlet("/changePage")
public class ChangPageServlet extends ViewBaseServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pageOpt = req.getParameter("pageOpt");
        HttpSession session = req.getSession();
        Page page = null;
        try {
            page = (Page) session.getAttribute("page");
            if(page != null) {
                switch (pageOpt) {
                    case "first":
                        page.first();
                        break;
                    case "previous":
                        page.previous();
                        break;
                    case "next":
                        page.next();
                        break;
                    case "last":
                        page.last();
                        break;
                    default:
                        break;
                }
            }

            resp.sendRedirect("Onload");


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
