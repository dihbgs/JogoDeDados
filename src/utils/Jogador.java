package src.utils;
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

    public char getTipo(){   // Retorna o tipo.
        return this.tipo;
    }

    public JogoDados[] getJogosAdicionados() {   // Retorna o vetor de jogos.
        return this.jogosAdicionados;
    }

    public int getEscolhaJogo() {       // Retorna a escolha do jogo (1-Jogo General ou 2-Jogo de Azar).
        return this.escolhaJogo;
    }

    public float getSaldo(){            // Retorna o saldo atual do jogador.
        return this.saldo;
    }

    public boolean getEstaCheio(){      // Retorna se o jogador já atingiu ou não o limite de jogos.
        return this.estaCheio;
    }

    public float getApostas(int i) {    // Retorna o vetor que guarga todos os valores de apostas do jogador.
        return apostas[i];
    }

    // Métodos setters:

    public void setNome(String nome) {      // Altera o nome.
        this.nome = nome;
    }

    public void setTipo(char tipo) {        // Altera o tipo.
        this.tipo = tipo;
    }

    public void setEscolhaJogo(int escolhaJogo) {       // Altera a escolha de jogo.
        this.escolhaJogo = escolhaJogo;
    }

    public void setValorDaAposta(float aposta){         // Altera o valor da aposta atual.
        this.valorDaAposta = aposta;
    }
    
    public void setApostas(int i, float aposta){        // Adiciona um novo valor de aposta ao vetor de apostas realizadas.
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

    // Este método cria, para um jogador, uma nova instância de Jogo General:
    public JogoGeneral inicializarJogoG(){
        JogoGeneral jogo = new JogoGeneral();
        this.adicionarJogoNoVetor(jogo);
        return jogo;
    }

    // Este método cria, para um jogador, uma nova instância de Jogo de Azar:
    public JogoAzar inicializarJogoA(){
        JogoAzar jogo = new JogoAzar();
        this.adicionarJogoNoVetor(jogo);
        return jogo;
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
    public void adicionarJogoNoVetor(JogoDados jogoAtual){   // Como o jogoAtual vai ser ou JogoAzar ou JogoGeneral, aqui ocorre polimorfismo.       
        int i = getIndiceLivre();

        if (i >= 0 && i < 10 && this.jogosAdicionados[i] == null) {
            this.jogosAdicionados[i] = jogoAtual;
        }
    }

    // Retorna pontuação de uma jogada específica para as tabelas intermediárias:
    public String cartela(JogoGeneral jogo, int i){ 
        String s = new String();
        s = jogo.montarTabela(i);
        return s;
    }

    // Mostra a tabela intermediária do Jogo General que está sendo executado:
    public String mostraJogadasExecutadas(JogoGeneral jogo){
        String s = new String();

        s = "1\t2\t3\t4\t5\t6\t7(T)\t8(Q)\t9(F)\t10(S+)\t11(S-)\t12(G)\t13(X)\n";

        for(int i = 1; i <= 13; i++){
            s = s + this.cartela(jogo, i);
        }
        return s;
    }

    public void jogada(JogoGeneral jogo){ // Efetua uma jogada, rolando os dados e imprimindo.
        jogo.rolarDados();
        jogo.imprimirResultado();
    }

    public boolean resultado(JogoGeneral jogoG){ // Retorna o resultado do Jogo General ('true' para vitória, 'false' para derota).
        return jogoG.calculaResultado();
    }

    // Verifica se ainda é possível adicionar algum jogo para o jogador. Retorna 'true' se pode e 'false' se não.
    // Se não houver mais espaços livres para os jogos, definirá que o jogador está cheio / não pode jogar mais.
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

    // Todas as suas subclasses deverão ter um método que retorne um valor float de aposta:
    public abstract float apostar();

}