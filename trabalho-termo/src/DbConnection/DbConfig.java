package DbConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DbConfig {
    public static Connection conectarAoBancoDeDados() throws SQLException {
        return DriverManager.getConnection(url, usuario, senha);
    }

    String url = "jdbc:mysql://localhost:3306/seu_banco_de_dados";
    String usuario = "seu_usuario";
    String senha = "sua_senha";

    public static void lerDadosDaTabela() {
        String query = "SELECT id, palavra FROM sua_tabela";

        try (Connection conexao = conectarAoBancoDeDados();
             PreparedStatement preparedStatement = conexao.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String palavra = resultSet.getString("palavra");

                System.out.println("ID: " + id + ", Palavra: " + palavra);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
