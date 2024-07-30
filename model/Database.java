package model;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import java.sql.SQLException;

/*
 *  Class: Database Class
 *   
 * @autor Matheus Teles
 * @autor João Vitor
 * @autor Victor Alves
 * @autor Yan Santos
 * @autor Gabriel Eduardo
 */
public class Database {
    private String databaseName;
    private JdbcConnectionSource connection;

    public Database(String databaseName) {
        this.databaseName = databaseName;
    }

    public JdbcConnectionSource getConnection() {
        if (connection == null) {
            try {
                connection = new JdbcConnectionSource("jdbc:sqlite:" + databaseName);
            } catch (SQLException e) {
                System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            }
        }
        return connection;
    }

    public void close() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (java.lang.Exception e) {
                System.err.println(e);
            }
        }
    }
}
