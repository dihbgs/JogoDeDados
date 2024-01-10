package src.utils;
import java.util.Random;
import java.io.Serializable;

public class Dado implements Serializable{
    private int faceSup;

    public Dado(){          // Construtor padrão: vai inicializar o dado com a face superior sendo 1.
        faceSup = 1;
    }

    public Dado(int face){   // Sobrecarga do construtor: vai inicializar a face superior com o valor passado por parâmetro.
        this.faceSup = face;
    }

    public int getFaceSuperior(){ // Retorna a face superior do dado.
        return  faceSup;
    }

    public void roll(){          // Rola o dado. Para sua face superior sera atribuido um valor entre 1 e 6.
        Random random = new Random();
        this.faceSup = random.nextInt(6) + 1;
    }

    public String toString(){    // Imprime a face superior do dado.  
        return Integer.toString(faceSup);
    }
    
}