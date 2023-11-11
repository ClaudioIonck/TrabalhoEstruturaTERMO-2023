package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static DbConnection.DbConfig.conectarAoBancoDeDados;

public class PalavrasController {

    public static List<Palavra> getAll() {
        List<Palavra> palavras = new ArrayList<>();
        String query = "SELECT id, palavra FROM sua_tabela";

        try (Connection conexao = conectarAoBancoDeDados();
             PreparedStatement preparedStatement = conexao.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String palavra = resultSet.getString("palavra");

                Palavra novaPalavra = new Palavra(id, palavra);
                palavras.add(novaPalavra);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return palavras;
    }

    public static void main(String[] args) {
        List<Palavra> palavras = getAll();

        for (Palavra palavra : palavras) {
            System.out.println("ID: " + palavra.getId() + ", Palavra: " + palavra.getPalavra());
        }
    }
}

class Palavra {
    private int id;
    private String palavra;

    public Palavra(int id, String palavra) {
        this.id = id;
        this.palavra = palavra;
    }

    public int getId() {
        return id;
    }

    public String getPalavra() {
        return palavra;
    }
}
