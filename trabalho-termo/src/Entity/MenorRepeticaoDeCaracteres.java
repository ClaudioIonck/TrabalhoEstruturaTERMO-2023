package Entity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MenorRepeticaoDeCaracteres {

    public static void main(String[] args) {
        String nomeArquivo = "trabalho-termo/palavras.txt";

        try {
            BufferedReader leitor = new BufferedReader(new FileReader(nomeArquivo));

            String linha;
            while ((linha = leitor.readLine()) != null) {
                String[] palavras = linha.split("\\s+"); // Divide a linha em palavras

                for (String palavra : palavras) {
                    if (temMenosRepeticao(palavra)) {
                        System.out.println(palavra);
                    }
                }
            }

            leitor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean temMenosRepeticao(String palavra) {
        int[] contagemCaracteres = new int[256]; // Assume caracteres ASCII

        for (char c : palavra.toCharArray()) {
            contagemCaracteres[c]++;
        }

        int menorRepeticao = Integer.MAX_VALUE;
        for (int contagem : contagemCaracteres) {
            if (contagem > 0 && contagem < menorRepeticao) {
                menorRepeticao = contagem;
            }
        }

        return menorRepeticao == 1; // Retorna verdadeiro se houver apenas uma repetição do caractere.
    }
}


