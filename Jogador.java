import java.io.Serializable;

public abstract class Jogador implements Serializable {
    private String nome = new String();
    private char tipo;
    private JogoDados[] jogosAdicionados;
    private int escolhaJogo;
    private float saldo;
    private float valorDaAposta;
    private float[] apostas;
    private boolean estaCheio;

    public Jogador(String nome, char tipo) { // Inicializa jogador.
        this.nome = nome;
        this.tipo = tipo;
        this.jogosAdicionados = new JogoDados[10];
        this.escolhaJogo = 1;
        this.saldo = 100.0f; // Saldo inicial é de R$100.00.
        this.valorDaAposta = 0.0f;
        this.apostas = new float[10];
        this.estaCheio = false;
    }

    // Métodos getters:
    public String getNome() { // Retorna o nome.
        return this.nome;
    }

    public char getTipo(){
        return this.tipo;
    }

    public JogoDados[] getJogosAdicionados() {
        return this.jogosAdicionados;
    }

    public int getEscolhaJogo() {
        return this.escolhaJogo;
    }

    public float getSaldo(){
        return this.saldo;
    }

    public float getValorDaAposta(){
        return this.valorDaAposta;
    }

    public boolean getEstaCheio(){
        return this.estaCheio;
    }

    // Métodos setters:

    public void setNome(String nome) { // Altera o nome.
        this.nome = nome;
    }

    public void setTipo(char tipo) {
        this.tipo = tipo;
    }

    public void setEscolhaJogo(int escolhaJogo) {
        this.escolhaJogo = escolhaJogo;
    }

    public void setValorDaAposta(float aposta){
        this.valorDaAposta = aposta;
    }
    
    public void setApostas(int i, float aposta){
        this.apostas[i] = aposta;
    }

    // Este método diminui ou aumenta o saldo de acordo com a vitória (true) ou derrota (false) do jogador em sua aposta:
    public void atualizarSaldo(boolean resultadoDaAposta){
        if(resultadoDaAposta == true){
            this.saldo += this.valorDaAposta;
        }
        else if (resultadoDaAposta == false){
            this.saldo -= this.valorDaAposta;

            if(this.saldo < 0){
                this.saldo = 0;
            }
        }
    }

    // Este método encontra o próximo índice livre do vetor jogosAdicionados:
    public int getIndiceLivre(){
        int i = 0;

        while(i < 10 && jogosAdicionados[i] != null){
            i++;
        }

        return i;
    }

    // O jogo que está sendo rodado é o anterior ao próximo livre do vetor jogosAdicionados. 
    // Este método o busca e o retorna:
    public JogoDados getJogoAtual(){
        int indice = getIndiceLivre() - 1;

        return this.jogosAdicionados[indice];
    }

    // Este método adiciona um novo jogo no vetor jogosAdcionados:
    public void adicionarJogoNoVetor(JogoDados jogoAtual) {
        int i = getIndiceLivre();

        if (i >= 0 && i < 10 && this.jogosAdicionados[i] == null) {
            this.jogosAdicionados[i] = jogoAtual;
        }
    }

    // Retorna pontuação de uma jogada específica para as tabelas intermediárias:
    public String cartela2(JogoGeneral jogo, int i){ 
        String s = new String();
        s = jogo.montarTabela2(i);
        return s;
    }

    // Mostra a tabela intermediária do Jogo General que está sendo executado:
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

    public boolean resultado(JogoGeneral jogoG){ // Retorna o resultado do Jogo General ('true' para vitória, 'false' para derota).
        return jogoG.calculaResultado();
    }

    // Verifica se ainda é possível adicionar algum jogo para o jogador. Retorna 'true' se pode e 'false' se não.
    public boolean verificaJogosLivres(){
        if(this.estaCheio == false){
            for(JogoDados jogo: jogosAdicionados){
                if(jogo == null){
                    return true;
                }
            }
        }

        this.estaCheio = true;
        return false;
    }

}