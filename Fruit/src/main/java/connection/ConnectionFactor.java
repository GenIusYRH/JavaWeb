package connection;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactor {

    private static DataSource dataSource = null;
    static {
        System.out.println("执行了一次");
    }

    private ConnectionFactor() {}

    public static Connection getConnection() throws IOException, SQLException {
        InputStream fis = null;
        try {
            if(dataSource == null) {
                synchronized(ConnectionFactor.class) {
                    if(dataSource == null) {
                        fis = ConnectionFactor.class.getClassLoader().getResourceAsStream("fruit.properties");
                        Properties properties = new Properties();
                        properties.load(fis);

                        dataSource = DruidDataSourceFactory.createDataSource(properties);
                    }
                }
            }

            Connection connection = dataSource.getConnection();
            return connection;

        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException(e);
        } catch (SQLException e) {
            throw new SQLException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {

            if(fis != null) {
                fis.close();
            }


        }
    }


    public Connection getConnection(String path) throws IOException, SQLException {
        FileInputStream fis = null;
        try {

            fis = new FileInputStream(path);
            Properties properties = new Properties();
            properties.load(fis);

            DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
            Connection connection = dataSource.getConnection();
            return connection;

        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException(e);
        } catch (SQLException e) {
            throw new SQLException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {

            if(fis != null) {
                fis.close();
            }

        }
    }


    public static <T> void closeResource(T t) throws Exception {
        if(t != null) {
            Method close = t.getClass().getMethod("close");
            close.invoke(t);
        }

    }




}
