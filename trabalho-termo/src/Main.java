import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import Entity.DicionarioDTO;

// novos

import java.io.BufferedReader;
import java.io.FileReader;



public class Main {
    private static boolean isRandom;

    public static void main(String[] args) {

        int modo = TipoDeExecucao();
        String resposta = "";

        if (modo == 1 || modo == 2) {
            int[] posicoesCorretas = GerarPosicoesCorretas();
            ArrayList<String> letrasDisponiveis = GerarLetras();
            ArrayList<String> palavrasDisponiveis = GerarPalavras();

            palavrasDisponiveis = FiltrarPorTamanho(palavrasDisponiveis, posicoesCorretas.length);

            MostrarLista_String("Palavras disponíveis", palavrasDisponiveis);

            boolean isCorreta = false;
            String escolha = "";

            while (!isCorreta) {
                DicionarioDTO dicionario = RoboRemovePosicaoZero(palavrasDisponiveis, letrasDisponiveis, posicoesCorretas, escolha);

                palavrasDisponiveis = dicionario.palavras;
                letrasDisponiveis = dicionario.letras;

                escolha = RoboEscolhePalavra(palavrasDisponiveis, letrasDisponiveis, posicoesCorretas, escolha);

                MostrarLista_String("Letras disponíveis", letrasDisponiveis);
                palavrasDisponiveis = FiltrarPorLetra(palavrasDisponiveis, letrasDisponiveis);
                MostrarLista_String("Palavras disponíveis", palavrasDisponiveis);

                System.out.println("\n\nRobô escolheu: " + escolha);

                TransformarPosicoesEmArray(posicoesCorretas);

                boolean todasCorretas = true;

                for (int posicoesCorreta : posicoesCorretas) {
                    if (posicoesCorreta != 1) {
                        todasCorretas = false;
                        break;
                    }
                }

                if (todasCorretas) {
                    resposta = escolha;
                    isCorreta = true;
                }
            }
        } else {
            throw new RuntimeException("\n\nErro: Decisão inválida.\n\n");
        }

        System.out.println("\n\nA palavra correta é: " + resposta);
    }

    public static DicionarioDTO RoboRemovePosicaoZero(ArrayList<String> palavrasDisponiveis, ArrayList<String> letrasDisponiveis, int[] posicoesUsuario, String escolhaAnterior)
    {
        DicionarioDTO dicionario = new DicionarioDTO();

        if(Objects.equals(escolhaAnterior, ""))
        {
            dicionario.palavras = palavrasDisponiveis;
            dicionario.letras = letrasDisponiveis;

            return  dicionario;
        }

        //Passando pelas posições sugeridas pelo usuário
        for(int a = 0; a < posicoesUsuario.length; a++)
        {
            //Verificando se a letra não existe
            if(posicoesUsuario[a] == 0)
            {
                char[] escolhaAnteriorSplit = escolhaAnterior.toCharArray();

                String letraParaRemover = Character.toString(escolhaAnteriorSplit[a]);


                //Passando pelas letras disponíveis
                for(int b = 0; b < letrasDisponiveis.size(); b++)
                {
                    //Verificando se a letra existe na lista de letras
                    if(letrasDisponiveis.get(b).equalsIgnoreCase(letraParaRemover))
                    {
                        //Passar pelas palavras para remover todas que tem essa letra nessa posição
                        for(int d = 0; d < palavrasDisponiveis.size(); d++)
                        {
                            String palavraAtual = palavrasDisponiveis.get(d);
                            String letraDaPosicaoParaRemover = Character.toString(palavraAtual.toCharArray()[a]);

                            //Verificando se a letra da posição incorreta é igual a da palavra atual
                            if(letraDaPosicaoParaRemover.equalsIgnoreCase(letraParaRemover))
                            {
                                //Remova a palavra
                                palavrasDisponiveis.remove(d);
                                d = d - 1;
                            }
                        }

                        letrasDisponiveis.remove(b);
                        break;
                    }
                }
            }
        }


        dicionario.palavras = palavrasDisponiveis;
        dicionario.letras = letrasDisponiveis;

        return  dicionario;
    }

    private static void TransformarPosicoesEmArray(int[] posicoesCorretas) {
        MostrarArray_Int(posicoesCorretas);

        System.out.println("\n\nPara as posições das letras:\nDigite 2 para as que estão em posição incorreta.");
        System.out.println("Digite 1 para as que estão corretas.");
        System.out.println("Digite 0 para as não existem.");

        System.out.println("\nDigite separando por vírgulas desta forma: 0,0,1,2,0\n");

        //Iniciando scanner
        Scanner scanner = new Scanner(System.in);

        //Solicitar um número ao usuário
        System.out.print("Digite seu array: ");
        String array = scanner.nextLine();

        //Verifica se o tamanho do array digitado está do tamanho correto
        if (array.split(",").length != posicoesCorretas.length) {
            throw new RuntimeException("\n\nProblema: O array inserido tem tamanho diferente do permitido.\n\n");
        }

        //Passando pelo array do usuário
        for (int a = 0; a < posicoesCorretas.length; a++) {
            //Verificando se posição atual é uma posição correta
            if (posicoesCorretas[a] == 1) {
                posicoesCorretas[a] = 1;
            } else {
                //Verificando se é um inteiro válido
                try {
                    // Tenta converter a string para um número
                    int numero = Integer.parseInt(array.split(",")[a]);

                    //Adiciona o valor que o usuário informou na posição do array
                    posicoesCorretas[a] = numero;
                } catch (NumberFormatException e) {
                    throw new NumberFormatException("\n\nErro: A string '" + array.split(",")[a] + "' na posição " + a + " do seu array não é um número.");
                }
            }
        }

    }

    private static void MostrarArray_Int(int[] array) {
        // Imprimir o array gerado
        System.out.println("\n" + "Posições sugeridas" + ": ");

        int counter = 1;

        for (int valor : array) {

            if (counter == 5) {
                System.out.print(valor + "\n");
                counter = 1;
            } else {
                System.out.print(valor + " - ");
                counter++;
            }
        }
    }

    private static String RoboEscolhePalavra(ArrayList<String> palavrasDisponiveis, ArrayList<String> letrasDisponiveis, int[] posicoesCorretas, String escolha) {
        //Se existir palavras disponíveis
        Random geradorAleatorio = new Random();
        if (!palavrasDisponiveis.isEmpty()) {
            //Se a escolha deve ser aleatória
            if (isRandom) {
                // Gerar um número aleatório entre 0 e o tamanho máximo de palavras disponíveis
                int numeroAleatorio = geradorAleatorio.nextInt((palavrasDisponiveis.size() - 1));

                return palavrasDisponiveis.get(numeroAleatorio);
            } else {
                // Passar pelas posições corretas
                palavrasDisponiveis = RoboRemovePosicaoZero(palavrasDisponiveis, letrasDisponiveis, posicoesCorretas, escolha).palavras;
                palavrasDisponiveis = FiltrarPorTamanho(palavrasDisponiveis, posicoesCorretas.length);

                // Gerar um número aleatório entre 0 e o tamanho máximo de palavras disponíveis
                int numeroAleatorio = geradorAleatorio.nextInt(palavrasDisponiveis.size());

                return palavrasDisponiveis.get(numeroAleatorio);
            }
        } else {
            return "";
        }
    }

    private static void MostrarLista_String(String titulo, ArrayList<String> lista) {
        // Imprimir a lista gerada
        System.out.println("\n\n\n" + titulo + ": ");

        int counter = 0;

        for (String valor : lista) {
            if (counter == 5) {
                System.out.print(valor + "\n");
                counter = 1;
            } else {
                System.out.print(valor + " - ");
                counter++;
            }
        }
    }

    private static ArrayList<String> FiltrarPorLetra(ArrayList<String> palavrasDisponiveis, ArrayList<String> letrasDisponiveis) {
        ArrayList<String> palavrasFiltradas = new ArrayList<>();

        for (String palavra : palavrasDisponiveis) {
            for (String letra : letrasDisponiveis) {
                if (palavra.contains(letra)) {
                    palavrasFiltradas.add(palavra);
                    break;
                }
            }
        }

        return palavrasFiltradas;
    }

    private static ArrayList<String> FiltrarPorTamanho(ArrayList<String> palavrasDisponiveis, int length) {
        ArrayList<String> palavrasFiltradas = new ArrayList<>();

        for (String palavra : palavrasDisponiveis) {
            if (palavra.trim().length() == length) {
                palavrasFiltradas.add(palavra);
            }
        }

        return palavrasFiltradas;
    }

    private static int[] GerarPosicoesCorretas() {
        // Definir o tamanho fixo desejado (5)
        int tamanhoArray = 5;

        // Gerar array de inteiros com o tamanho 5
        int[] meuArray = new int[tamanhoArray];

        // Inicializar todas as posições com o valor 3
        Arrays.fill(meuArray, 3);

        return meuArray;
    }

    private static int TipoDeExecucao() {
        // Solicitar tipo de execução
        System.out.print("Digite 1 para executar em modo 'dev' ou 2 para executar em modo 'user': ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    private static ArrayList<String> GerarPalavras() {
        Path caminhoDoArquivo = Paths.get("C:\\Users\\User\\Documents\\GitHub\\TrabalhoEstruturaTERMO-2023\\trabalho-termo\\src\\palavras.txt");
        ArrayList<String> palavrasDisponiveis = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoDoArquivo.toFile(), StandardCharsets.UTF_8))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                palavrasDisponiveis.add(linha);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de palavras: " + e.getMessage());
        }

        return palavrasDisponiveis;
    }



    private static ArrayList<String> GerarLetras() {
        ArrayList<String> letrasDisponiveis = new ArrayList<>();

        letrasDisponiveis.add("Á");
        letrasDisponiveis.add("À");
        letrasDisponiveis.add("Â");
        letrasDisponiveis.add("Ã");
        letrasDisponiveis.add("Ç");
        letrasDisponiveis.add("É");
        letrasDisponiveis.add("È");
        letrasDisponiveis.add("Ê");
        letrasDisponiveis.add("Í");
        letrasDisponiveis.add("Ì");
        letrasDisponiveis.add("Î");
        letrasDisponiveis.add("Ñ");
        letrasDisponiveis.add("Ó");
        letrasDisponiveis.add("Ò");
        letrasDisponiveis.add("Ô");
        letrasDisponiveis.add("Õ");
        letrasDisponiveis.add("Ú");
        letrasDisponiveis.add("ú");
        letrasDisponiveis.add("Ù");
        letrasDisponiveis.add("ù");
        letrasDisponiveis.add("Û");
        letrasDisponiveis.add("A");
        letrasDisponiveis.add("B");
        letrasDisponiveis.add("C");
        letrasDisponiveis.add("D");
        letrasDisponiveis.add("E");
        letrasDisponiveis.add("F");
        letrasDisponiveis.add("G");
        letrasDisponiveis.add("H");
        letrasDisponiveis.add("I");
        letrasDisponiveis.add("J");
        letrasDisponiveis.add("K");
        letrasDisponiveis.add("L");
        letrasDisponiveis.add("M");
        letrasDisponiveis.add("N");
        letrasDisponiveis.add("O");
        letrasDisponiveis.add("P");
        letrasDisponiveis.add("Q");
        letrasDisponiveis.add("R");
        letrasDisponiveis.add("S");
        letrasDisponiveis.add("T");
        letrasDisponiveis.add("U");
        letrasDisponiveis.add("V");
        letrasDisponiveis.add("W");
        letrasDisponiveis.add("X");
        letrasDisponiveis.add("Y");
        letrasDisponiveis.add("Z");

        return letrasDisponiveis;
    }

}
