import Entity.DicionarioDTO;
import jdk.jshell.spi.ExecutionControl;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
import java.util.concurrent.ExecutionException;


public class Main
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);

        int modo = TipoDeExecucao(scanner);

        if(modo == 1)
        {
            //Gerando array de decisões (Pergunta o tamanho das palavras)
            int[] posicoesCorretas = GerarPosicoesCorretas(scanner);


            //Gerando letras e palavras disponíveis
            ArrayList<String> letrasDisponiveis = GerarLetras();
            ArrayList<String> palavrasDisponiveis = GerarPalavras();


            //Filtrando palavras com o tamanho escolhido
            palavrasDisponiveis = FiltrarPorTamanho(palavrasDisponiveis, posicoesCorretas.length);

            MostrarLista_String("Palavras disponíveis", palavrasDisponiveis);

            boolean isCorrectAwnser = false;
            String escolha = "";


            MostrarArray_Int("Posições corretas", posicoesCorretas);
            MostrarLista_String("Letras disponíveis", letrasDisponiveis);

            //Filtragem por letras disponíveis
            ArrayList<String> palavrasFiltradas = FiltrarPorLetra(palavrasDisponiveis, letrasDisponiveis);
            MostrarLista_String("Palavras disponíveis", palavrasFiltradas);


            //Não finaliza enquanto não encontrar a resposta
            while(!isCorrectAwnser)
            {

                //Removendo sugestões zeradas
                DicionarioDTO dicionario = RoboRemovePosicaoZero(palavrasFiltradas, letrasDisponiveis, posicoesCorretas, escolha);

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
                MostrarLista_String("Letras disponíveis", letrasDisponiveis);

                //Filtragem por letras disponíveis
                palavrasFiltradas = FiltrarPorLetra(palavrasDisponiveis, letrasDisponiveis);
                MostrarLista_String("Palavras disponíveis", palavrasFiltradas);


                System.out.println("\n\nRobô escolheu: " + escolha);

                //Usuário verifica quais posições estão corretas
                posicoesCorretas = TransformarPosicoesEmArray(posicoesCorretas);





                //break;
            }

        }
        else if(modo == 2)
        {
            throw new RuntimeException("\n\nModo user não implementado.\n\n");
        }
        else
        {
            throw new RuntimeException("\n\nErro: Decisão inválida.\n\n");
        }
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
                //Passar pelas posições corretas-------------------

                Random geradorAleatorio = new Random();

                //palavrasDisponiveis = RoboRemovePosicaoZero(palavrasDisponiveis, letrasDisponiveis, posicoesUsuario, escolhaAnterior).palavras;
                palavrasDisponiveis = RoboFiltraPosicaoUm(palavrasDisponiveis, posicoesUsuario, escolhaAnterior);
                palavrasDisponiveis = RoboFiltraPosicaoDois(palavrasDisponiveis, posicoesUsuario, escolhaAnterior);


                // Gerar um número aleatório entre 0 e o tamanho máximo de palavras disponíveis
                int numeroAleatorio = geradorAleatorio.nextInt((palavrasDisponiveis.size() - 1));

                return palavrasDisponiveis.get(numeroAleatorio);
            }
        }
        else
        {
            return "";
        }
    }

    public static DicionarioDTO RoboRemovePosicaoZero(ArrayList<String> palavrasDisponiveis, ArrayList<String> letrasDisponiveis, int[] posicoesUsuario, String escolhaAnterior)
    {
        DicionarioDTO dicionario = new DicionarioDTO();

        if(escolhaAnterior == "")
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

    public static ArrayList<String> RoboFiltraPosicaoUm(ArrayList<String> palavrasDisponiveis, int[] posicoesUsuario, String escolhaAnterior)
    {

        ArrayList<String> palavrasDisponiveisFiltradas = new ArrayList<>();
        boolean findValueOne = false;

        //METODO FILTRANDO POR PROMT_APENAS VALORES 1
        for(int a = 0; a < posicoesUsuario.length; a++)
        {
            //Passando pelo aray de sugestão do usuário
            if(posicoesUsuario[a] == 1)
            {
                int posicaoDaLetraCorreta = a;

                //Passanado pelas palavras disponíveis
                for(int b = 0; b < palavrasDisponiveis.size(); b++)
                {

                    String palavraAtual = palavrasDisponiveis.get(b);

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
                                palavrasDisponiveisFiltradas.add(palavraAtual);
                                findValueOne = true;
                            }
                        }
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

    public static ArrayList<String> RoboFiltraPosicaoDois(ArrayList<String> palavrasDisponiveis, int[] posicoesUsuario, String escolhaAnterior)
    {
        //METODO FILTRANDO POR PROMT_APENAS VALORES 1
        for(int a = 0; a < posicoesUsuario.length; a++)
        {
            //Passando pelo aray de sugestão do usuário
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
                                palavrasDisponiveis.remove(palavraAtual);
                            }
                        }
                    }

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
    private static ArrayList<String> GerarPalavras() //Gera as palavras iniciais
    {
        ArrayList<String> palavrasDisponiveis = new ArrayList<>();

        palavrasDisponiveis.add("a");
        palavrasDisponiveis.add("à");
        palavrasDisponiveis.add("Aarão");
        palavrasDisponiveis.add("aba");
        palavrasDisponiveis.add("abacate");
        palavrasDisponiveis.add("abacateiro");
        palavrasDisponiveis.add("abacateiros");
        palavrasDisponiveis.add("abacates");
        palavrasDisponiveis.add("abacaxi");
        palavrasDisponiveis.add("abacaxis");

        palavrasDisponiveis.add("burras");
        palavrasDisponiveis.add("burrice");
        palavrasDisponiveis.add("burrices");
        palavrasDisponiveis.add("burrico");
        palavrasDisponiveis.add("burricos");
        palavrasDisponiveis.add("burrinha");
        palavrasDisponiveis.add("burrinhas");
        palavrasDisponiveis.add("burrinho");
        palavrasDisponiveis.add("burrinhos");
        palavrasDisponiveis.add("burriquinho");
        palavrasDisponiveis.add("burro");

        palavrasDisponiveis.add("hipnotizando");
        palavrasDisponiveis.add("hipnotizar");
        palavrasDisponiveis.add("hipnotizara");
        palavrasDisponiveis.add("hipnotizará");
        palavrasDisponiveis.add("hipnotizaram");
        palavrasDisponiveis.add("hipnotizáramos");
        palavrasDisponiveis.add("hipnotizarão");
        palavrasDisponiveis.add("hipnotizaras");
        palavrasDisponiveis.add("hipnotizarás");
        palavrasDisponiveis.add("hipnotizardes");
        palavrasDisponiveis.add("hipnotizarei");
        palavrasDisponiveis.add("hipnotizareis");
        palavrasDisponiveis.add("hipnotizáreis");
        palavrasDisponiveis.add("hipnotizarem");
        palavrasDisponiveis.add("hipnotizaremo");

        palavrasDisponiveis.add("hispânico");
        palavrasDisponiveis.add("hispânicos");
        palavrasDisponiveis.add("hispanismo");
        palavrasDisponiveis.add("hispanista");
        palavrasDisponiveis.add("hispanistas");
        palavrasDisponiveis.add("histeria");
        palavrasDisponiveis.add("histerias");
        palavrasDisponiveis.add("histérica");
        palavrasDisponiveis.add("histéricas");
        palavrasDisponiveis.add("histérico");
        palavrasDisponiveis.add("histéricos");
        palavrasDisponiveis.add("histerismo");
        palavrasDisponiveis.add("histerismos");
        palavrasDisponiveis.add("histograma");

        palavrasDisponiveis.add("objetivações");
        palavrasDisponiveis.add("objetivado");
        palavrasDisponiveis.add("objetivai");
        palavrasDisponiveis.add("objetivais");
        palavrasDisponiveis.add("objetivam");
        palavrasDisponiveis.add("objetivamente");
        palavrasDisponiveis.add("objetivamo");
        palavrasDisponiveis.add("objetivamos");
        palavrasDisponiveis.add("objetivando");
        palavrasDisponiveis.add("objetivar");
        palavrasDisponiveis.add("objetivara");
        palavrasDisponiveis.add("objetivará");
        palavrasDisponiveis.add("objetivaram");
        palavrasDisponiveis.add("objetiváramos");
        palavrasDisponiveis.add("objetivarão");
        palavrasDisponiveis.add("objetivaras");
        palavrasDisponiveis.add("objetivarás");
        palavrasDisponiveis.add("objetivardes");
        palavrasDisponiveis.add("objetivarei");

        palavrasDisponiveis.add("sistematizaríamos");
        palavrasDisponiveis.add("sistematizarias");
        palavrasDisponiveis.add("sistematizaríeis");
        palavrasDisponiveis.add("sistematizarmo");
        palavrasDisponiveis.add("sistematizarmos");
        palavrasDisponiveis.add("sistematizas");
        palavrasDisponiveis.add("sistematizasse");
        palavrasDisponiveis.add("sistematizásseis");
        palavrasDisponiveis.add("sistematizassem");
        palavrasDisponiveis.add("sistematizássemos");
        palavrasDisponiveis.add("sistematizasses");
        palavrasDisponiveis.add("sistematizaste");
        palavrasDisponiveis.add("sistematizastes");
        palavrasDisponiveis.add("sistematizava");
        palavrasDisponiveis.add("sistematizavam");
        palavrasDisponiveis.add("sistematizávamos");

        palavrasDisponiveis.add("treinemos");
        palavrasDisponiveis.add("treines");
        palavrasDisponiveis.add("treino");
        palavrasDisponiveis.add("treinou");
        palavrasDisponiveis.add("trejeito");
        palavrasDisponiveis.add("trejeitos");
        palavrasDisponiveis.add("treliça");
        palavrasDisponiveis.add("treliças");
        palavrasDisponiveis.add("trem");
        palavrasDisponiveis.add("trema");
        palavrasDisponiveis.add("tremais");
        palavrasDisponiveis.add("tremam");
        palavrasDisponiveis.add("tremamo");
        palavrasDisponiveis.add("tremamos");
        palavrasDisponiveis.add("tremas");
        palavrasDisponiveis.add("treme");
        palavrasDisponiveis.add("tremê");
        palavrasDisponiveis.add("tremedeira");
        palavrasDisponiveis.add("tremedeiras");
        palavrasDisponiveis.add("tremedor");

        palavrasDisponiveis.add("zurres");
        palavrasDisponiveis.add("zurro");
        palavrasDisponiveis.add("zurros");
        palavrasDisponiveis.add("zurrou");
        palavrasDisponiveis.add("zurrou");


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