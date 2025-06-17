import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtil {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1279";

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void executeSchema(String schemaFilePath) {
        try (Connection connection = getConnection();
             BufferedReader reader = new BufferedReader(new FileReader(schemaFilePath))) {
            StringBuilder sqlBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sqlBuilder.append(line).append("\n");
            }
            String sql = sqlBuilder.toString();

            try (Statement statement = connection.createStatement()) {
                statement.execute(sql);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erro ao executar o schema: " + e.getMessage());
        }
    }
}