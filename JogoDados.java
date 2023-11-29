import java.io.Serializable;

public abstract class JogoDados implements Serializable, Estatistica{
    private Dado[] dados;
    private int[] facesRoladas;

    public JogoDados(int nDados) {
        this.dados = new Dado[nDados];

        for (int i = 0; i < nDados; i++) {
            this.dados[i] = new Dado();
        }

        this.facesRoladas = new int[numFaces];

        for(int i = 0; i < numFaces; i++){
            facesRoladas[i] = 0;
        }
    }

    // Métodos getters:

    public int[] getFacesRoladas() {
        return facesRoladas;
    }

    public Dado[] getDados(){
        return this.dados;
    }

    // Método que soma um ao índice representante de uma face do dado toda vez que ela é sorteada:
    public void somarFacesSorteadas(int i){
        this.facesRoladas[i - 1] = this.facesRoladas[i - 1] + 1;
    }

    // Método que rola os dados e grava seus resultado no vetor facesRoladas para futuro cálculo de estatística:
    public void rolarDados() {
        for (Dado dado : dados) {
            dado.roll();
            somarFacesSorteadas(dado.getFaceSuperior());
        }
    }

    // Método abstrato que imprime os resultados do sorteio dos dados, a ser implementado por suas subclasses:
    public abstract void imprimirResultado();
}
