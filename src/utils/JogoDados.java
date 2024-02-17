package src.utils;

import java.io.Serializable;

// Nesta classe ocorre herança múltipla, pois ela implementa duas interfaces: Serializable, para poder ser
// gravada posteriormente, e Estatistica, para implementar seus métodos.
public abstract class JogoDados implements Serializable, Estatistica {
    private Dado[] dados;
    private String nome;
    private int[] facesRoladas;

    public JogoDados(int nDados, String nome) {
        this.dados = new Dado[nDados];
        this.nome = nome;

        for (int i = 0; i < nDados; i++) {
            this.dados[i] = new Dado();
        }

        this.facesRoladas = new int[numFaces];

        for (int i = 0; i < numFaces; i++) {
            facesRoladas[i] = 0;
        }
    }

    // Métodos getters:

    public Dado[] getDados() {
        return this.dados;
    }

    public String getNome() {
        return this.nome;
    }

    public int[] getFacesRoladas() {
        return facesRoladas;
    }

    // Método que soma um ao índice representante de uma face do dado toda vez que
    // ela é sorteada:
    public void somarFacesSorteadas(int i) {
        this.facesRoladas[i - 1] = this.facesRoladas[i - 1] + 1;
    }

    // Método que rola os dados e grava seus resultado no vetor facesRoladas para
    // futuro cálculo de estatística:
    public void rolarDados() {
        for (Dado dado : dados) {
            dado.roll();
            somarFacesSorteadas(dado.getValue());
        }
    }

    // Método abstrato que imprime os resultados do sorteio dos dados, a ser
    // implementado por suas subclasses:
    public abstract void imprimirResultado();
}
