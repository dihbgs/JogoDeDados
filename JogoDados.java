import java.util.Scanner;

public class JogoDados {
    protected int nDados;
    protected String nomeJogo;
    protected float saldo;
    protected Dado[] dados;

    public JogoDados(int nDados, String nomeJogo) {
        this.nDados = nDados;
        this.nomeJogo = nomeJogo;
        this.saldo = 100.0f; // Saldo inicial é 100.0
        this.dados = new Dado[nDados];

        for (int i = 0; i < nDados; i++) {
            this.dados[i] = new Dado();
        }
    }

    public Dado[] getDados(){
        return this.dados;
    }

    public void rolarDados() {
        for (Dado dado : dados) {
            dado.roll();
        }
    }

    public void imprimirResultado() {
        System.out.println("Resultado do " + nomeJogo + ":");

        for (Dado dado : dados) {
            System.out.print(dado.getFaceSuperior() + " ");
        }

        System.out.println();
    }

    public void imprimirDadosUmAUm(){
        int i = 1;

        for(Dado dado : dados){
            System.out.println("Valor do dado " + i + ":" + dado.getFaceSuperior());
            i++;
        }
    }
}
