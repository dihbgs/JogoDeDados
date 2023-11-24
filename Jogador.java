import java.io.Serializable;

public abstract class Jogador implements Serializable {
    private String nome = new String();
    private char tipo;
    private JogoDados[] jogosCompletos;
    JogoGeneral jogo;
    private int escolhaJogo;

    public Jogador(String nome, char tipo) { // Inicializa jogador.
        this.nome = nome;
        this.tipo = tipo;
        this.jogosCompletos = new JogoDados[10];
        this.jogo = new JogoGeneral();
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

    public int getIndiceLivre(){
        int i = 0;

        while(jogosCompletos[i] != null && i < 10){
            i++;
        }

        return i;
    }

    public void adicionarJogoNoVetor(int i, JogoDados jogoAtual) {
        if ((i >= 0 && i < 10) && this.jogosCompletos[i] == null && i < 10) {
            this.jogosCompletos[i] = jogoAtual;
        }
    }

    public String cartela(int i){ // Retorna a pontuação de uma jogada específica para a tabela final.
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

    public void inicializarPartida(){
        jogo.inicializarJogadas();
    }

    public void jogada(JogoGeneral jogo){ // Efetua uma jogada, rolando os dados e imprimindo.
        jogo.rolarDados();
        String s = jogo.toString(); 
 
        System.out.printf(s);
    }

    public boolean validar(int escolha){ // Valida a jogada.
        return jogo.validarJogada(escolha);
    }

    public int total(){ // Calcula pontuacao total.
        return jogo.calculaTotal();
    }
    
}