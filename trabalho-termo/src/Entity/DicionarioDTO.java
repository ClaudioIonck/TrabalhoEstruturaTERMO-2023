package Entity;

import java.util.ArrayList;
import java.util.Objects;

public class DicionarioDTO
{

    public ArrayList<String> palavras;
    public ArrayList<String> letras;


    public DicionarioDTO() {
    }

    public DicionarioDTO(ArrayList<String> palavras, ArrayList<String> letras) {
        this.palavras = palavras;
        this.letras = letras;
    }





    public ArrayList<String> getPalavras() {
        return palavras;
    }

    public void setPalavras(ArrayList<String> palavras) {
        this.palavras = palavras;
    }

    public ArrayList<String> getLetras() {
        return letras;
    }

    public void setLetras(ArrayList<String> letras) {
        this.letras = letras;
    }
}
