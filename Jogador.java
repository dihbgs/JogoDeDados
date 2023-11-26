import java.io.Serializable;

public abstract class Jogador implements Serializable {
    private String nome = new String();
    private char tipo;
    private JogoDados[] jogosAdicionados;
    private int escolhaJogo;
    private float saldo;
    private float valorDaAposta;

    public Jogador(String nome, char tipo) { // Inicializa jogador.
        this.nome = nome;
        this.tipo = tipo;
        this.jogosAdicionados = new JogoDados[10];
        this.saldo = 100.0f; // Saldo inicial é 100.0
    }

    public String getNome() { // Retorna o nome.
        return nome;
    }

    public char getTipo(){
        return this.tipo;
    }

    public void setNome(String nome) { // Altera o nome.
        this.nome = nome;
    }

    public void setTipo(char tipo) {
        this.tipo = tipo;
    }

    public void setEscolhaJogo(int escolhaJogo) {
        this.escolhaJogo = escolhaJogo;
    }
    
    public int getEscolhaJogo() {
        return escolhaJogo;
    }

    public float getSaldo(){
        return this.saldo;
    }

    public float getValorDaAposta(){
        return this.valorDaAposta;
    }

    public void setValorDaAposta(float aposta){
        this.valorDaAposta = aposta;
    }

    public int getIndiceLivre(){
        int i = 0;

        while(jogosAdicionados[i] != null && i < 10){
            i++;
        }

        return i;
    }

    public JogoDados getJogoAtual(){
        return this.jogosAdicionados[getIndiceLivre() - 1];
    }

    public void adicionarJogoNoVetor(JogoDados jogoAtual) {
        int i = getIndiceLivre();

        if ((i >= 0 && i < 10) && this.jogosAdicionados[i] == null && i < 10) {
            this.jogosAdicionados[i] = jogoAtual;
        }
    }

    public String cartela(JogoGeneral jogo, int i){ // Retorna a pontuação de uma jogada específica para a tabela final.
        String s = new String();
        s = jogo.montarTabela(i);
        return s;
    }

    public String cartela2(JogoGeneral jogo, int i){ // Retorna pontuação de uma jogada específica para as tabelas intermediárias.
        String s = new String();
        s = jogo.montarTabela2(i);
        return s;
    }

    public String mostraJogadasExecutadas(JogoGeneral jogo){
        String s = new String();

        s = "1\t2\t3\t4\t5\t6\t7(T)\t8(Q)\t9(F)\t10(S+)\t11(S-)\t12(G)\t13(X)\n";

        for(int i = 1; i <= 13; i++){
            s = s + this.cartela2(jogo, i);
        }
        return s;
    }

    public void jogada(JogoGeneral jogo){ // Efetua uma jogada, rolando os dados e imprimindo.
        jogo.rolarDados();
        String s = jogo.toString(); 
 
        System.out.printf(s);
    }

    public int total(JogoGeneral jogoG){ // Calcula pontuacao total.
        return jogoG.calculaTotal();
    }

    public boolean verificaJogosLivres(){
        for(JogoDados jogo: jogosAdicionados){
            if(jogo == null){
                return true;
            }
        }

        return false;
    }
}