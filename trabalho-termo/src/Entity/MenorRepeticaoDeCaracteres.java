package Entity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class MenorRepeticaoDeCaracteres {

    public static void main(String[] args) {
        String nomeArquivo = "trabalho-termo/palavras.txt";

        try {
            BufferedReader leitor = new BufferedReader(new FileReader(nomeArquivo));

            String linha;
            while ((linha = leitor.readLine()) != null) {
                String[] palavras = linha.split("\\s+"); // Divide a linha em palavras

                for (String palavra : palavras) {
                    if (temApenasUnicas(palavra)) {
                        System.out.println(palavra);
                    }
                }
            }

            leitor.close();
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
}



