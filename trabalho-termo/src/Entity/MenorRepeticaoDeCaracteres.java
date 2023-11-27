package Entity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class MenorRepeticaoDeCaracteres {

    public static void main(String[] args) {
        String nomeArquivoEntrada = "trabalho-termo/palavras.txt";
        String nomeArquivoSaida = "trabalho-termo/palavrasiniciais.txt";

        try {
            BufferedReader leitor = new BufferedReader(new FileReader(nomeArquivoEntrada));
            BufferedWriter escritor = new BufferedWriter(new FileWriter(nomeArquivoSaida));

            String linha;
            while ((linha = leitor.readLine()) != null) {
                String[] palavras = linha.split("\\s+"); // Divide a linha em palavras

                for (String palavra : palavras) {
                    palavra = palavra.toUpperCase(); // Transforma a palavra em maiúsculas
                    if (temApenasUnicas(palavra)) {
                        escreverPalavra(escritor, palavra);
                    }
                }
            }

            leitor.close();
            escritor.close();

            System.out.println("Arquivo " + nomeArquivoSaida + " gerado com sucesso.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean temApenasUnicas(String palavra) {
        Set<Character> caracteres = new HashSet<>();

        for (char c : palavra.toCharArray()) {
            if (!caracteres.add(c)) {
                return false; // Retorna false se encontrar um caractere repetido.
            }
        }

        return true;
    }

    private static void escreverPalavra(BufferedWriter escritor, String palavra) throws IOException {
        escritor.write(palavra + "\n");
    }
}





