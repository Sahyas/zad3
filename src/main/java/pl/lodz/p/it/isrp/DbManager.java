package pl.lodz.p.it.isrp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DbManager implements AutoCloseable {
    private String dbUsername = System.getenv("DB_USERNAME");
    private String dbPassword = System.getenv("DB_PASSWORD");
    private String dbUrl = System.getenv("DB_CONNECTION_URL") + System.getenv("DB_NAME");
    private Connection connection;

    public void connect() {
        try {
            connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            System.out.println("Connecting to database");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void executeSql(String sql) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute(sql);
    }

    @Override
    public void close() throws Exception {
        this.connection.close();
        System.out.println("Disconnecting database");
    }
}
