import java.util.ArrayList;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        //Gerando array de decisões
        int[] posicoesCorretas = GerarPosicoesCorretas();

        MostrarArray_Int("Posições corretas", posicoesCorretas);




        //Gerando letras e palavras disponíveis
        ArrayList<String> letrasDisponiveis = GerarLetras();
        ArrayList<String> palavrasDisponiveis = GerarPalavras();



        MostrarLista_String("Letras disponíveis", letrasDisponiveis);
        MostrarLista_String("Palavras disponíveis", palavrasDisponiveis);


    }






    //Geradores
    private static int[] GerarPosicoesCorretas()
    {
        Scanner scanner = new Scanner(System.in);

        // Solicitar um número ao usuário
        System.out.print("Informe a quantidade de letras das palavras que você deseja gerar: ");
        int tamanhoArray = scanner.nextInt();

        // Gerar array de inteiros com o tamanho informado
        int[] meuArray = new int[tamanhoArray];

        // Inicializar todas as posições com o valor 0
        for (int i = 0; i < meuArray.length; i++) {
            meuArray[i] = 0;
        }

        scanner.close();

        return meuArray;
    }


    private static ArrayList<String> GerarLetras()
    {
        ArrayList<String> letrasDisponiveis = new ArrayList<>();

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

    private static ArrayList<String> GerarPalavras()
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



    //Filtros
    private static ArrayList<String> FiltrarPorLetra(ArrayList<String> palavrasDisponiveis, ArrayList<String> letrasDisponiveis)
    {
        ArrayList<String> palavrasFiltradas = new ArrayList<>();



        return palavrasFiltradas;
    }



    //Utils
    private static void MostrarArray_Int(String nome, int[] array)
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
    private static void MostrarLista_String(String nome, ArrayList<String> lista)
    {
        // Imprimir a lista gerada
        System.out.println("\n" + nome + ": ");

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