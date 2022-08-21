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

@WebServlet("/fruit")
public class FruitServlet extends  ViewBaseServlet{

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if((Page) session.getAttribute("page") == null ) {
            init(req,resp);
            return ;
        }

        String operation = req.getParameter("operation");
        if(operation == null || "".equals(operation))
            operation = "onload";
        switch(operation) {
            case "onload":
                onload(req,resp);
                break;
            case "add":
                add(req,resp);
                break;
            case "changPage":
                changePage(req,resp);
                break;
            case "delete":
                delete(req,resp);
                break;
            case "edit":
                edit(req,resp);
                break;
            case "search":
                search(req,resp);
                break;
            case "update":
                update(req,resp);
                break;
            default:
                throw new RuntimeException("operation is illegal !");
        }

    }

    private void init(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        HttpSession session = req.getSession();
        System.out.println("来到了init");
        try {
            Page page = new Page();
            session.setAttribute("page",page);

            req.getRequestDispatcher("fruit").forward(req,resp);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private void onload(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

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

    private void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("utf-8");
        HttpSession session = req.getSession();

        String name = req.getParameter("name");
        String price = req.getParameter("price");
        String count = req.getParameter("count");
        String remark = req.getParameter("remark");
        Fruit fruit = new Fruit(1,name, Integer.parseInt(price), Integer.parseInt(count), remark);


        Connection connection = null;

        try {

            connection = ConnectionFactor.getConnection();
            FruitDAO fruitDAO = new FruitDAOImpl();
            fruitDAO.addFruit(connection,fruit);

            resp.sendRedirect("fruit");

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

    private void changePage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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

            resp.sendRedirect("fruit");


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection connection = null;

        try {
            connection = ConnectionFactor.getConnection();

            int id = Integer.parseInt(req.getParameter("id"));
            FruitDAO fruitDAO = new FruitDAOImpl();
            fruitDAO.delFruitById(connection,id);

            resp.sendRedirect("fruit");

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

    private void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


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

    private void search(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String keyword = req.getParameter("keyword");
        HttpSession session = req.getSession();

        try {
            Page page = (Page) session.getAttribute("page");
            page.setKeyword(keyword);
            page.first();


            resp.sendRedirect("fruit");


        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

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

            resp.sendRedirect("fruit");


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
