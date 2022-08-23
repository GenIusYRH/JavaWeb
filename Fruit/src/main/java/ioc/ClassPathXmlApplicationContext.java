package ioc;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;

public class ClassPathXmlApplicationContext implements BeanFactory {
    private HashMap<String,Object> beanObjectMap = new HashMap<>();


    public ClassPathXmlApplicationContext() {
        DocumentBuilder documentBuilder = null;
        InputStream is = null;
        try {
            //获取文件流
            is = ClassPathXmlApplicationContext.class.getClassLoader().getResourceAsStream("applicationContext.xml");
//            is = new FileInputStream("D:\\IntelliJ IDEA 2022.1\\code\\JavaWeb\\Fruit\\src\\applicationContext.xml");
            //获取要读取的结点列表
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document parse = documentBuilder.parse(is);
            NodeList beanList = parse.getElementsByTagName("bean");
            //遍历结点列表
            for(int i = 0; i < beanList.getLength(); i++) {
                Node item = beanList.item(i);
                //判断结点是否是元素节点
                if(item.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) item;//强转
                    String id = element.getAttribute("id");//获取id
                    String clas = element.getAttribute("class");//获取全类名
                    Class beanClass = Class.forName(clas);//获取类
                    Object beanObject = beanClass.newInstance();//造对象
                    beanObjectMap.put(id,beanObject);//放入表中

                }
            }


            for(int i = 0; i < beanList.getLength(); i++) {
                Node item = beanList.item(i);
                if(item.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) item;
                    String id = element.getAttribute("id");
                    String clas = element.getAttribute("class");
                    NodeList property = element.getElementsByTagName("property");//找到结点中的property结点列表
                    for(int j = 0; j < property.getLength(); j++) {
                        Node propertyNode = property.item(j);
                        if(propertyNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element propertyElement = (Element) propertyNode;
                            String name = propertyElement.getAttribute("name");//找到需要被注入依赖的的属性名
                            String ref = propertyElement.getAttribute("ref");//找到依赖的类名
                            Object relObject = beanObjectMap.get(ref);//根据map找到依赖的对象
                            Object targetObject = beanObjectMap.get(id);//根据map找到需要依赖的对象
                            Field field = targetObject.getClass().getDeclaredField(name);//获取需要注入的属性
                            boolean accessible = field.isAccessible();
                            field.setAccessible(true);
                            field.set(targetObject,relObject);//在需要注入的属性里注入找到的依赖对象
                            field.setAccessible(accessible);

                        }
                    }

                }
            }





        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public Object getBean(String id) {
        return beanObjectMap.get(id);
    }
}
