package mvc;

import ioc.BeanFactory;
import ioc.ClassPathXmlApplicationContext;
import message.Page;
import servlet.ViewBaseServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@WebServlet("*.do")
public class DispatcherServlet extends ViewBaseServlet {

    private BeanFactory beanFactory = new ClassPathXmlApplicationContext();


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("utf-8");
        System.out.println("service");


        //设置页数，如果是session没有page，则新建page存入session
        HttpSession session = req.getSession();
        if((Page) session.getAttribute("page") == null ) {
            Page page = null;
            try {
                page = new Page();
                session.setAttribute("page",page);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }


        try {
            String reqServletPathPath = req.getServletPath();//获取请求路径 "/fruit.do"
            //解析路径获取"fruit"
            String servletPath = reqServletPathPath.substring(1,reqServletPathPath.length() - 3);
            System.out.println(servletPath);

            //在map里查询"fruit"，获取key值，更具全类名，新建对象
            Object controller = beanFactory.getBean(servletPath);

            //获取网页发来的请求类型
            String operation = req.getParameter("operation");
            if(operation == null || "".equals(operation))
                operation = "onload";

            //根据请求类型获取方法名
            Method[] methods = controller.getClass().getDeclaredMethods();
            Method method = null;
            for(Method temp: methods){
                if(operation.equals(temp.getName())) {
                    method = temp;
                    break;
                }
            }

            //获取方法参数，并填充方法参数
            Parameter[] parameters = method.getParameters();
            Object[] argv = new Object[parameters.length];
            for(int i = 0; i < parameters.length; i++) {
                String name = parameters[i].getName();
                if("req".equals(name)) {
                    argv[i] = req;
                } else {
                    argv[i] = req.getParameter(name);
                }
            }

            //调用方法
            boolean accessible = method.isAccessible();
            method.setAccessible(true);
            String methodReturnString = (String)method.invoke(controller, argv);
            method.setAccessible(accessible);



//            Method method = controller.getClass().getDeclaredMethod(operation, HttpServletRequest.class);
//            Parameter[] parameters = method.getParameters();
//            for(int i = 0; i < parameters.length; i++) {
//                System.out.println(parameters[i].getName());
//            }
//
//            boolean accessible = method.isAccessible();
//            method.setAccessible(true);
//            String methodReturnString = (String)method.invoke(controller, req);
//            method.setAccessible(accessible);



            //根据调用的方法的返回值，决定下一步的转发或者
            if(methodReturnString.startsWith("redirect:")) {
                String redirect = methodReturnString.substring("redirect:".length());
                resp.sendRedirect(redirect);
            } else {
                super.processTemplate(methodReturnString,req,resp);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

//        RequestDispatcher requestDispatcher = req.getRequestDispatcher(servlet);
//        requestDispatcher.forward(req,resp);

    }
}


//    public DispatcherServlet() {
//        InputStream is = null;
//        try {
//            //读取xml文件
//
//            is = DispatcherServlet.class.getClassLoader().getResourceAsStream("applicationContext.xml");
//
//
//            if(is == null) {
//                System.out.println("null");
//            } else {
//                System.out.println("not null");
//            }
//
//            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
//            Document document = documentBuilder.parse(is);
//            NodeList beanList = document.getElementsByTagName("bean");
//
//            for(int i = 0; i < beanList.getLength(); i++) {
//                Node node = beanList.item(i);
//                if(node.getNodeType() == Node.ELEMENT_NODE) {
//                    Element element = (Element) node;
//                    String id = element.getAttribute("id");
//                    String clasPath = element.getAttribute("class");
//                    Class clas = Class.forName(clasPath);
//                    Object controller = clas.newInstance();
//                    beanNameMap.put(id,controller); //将读出的信息存入map
//                    System.out.println("id = " + id + " class = " + clas);
//                }
//            }
//
//
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        } finally {
//            if(is != null) {
//                try {
//                    is.close();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }
//
//    }
