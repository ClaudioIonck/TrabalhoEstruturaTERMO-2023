import Entity.DicionarioDTO;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;


public class Main
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);

        int modo = TipoDeExecucao(scanner);
        String awnser = "";

        if(modo == 1)
        {
            //2. Gerando array de decisões (Pergunta o tamanho das palavras)
            int[] posicoesCorretas = GerarPosicoesCorretas(scanner);


            //3. Gerando letras
            ArrayList<String> letrasDisponiveis = GerarLetras();

            //4. Gerando palavras disponíveis
            ArrayList<String> palavrasDisponiveis = GerarPalavras(posicoesCorretas.length);


            //5. Filtrando palavras com o tamanho escolhido
            palavrasDisponiveis = FiltrarPorTamanho(palavrasDisponiveis, posicoesCorretas.length);

            MostrarLista_String("Palavras disponíveis", palavrasDisponiveis);

            boolean isCorrectAwnser = false;
            String escolha = "";


            MostrarArray_Int("Posições corretas", posicoesCorretas);
            MostrarLista_String("Letras disponíveis", letrasDisponiveis);

            //Filtragem por letras disponíveis
            ArrayList<String> palavrasFiltradas = FiltrarPorLetra(palavrasDisponiveis, letrasDisponiveis);
            MostrarLista_String("Palavras disponíveis", palavrasFiltradas);


            int palpites = 0;

            //Não finaliza enquanto não encontrar a resposta
            while(!isCorrectAwnser)
            {

                /*
                if(!escolha.equalsIgnoreCase(""))
                {
                    String caminhoEscrita = "historico-palavras/escolha_" + palpites + ".txt";

                    // Escrevendo as palavras em um novo arquivo
                    try
                    {
                        File arquivoEscrita = new File(caminhoEscrita);
                        BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoEscrita));

                        writer.write("Escolha: " + escolha);
                        writer.newLine();

                        writer.write("Array usuário: " + Arrays.toString(posicoesCorretas));
                        writer.newLine();

                        for (String palavra : palavrasFiltradas) {
                            writer.write(palavra);
                            writer.newLine();
                        }

                        writer.close();
                        System.out.println("Novo arquivo criado com sucesso!");

                    }
                    catch (IOException e)
                    {
                        System.out.println("Erro ao escrever no arquivo: " + e.getMessage());
                    }
                }
                */


                palavrasFiltradas = RoboFiltraPosicaoUm(palavrasFiltradas, posicoesCorretas, escolha, palpites);

                //6. Removendo sugestões zeradas
                DicionarioDTO dicionario = RoboRemovePosicaoZero(palavrasFiltradas, letrasDisponiveis, posicoesCorretas, escolha, palpites);

                palavrasFiltradas = dicionario.palavras;
                letrasDisponiveis = dicionario.letras;

                palavrasFiltradas = RoboFiltraPosicaoDois(palavrasFiltradas, posicoesCorretas, escolha, palpites);


                //7. Se o usuário ainda não inseriu no prompt
                if(posicoesCorretas[0] == 3)
                {
                    //7. Robo escolhe alguma palavra aleatória
                    escolha = RoboEscolhePalavra(palavrasFiltradas, letrasDisponiveis, posicoesCorretas, true, escolha);
                }
                else
                {
                    //7. Robo escolhe alguma palavra de forma estratégica
                    escolha = RoboEscolhePalavra(palavrasFiltradas, letrasDisponiveis, posicoesCorretas, false, escolha);
                }


                //MostrarArray_Int("Posições corretas", posicoesCorretas);
                MostrarLista_String("Letras disponíveis", letrasDisponiveis);

                //Filtragem por letras disponíveis
                palavrasFiltradas = FiltrarPorLetra(palavrasFiltradas, letrasDisponiveis);
                MostrarLista_String("Palavras disponíveis", palavrasFiltradas);


                System.out.println("\n\nRobô escolheu: " + escolha);

                //Usuário verifica quais posições estão corretas
                posicoesCorretas = TransformarPosicoesEmArray(posicoesCorretas);




                int countPosition = 0;

                for(int a = 0; a < posicoesCorretas.length; a++)
                {

                    if(posicoesCorretas[a] == 1)
                    {
                        countPosition++;
                    }
                    else
                    {
                        break;
                    }
                }

                if(countPosition == posicoesCorretas.length)
                {
                    awnser = escolha;
                    isCorrectAwnser = true;
                }




                palpites++;

            }


        }
        else if(modo == 2)
        {
            //Gerando array de decisões (Pergunta o tamanho das palavras)
            int[] posicoesCorretas = GerarPosicoesCorretas(scanner);


            //Gerando letras e palavras disponíveis
            ArrayList<String> letrasDisponiveis = GerarLetras();
            ArrayList<String> palavrasDisponiveis = GerarPalavras(posicoesCorretas.length);


            //Filtrando palavras com o tamanho escolhido
            palavrasDisponiveis = FiltrarPorTamanho(palavrasDisponiveis, posicoesCorretas.length);


            boolean isCorrectAwnser = false;
            String escolha = "";


            //Filtragem por letras disponíveis
            ArrayList<String> palavrasFiltradas = FiltrarPorLetra(palavrasDisponiveis, letrasDisponiveis);

            int palpites = 0;

            //Não finaliza enquanto não encontrar a resposta
            while(!isCorrectAwnser)
            {
                if(!escolha.equalsIgnoreCase(""))
                {
                    palpites++;
                }

                //Removendo sugestões zeradas
                DicionarioDTO dicionario = RoboRemovePosicaoZero(palavrasFiltradas, letrasDisponiveis, posicoesCorretas, escolha, palpites);

                palavrasFiltradas = dicionario.palavras;
                letrasDisponiveis = dicionario.letras;



                //Se o usuário ainda não inseriu no prompt
                if(posicoesCorretas[0] == 3)
                {
                    //Robo escolhe alguma palavra aleatória
                    escolha = RoboEscolhePalavra(palavrasDisponiveis, letrasDisponiveis, posicoesCorretas, true, escolha);
                }
                else
                {
                    escolha = RoboEscolhePalavra(palavrasDisponiveis, letrasDisponiveis, posicoesCorretas, false, escolha);
                }


                //MostrarArray_Int("Posições corretas", posicoesCorretas);

                //Filtragem por letras disponíveis
                palavrasFiltradas = FiltrarPorLetra(palavrasDisponiveis, letrasDisponiveis);


                System.out.println("\n\nRobô escolheu: " + escolha);

                //Usuário verifica quais posições estão corretas
                posicoesCorretas = TransformarPosicoesEmArray(posicoesCorretas);


                boolean isAllCorrect = false;

                for(int a = 0; a < posicoesCorretas.length; a++)
                {
                    if(posicoesCorretas[a] == 1)
                    {
                        isAllCorrect = true;
                    }
                    else
                    {
                        isAllCorrect = false;
                        break;
                    }
                }

                if(isAllCorrect)
                {
                    awnser = escolha;
                    isCorrectAwnser = true;
                }

            }
        }
        else
        {
            throw new RuntimeException("\n\nErro: Decisão inválida.\n\n");
        }

        System.out.println("\n\nA palavra correta é: " + awnser);

    }






    //Decisões do robô-------------------------------------------------------------------------------------------------
    public static String RoboEscolhePalavra(ArrayList<String> palavrasDisponiveis, ArrayList<String> letrasDisponiveis, int[] posicoesUsuario, boolean isRandom, String escolhaAnterior)
    {
        //Se existir palavras disponíveis
        if(palavrasDisponiveis.size() > 0)
        {
            //Se a escolha deve ser aleatória
            if(isRandom)
            {
                Random geradorAleatorio = new Random();

                // Gerar um número aleatório entre 0 e o tamanho máximo de palavras disponíveis
                int numeroAleatorio = geradorAleatorio.nextInt((palavrasDisponiveis.size() - 1));

                return palavrasDisponiveis.get(numeroAleatorio);
            }
            else
            {
                //Criando char para colocar as letras encontradas em suas posições
                char[] letrasEncontradas = new char[escolhaAnterior.length()];


                //1. Buscando letras encontradas
                //Transformando a escolha anterior em array de char
                char[] escolhaAnteriorChar = escolhaAnterior.toCharArray();

                //Contaddor de quantas letras corretas foram encontradas
                int totalLetrasIguais = 0;

                //Passando pela palavra anterior
                for(int a = 0; a < escolhaAnteriorChar.length; a++)
                {
                    //Verificando se a posição do array do usuário tem valor 1
                    if(posicoesUsuario[a] == 1)
                    {
                        //Buscar a letra da posição atual
                        char letraAtual = escolhaAnteriorChar[a];

                        //Adicionar letra encontrada na posição correta
                        letrasEncontradas[a] = letraAtual;

                        totalLetrasIguais++;
                    }
                }


                //Se encontrou alguma letra correta
                if(totalLetrasIguais > 0)
                {
                    //Criando lista das possíveis palavras
                    ArrayList<String> palavrasPossiveis = new ArrayList<>();

                    //Passar pela lista de palavras-------------------
                    for(int a = 0; a < palavrasDisponiveis.size(); a++)
                    {
                        //Transformando palavra em array
                        char[] palavraAtual = palavrasDisponiveis.get(a).toCharArray();

                        //Contador de letras encontradas
                        int totalLetrasEncontradas = 0;

                        //Passando por cada letra da palavra
                        for(int b = 0; b < palavraAtual.length; b++)
                        {
                            //Verificando se a posição do array do usuário tem valor 1
                            if(palavraAtual[b] == letrasEncontradas[b])
                            {
                                totalLetrasEncontradas++;
                            }
                        }

                        //Verificando se foi encontrado o mínimo de letras
                        if(totalLetrasEncontradas >= totalLetrasIguais)
                        {
                            //Adiciono a palavra nas palavras possíveis
                            palavrasPossiveis.add(palavrasDisponiveis.get(a));
                        }

                    }

                    //Retorna a primeira palavra
                    return palavrasPossiveis.get(0);

                }
                else
                {
                    Random geradorAleatorio = new Random();

                    // Gerar um número aleatório entre 0 e o tamanho máximo de palavras disponíveis
                    int numeroAleatorio = geradorAleatorio.nextInt((palavrasDisponiveis.size() - 1));

                    return palavrasDisponiveis.get(numeroAleatorio);
                }


                /*
                Random geradorAleatorio = new Random();


                // Gerar um número aleatório entre 0 e o tamanho máximo de palavras disponíveis
                int numeroAleatorio = geradorAleatorio.nextInt((palavrasDisponiveis.size() - 1));

                return palavrasDisponiveis.get(numeroAleatorio);
                */


            }
        }
        else
        {
            return "";
        }
    }

    public static DicionarioDTO RoboRemovePosicaoZero(ArrayList<String> palavrasDisponiveis, ArrayList<String> letrasDisponiveis, int[] posicoesUsuario, String escolhaAnterior, int palpite)
    {
        DicionarioDTO dicionario = new DicionarioDTO();

        ArrayList<String> palavrasRemovidas = new ArrayList<>();


        if(escolhaAnterior.equalsIgnoreCase(""))
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
                //1. Transformando em array a palavra escolhida anteriormente pelo robô
                char[] escolhaAnteriorSplit = escolhaAnterior.toCharArray();

                //2. Pegando a letra da palavra escolhida pelo robô na posição atual do array
                String letraParaRemover = Character.toString(escolhaAnteriorSplit[a]);


                //3. Passando pelas letras disponíveis
                for(int b = 0; b < letrasDisponiveis.size(); b++)
                {
                    String letraAtual = letrasDisponiveis.get(b);


                    //4. Verificando se a letra existe na lista de letras
                    if(letrasDisponiveis.get(b).equalsIgnoreCase(letraParaRemover))
                    {
                        //5. Passar pelas palavras para remover todas que tem essa letra nessa posição
                        for(int d = 0; d < palavrasDisponiveis.size(); d++)
                        {
                            //6. Pegando palavra atual da lista de palavras
                            String palavraAtual = palavrasDisponiveis.get(d);

                            //Transformando palavra em array e pegando a letra da posição atual
                            //String letraDaPosicaoParaRemover = Character.toString(palavraAtual.toCharArray()[a]);

                            //7. Transformando palavra em array
                            char[] palavraAtual_Array = palavraAtual.toCharArray();

                            //8. Passando pelo array da palavra
                            for(int e = 0; e < palavraAtual_Array.length; e++)
                            {
                                //9. Pegando a letra atual
                                String letraDaPosicaoParaRemover = Character.toString(palavraAtual_Array[e]);


                                //10. Verificando se a letra da posição incorreta é igual a da palavra atual
                                if(letraDaPosicaoParaRemover.equalsIgnoreCase(letraParaRemover))
                                {
                                    //11. Remova a palavra
                                    System.out.println("Palavra removida: " + palavraAtual);
                                    palavrasRemovidas.add(palavraAtual);
                                    palavrasDisponiveis.remove(d);
                                    d = d - 1;
                                    break;
                                }
                            }

                        }

                        letrasDisponiveis.remove(b);
                        break;
                    }
                }
            }
        }


        if(!escolhaAnterior.equalsIgnoreCase(""))
        {
            String caminhoEscrita = "historico-palavras/removidos0_escolha_" + palpite + ".txt";

            // Escrevendo as palavras em um novo arquivo
            try
            {
                File arquivoEscrita = new File(caminhoEscrita);
                BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoEscrita));

                writer.write("Escolha: " + escolhaAnterior);
                writer.newLine();

                writer.write("Array usuário: " + Arrays.toString(posicoesUsuario));
                writer.newLine();
                writer.newLine();

                for (String palavra : palavrasRemovidas) {
                    writer.write(palavra);
                    writer.newLine();
                }

                writer.close();

            }
            catch (IOException e)
            {
                System.out.println("Erro ao escrever no arquivo: " + e.getMessage());
            }
        }



        dicionario.palavras = palavrasDisponiveis;
        dicionario.letras = letrasDisponiveis;

        return  dicionario;
    }

    public static ArrayList<String> RoboFiltraPosicaoUm(ArrayList<String> palavrasDisponiveis, int[] posicoesUsuario, String escolhaAnterior, int palpite)
    {

        ArrayList<String> palavrasDisponiveisFiltradas = new ArrayList<>();
        ArrayList<String> palavrasRemovidas = new ArrayList<>();

        boolean findValueOne = false;

        //Passando pelo array de sugestão do usuário
        for(int a = 0; a < posicoesUsuario.length; a++)
        {
            //Verificando se o valor é 1
            if(posicoesUsuario[a] == 1)
            {
                int posicaoDaLetraCorreta = a;

                //Passanado pelas palavras disponíveis
                for(int b = 0; b < palavrasDisponiveis.size(); b++)
                {
                    String palavraAtual = palavrasDisponiveis.get(b);

                    boolean findLyric = false;

                    //Passando pelos caracteres da palavra
                    for(int c = 0; c < palavraAtual.length(); c++)
                    {
                        //Se for a mesma posição da letra correta
                        if(c == posicaoDaLetraCorreta)
                        {
                            //Se for a mesma letra da escolha anterior
                            if(escolhaAnterior.toCharArray()[c] == palavraAtual.toCharArray()[c])
                            {
                                //Adiciona nas palavras filtradas
                                if(!palavrasDisponiveisFiltradas.contains(palavraAtual))
                                {
                                    palavrasDisponiveisFiltradas.add(palavraAtual);
                                    findValueOne = true;
                                    findLyric = true;
                                }
                            }
                        }
                    }

                    //Se nenhuma letra correta foi encontrada na palavra atual, é uma palavra removida
                    if(!findLyric)
                    {
                        palavrasRemovidas.add(palavraAtual);
                    }

                }

            }
        }



        if(!escolhaAnterior.equalsIgnoreCase(""))
        {
            //Se foi encontrado algum valor 1, então essas palavras foram removidas
            if(findValueOne)
            {
                if(palavrasRemovidas.size() > 0)
                {
                    String caminhoEscrita = "historico-palavras/removidos1_escolha_" + palpite + ".txt";

                    // Escrevendo as palavras em um novo arquivo
                    try
                    {
                        File arquivoEscrita = new File(caminhoEscrita);
                        BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoEscrita));

                        writer.write("Escolha: " + escolhaAnterior);
                        writer.newLine();

                        writer.write("Array usuário: " + Arrays.toString(posicoesUsuario));
                        writer.newLine();
                        writer.newLine();

                        for (String palavra : palavrasRemovidas) {
                            writer.write(palavra);
                            writer.newLine();
                        }

                        writer.close();

                    }
                    catch (IOException e)
                    {
                        System.out.println("Erro ao escrever no arquivo: " + e.getMessage());
                    }
                }
            }
        }




        if(findValueOne)
        {
            return palavrasDisponiveisFiltradas;
        }
        else
        {
            return palavrasDisponiveis;
        }

    }

    public static ArrayList<String> RoboFiltraPosicaoDois(ArrayList<String> palavrasDisponiveis, int[] posicoesUsuario, String escolhaAnterior, int palpite)
    {

        ArrayList<String> palavrasRemovidas = new ArrayList<>();


        //Passando pelo array de sugestão do usuário
        for(int a = 0; a < posicoesUsuario.length; a++)
        {
            //Verificando se a posição é valor 2
            if(posicoesUsuario[a] == 2)
            {
                int posicaoDaLetraMeiaCorreta = a;

                //Passanado pelas palavras disponíveis
                for(int b = 0; b < palavrasDisponiveis.size(); b++)
                {

                    String palavraAtual = palavrasDisponiveis.get(b);

                    //Passando pelos caracteres da palavra
                    for(int c = 0; c < palavraAtual.length(); c++)
                    {
                        //Se for a mesma posição da letra meia correta
                        if(c == posicaoDaLetraMeiaCorreta)
                        {
                            //Se for a mesma letra da escolha anterior
                            if(escolhaAnterior.toCharArray()[c] == palavraAtual.toCharArray()[c])
                            {
                                //Adiciona nas palavras filtradas
                                palavrasRemovidas.add(palavraAtual);
                                palavrasDisponiveis.remove(palavraAtual);
                            }
                        }
                    }

                }

            }
        }




        if(!escolhaAnterior.equalsIgnoreCase(""))
        {
            if(palavrasRemovidas.size() > 0)
            {
                String caminhoEscrita = "historico-palavras/removidos2_escolha_" + palpite + ".txt";

                // Escrevendo as palavras em um novo arquivo
                try
                {
                    File arquivoEscrita = new File(caminhoEscrita);
                    BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoEscrita));

                    writer.write("Escolha: " + escolhaAnterior);
                    writer.newLine();

                    writer.write("Array usuário: " + Arrays.toString(posicoesUsuario));
                    writer.newLine();
                    writer.newLine();

                    for (String palavra : palavrasRemovidas) {
                        writer.write(palavra);
                        writer.newLine();
                    }

                    writer.close();

                }
                catch (IOException e)
                {
                    System.out.println("Erro ao escrever no arquivo: " + e.getMessage());
                }
            }
        }


        return palavrasDisponiveis;
    }




    //Geradores--------------------------------------------------------------------------------------------------------
    private static int[] GerarPosicoesCorretas(Scanner scanner) //Pergunta para o usuário o tamanho das palavras
    {
        //Solicitar um número ao usuário
        System.out.print("Informe a quantidade de letras das palavras que você deseja gerar: ");
        int tamanhoArray = scanner.nextInt();

        //Gerar array de inteiros com o tamanho informado
        int[] meuArray = new int[tamanhoArray];

        //Inicializar todas as posições com o valor 0
        for (int i = 0; i < meuArray.length; i++) {
            meuArray[i] = 3;
        }


        return meuArray;
    }
    private static int[] TransformarPosicoesEmArray(int[] posicoesCorretas) //Pergunta ao usuário se as posições estão corretas
    {
        MostrarArray_Int("Posições sugeridas", posicoesCorretas);

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
        if(array.split(",").length != posicoesCorretas.length)
        {
            throw new RuntimeException("\n\nProblema: O array inserido tem tamanho diferente do permitido.\n\n");
        }




        //Passando pelo array do usuário
        for(int a = 0; a < posicoesCorretas.length; a++)
        {
            //Verificando se posição atual é uma posição correta
            if(posicoesCorretas[a] == 1)
            {
                posicoesCorretas[a] = 1;
            }
            else
            {
                //Verificando se é um inteiro válido
                try
                {
                    // Tenta converter a string para um número
                    int numero = Integer.parseInt(array.split(",")[a]);

                    //Adiciona o valor que o usuário informou na posição do array
                    posicoesCorretas[a] = numero;
                }
                catch (NumberFormatException e)
                {
                    throw new NumberFormatException("\n\nErro: A string '" + array.split(",")[a] + "' na posição " + a +" do seu array não é um número.");
                }
            }
        }

        return posicoesCorretas;
    }



    private static ArrayList<String> GerarLetras() //Gera as letras iniciais
    {
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
    private static ArrayList<String> GerarPalavras(int limitChar) //Gera as palavras iniciais
    {
        String caminhoDoArquivo = "palavras.txt";

        ArrayList<String> palavrasDisponiveis = new ArrayList<>();

        try
        {
            FileReader fileReader = new FileReader(caminhoDoArquivo);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String linha;
            while ((linha = bufferedReader.readLine()) != null)
            {
                if(linha.length() == limitChar)
                {
                    palavrasDisponiveis.add(linha);
                }
            }
        }
        catch (IOException e)
        {
            System.err.println("Erro ao ler o arquivo de palavras: " + e.getMessage());
        }

        return palavrasDisponiveis;
    }






    //Filtros----------------------------------------------------------------------------------------------------------
    private static ArrayList<String> FiltrarPorTamanho(ArrayList<String> palavrasDisponiveis, int tamanhoPermitido)
    {
        ArrayList<String> palavrasFiltradas = new ArrayList<>();

        for(int a = 0; a < palavrasDisponiveis.size(); a++)
        {
            if(palavrasDisponiveis.get(a).trim().length() == tamanhoPermitido)
            {
                palavrasFiltradas.add(palavrasDisponiveis.get(a));
            }

        }

        return palavrasFiltradas;
    }
    private static ArrayList<String> FiltrarPorLetra(ArrayList<String> palavrasDisponiveis, ArrayList<String> letrasDisponiveis) //Pega uma lista de palavras e remove todas as palavras que tenham letras que não está na lista de letras
    {
        //Passando pela lista de palavras recebida
        for (int z = 0; z < palavrasDisponiveis.size(); z++)
        {
            String palavra = palavrasDisponiveis.get(z);

            //Passando pelos caracteres da palavra
            for (int a = 0; a < palavra.length(); a++) {

                String caractere = Character.toString(palavra.charAt(a));

                boolean existeLetra = false;

                //Passando pelas letras disponíveis
                for(int b = 0; b < letrasDisponiveis.size(); b++)
                {
                    String letra = letrasDisponiveis.get(b);

                    String actLetraPalavra  = caractere.toUpperCase().trim();
                    String actLetraList = letra.toUpperCase().trim();


                    //Verificando se a letra está na lista de letras
                    if (actLetraPalavra.equalsIgnoreCase(actLetraList))
                    {
                        existeLetra = true;
                        break;
                    }
                }

                //Se a letra não foi encontrada
                if(!existeLetra)
                {
                    //Remova a palavra
                    palavrasDisponiveis.remove(z);
                    z = z - 1;
                    break;
                }
            }
        }

        return palavrasDisponiveis;
    }







    //Utils------------------------------------------------------------------------------------------------------------
    private static int TipoDeExecucao(Scanner scanner) //Pergunta ao usuário se quer executar como dev ou user
    {

        // Solicitar tipo de execução
        System.out.print("Digite 1 para executar em modo 'dev' ou 2 para executar em modo 'user': ");
        int decisao = scanner.nextInt();

        return decisao;
    }


    private static void MostrarArray_Int(String nome, int[] array) //Mostra os valores de um array de int
    {
        // Imprimir o array gerado
        System.out.println("\n" + nome + ": ");

        int counter = 1;

        for (int valor : array) {

            if(counter == 5)
            {
                System.out.print(valor + "\n");
                counter = 1;
            }else
            {
                System.out.print(valor + " - ");
                counter ++;
            }

        }
    }
    private static void MostrarLista_String(String nome, ArrayList<String> lista) //Mostra os valores de uma lista de string
    {
        // Imprimir a lista gerada
        System.out.println("\n\n\n" + nome + ": ");

        int counter = 0;

        for (String valor : lista) {

            if(counter == 5)
            {
                System.out.print(valor + "\n");
                counter = 1;
            }else
            {
                System.out.print(valor + " - ");
                counter ++;
            }

        }
    }



}