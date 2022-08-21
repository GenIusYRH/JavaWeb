package message;

import connection.ConnectionFactor;
import dao.FruitDAO;
import dao.impl.FruitDAOImpl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class Page {
    private  int pageCount;
    private  int pageNo = 1;
    private  String keyword = "";
    private FruitDAO fruitDAO = new FruitDAOImpl();

    public Page() throws Exception {
        pageCount = getPageCountFromDB();
    }

    public void flush() throws Exception {
        pageCount = getPageCountFromDB();
    }

    private  int getPageCountFromDB() throws Exception {
        Connection connection = null;

        try {

            connection = ConnectionFactor.getConnection();
            return (fruitDAO.getFruitCount(connection,this) + 4) / 5;

        } catch (Exception e) {
            throw new Exception(e);
        } finally {

            try {
                ConnectionFactor.closeResource(connection);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) throws Exception {
        this.keyword = keyword;
        flush();
    }

    public void next() {
        pageNo++;
    }

    public void previous() {
        pageNo--;
    }
    public void first() {
        pageNo = 1;
    }
    public void last() {
        pageNo = pageCount;
    }


    @Override
    public String toString() {
        return "Page{" +
                "pageCount=" + pageCount +
                ", pageNo=" + pageNo +
                '}';
    }
}
