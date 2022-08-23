package servlet;

import bean.Fruit;
import connection.ConnectionFactor;
import dao.FruitDAO;
import message.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

//@WebServlet("/FruitController")
public class FruitController{

    private FruitDAO fruitDAO = null;

    private String onload(HttpServletRequest req) {

        HttpSession session = req.getSession();
        Connection connection = null;

        try {
            connection = ConnectionFactor.getConnection();
            Page page = (Page) session.getAttribute("page");

            List<Fruit> fruitList = fruitDAO.getOnePageFruit(connection,page);
            session.setAttribute("fruitList",fruitList);

//            super.processTemplate("Hello",req,resp);
            return "Hello";


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

    private String add(String name,String price,String count,String remark,HttpServletRequest req) {

        HttpSession session = req.getSession();

        Fruit fruit = new Fruit(1,name, Integer.parseInt(price), Integer.parseInt(count), remark);


        Connection connection = null;

        try {

            connection = ConnectionFactor.getConnection();

            fruitDAO.addFruit(connection,fruit);

//            resp.sendRedirect("fruit.do");
            return "redirect:fruit.do";


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

    private String changePage(HttpServletRequest req) {
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

//            resp.sendRedirect("fruit.do");
            return "redirect:fruit.do";

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String delete(String id,HttpServletRequest req){
        Connection connection = null;

        try {
            connection = ConnectionFactor.getConnection();


            fruitDAO.delFruitById(connection,Integer.parseInt(id));

//            resp.sendRedirect("fruit.do");
            return "redirect:fruit.do";


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

    private String edit(String id,HttpServletRequest req){


        Connection connection = null;
        try {
            if(id != null) {
                connection = ConnectionFactor.getConnection();

                Fruit fruit = fruitDAO.getFruitById(connection, Integer.parseInt(id));

                HttpSession session = req.getSession();
                req.setAttribute("fruit",fruit);

//                super.processTemplate("edit",req,resp);
                return "edit";

            }
            return null;




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

    private String search(String keyword,HttpServletRequest req) throws Exception {

        HttpSession session = req.getSession();

        Page page = (Page) session.getAttribute("page");
        page.setKeyword(keyword);
        page.first();

        return "redirect:fruit.do";

//            resp.sendRedirect("fruit.do");

    }

    private String update(String id,String name,String price,String count,String remark,HttpServletRequest req) {


        Connection connection = null;

        try {

            connection = ConnectionFactor.getConnection();
            Fruit fruit = new Fruit(Integer.parseInt(id),name,Integer.parseInt(price), Integer.parseInt(count), remark);

            fruitDAO.setFruitById(connection,fruit);


//            resp.sendRedirect("fruit.do");
            return "redirect:fruit.do";

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

//    @Override
//    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        req.setCharacterEncoding("utf-8");
//        System.out.println("service");
//        HttpSession session = req.getSession();
//        if((Page) session.getAttribute("page") == null ) {
//            Page page = null;
//            try {
//                page = new Page();
//                session.setAttribute("page",page);
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }
//
//        String operation = req.getParameter("operation");
//        if(operation == null || "".equals(operation))
//            operation = "onload";
//
//        Method[] declaredMethods = this.getClass().getDeclaredMethods();
//        for(Method method : declaredMethods) {
//            String methodName = method.getName();
//            if(methodName.equals(operation)) {
//                try {
//                    method.invoke(this,req,resp);
//                    return ;
//                } catch (IllegalAccessException e) {
//                    throw new RuntimeException(e);
//                } catch (InvocationTargetException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }
//        throw new RuntimeException("operation is illegal !");
//

//        switch(operation) {
//            case "onload":
//                onload(req,resp);
//                break;
//            case "add":
//                add(req,resp);
//                break;
//            case "changPage":
//                changePage(req,resp);
//                break;
//            case "delete":
//                delete(req,resp);
//                break;
//            case "edit":
//                edit(req,resp);
//                break;
//            case "search":
//                search(req,resp);
//                break;
//            case "update":
//                update(req,resp);
//                break;
//            default:
//                throw new RuntimeException("operation is illegal !");
//        }
//


//    }

        //    private void init(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        req.setCharacterEncoding("utf-8");
//        HttpSession session = req.getSession();
//        System.out.println("来到了init");
//        try {
//            Page page = new Page();
//            session.setAttribute("page",page);
//
//            req.getRequestDispatcher("fruit.do").forward(req,resp);
//
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
