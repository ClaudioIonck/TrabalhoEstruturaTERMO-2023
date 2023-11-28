import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Arrays;
import java.util.Random;


public class Main {

    // LISTAS DE PALAVRAS
    private List<String> palavrasDisponiveis;
    // LISTA DE PALAVRAS INICIAIS
    private List<String> palavrasIniciais;
    // LISTA DE PALAVRAS DE 5 LETRAS (CHUTE INICIAL)
    private List<String> chuteInicial;


    // CONSTRUTOR
    public Main() {
        // INICIALIZAÇÃO DAS LISTAS:

        // LISTA DE LETRAS DISPONIVEIS
        palavrasDisponiveis = new ArrayList<>();

        // LISTA DE PALAVRAS INICIAIS
        palavrasIniciais = new ArrayList<>();

        // LISTA DE PALAVRAS DE 5 LETRAS (CHUTE INICIAL)
        chuteInicial = new ArrayList<>();
    }

    // METODO PARA CARREGAR AS LETRAS DISPONIVEIS:

    public void loadpalavrasDisponiveis() {
        palavrasDisponiveis.add("A");
        palavrasDisponiveis.add("B");
        palavrasDisponiveis.add("C");
        palavrasDisponiveis.add("D");
        palavrasDisponiveis.add("E");
        palavrasDisponiveis.add("F");
        palavrasDisponiveis.add("G");
        palavrasDisponiveis.add("H");
        palavrasDisponiveis.add("I");
        palavrasDisponiveis.add("J");
        palavrasDisponiveis.add("K");
        palavrasDisponiveis.add("L");
        palavrasDisponiveis.add("M");
        palavrasDisponiveis.add("N");
        palavrasDisponiveis.add("O");
        palavrasDisponiveis.add("P");
        palavrasDisponiveis.add("Q");
        palavrasDisponiveis.add("R");
        palavrasDisponiveis.add("S");
        palavrasDisponiveis.add("T");
        palavrasDisponiveis.add("U");
        palavrasDisponiveis.add("V");
        palavrasDisponiveis.add("W");
        palavrasDisponiveis.add("X");
        palavrasDisponiveis.add("Y");
        palavrasDisponiveis.add("Z");

    }

    // METODO PARA CARREGAR AS PALAVRAS INICIAIS:
    public void loadpalavrasIniciais(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            // LE CADA LINHA DO ARQUIVO E ADICIONA NA LISTA DE PALAVRAS INICIAIS
            String line;

            // ENQUANTO HOUVER LINHAS NO ARQUIVO, ADICIONA NA LISTA DE PALAVRAS INICIAIS
            while ((line = reader.readLine()) != null) {
                palavrasIniciais.add(line.toUpperCase());
            }
        }
    }

    // METODO PARA CARREGAR AS PALAVRAS DE 5 LETRAS (CHUTE INICIAL):
    public void loadchuteInicial(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.length() == 5) {
                    chuteInicial.add(line.toUpperCase());
                }
            }
        }
    }

    // METODO PARA JOGAR O JOGO:
    public void inicarJogo() {
        Scanner scanner = new Scanner(System.in);

        // ESCOLHA UMA PALAVRA INICIAL DA LISTA
        Random random = new Random();
        String palavraSecreta = palavrasIniciais.get(random.nextInt(palavrasIniciais.size()));

        boolean jogoGanho = false;
        List<String> palavrasUsadas = new ArrayList<>();

        while (!jogoGanho) {
            System.out.println("Palavra secreta: " + palavraSecreta);

            // SOLICITAR FEEDBACK AO USUARIO
            System.out.print("Informe o feedback (por exemplo, 1,1,2,2,0): ");
            String feedback = scanner.nextLine();

            // FILTRAR PALAVRAS COM BASE NO FEEDBACK
            List<String> filtroDePalavras = filtraPalavra(palavraSecreta, feedback);

            // EXCLUIR PALAVRAS JÁ USADAS DA LISTA DE FILTRO
            filtroDePalavras.removeAll(palavrasUsadas);

            // SE HOUVER APENAS UMA PALAVRA NA LISTA FILTRADA, O JOGO FOI GANHO
            if (filtroDePalavras.size() == 1) {
                jogoGanho = true;
                System.out.println("Parabéns, você acertou! A palavra é: " + filtroDePalavras.get(0));
            } else {
                // ESCOLHER A PRÓXIMA PALAVRA PARA TENTATIVA ALEATÓRIA
                palavraSecreta = filtroDePalavras.get(random.nextInt(filtroDePalavras.size()));
                palavrasUsadas.add(palavraSecreta);
            }

        }

        scanner.close();
    }


    // METODO PARA FILTRAR AS PALAVRAS COM BASE NO FEEDBACK:
    private List<String> filtraPalavra(String palavraSecreta, String feedback) {
        List<String> filtroDePalavras = new ArrayList<>();

        // SEPARAR O FEEDBACK EM UM ARRAY DE INTEIROS
        int[] feedbackArray = Arrays.stream(feedback.split(",")).mapToInt(Integer::parseInt).toArray();

        // FILTRAR PALAVRAS COM BASE NO FEEDBACK
        for (String palavra : chuteInicial) {
            if (palavraValida(palavra, palavraSecreta, feedbackArray)) {
                filtroDePalavras.add(palavra);
            }
        }

        return filtroDePalavras;
    }



    // METODO PARA VERIFICAR SE A PALAVRA EH VALIDA:
    private boolean palavraValida(String palavra, String palavraSecreta, int[] feedbackArray) {
        // VERIFICAR SE A PALAVRA TEM O MESMO COMPRIMENTO QUE A PALAVRA SECRETA
        if (palavra.length() != palavraSecreta.length()) {
            return false;
        }

        for (int i = 0; i < palavra.length(); i++) {
            char caracterAtual = palavra.charAt(i);

            // VERIFICAR FEEDBACKARRAY PARA VERIFICAR SE A LETRA ESTA NA POSICAO CORRETA
            if (feedbackArray[i] == 0) {
                // SE A LETRA ATUAL ESTIVER NA POSICAO CORRETA, A PALAVRA NAO EH VALIDA
                if (palavraSecreta.indexOf(caracterAtual) != -1) {
                    return false;
                }
            } else if (feedbackArray[i] == 1) {
                // SE A LETRA ATUAL NAO ESTIVER NA POSICAO CORRETA, A PALAVRA NAO EH VALIDA
                if (caracterAtual != palavraSecreta.charAt(i)) {
                    return false;
                }
            } else if (feedbackArray[i] == 2) {
                // SE A LETRA ESTIVER NA POSICAO CORRETA, A PALAVRA NAO EH VALIDA
                if (caracterAtual == palavraSecreta.charAt(i)) {
                    return false;
                }
            }
        }

        return true;
    }


    // METODO MAIN:
    public static void main(String[] args) {
        Main jogo = new Main();

        try {
            jogo.loadpalavrasDisponiveis();
            jogo.loadpalavrasIniciais("src/palavrasiniciais.txt");
            jogo.loadchuteInicial("src/palavras.txt");
            jogo.inicarJogo();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
