package src.utils;
import java.util.Random;
import java.util.Scanner;

// A classe Humano herda da classe Jogador, ou seja, possui todos os seus membros (campos e métodos).
public class Humano extends Jogador implements JogarComoHumano{
    private Scanner teclado;
    private String cpf;

    public Humano(Scanner teclado){    // Construtor que atribui valores padrões para o jogador humano.
        this("Anônimo", 'h', "12345678910", teclado);
    }

    public Humano(String nome, char tipo, String cpf, Scanner teclado){  // Construtor que atribui valores passados pelos parâmetros para o humano.
        super(nome, tipo);
        this.teclado = teclado;
        this.cpf = cpf;
    }

    // Método setter que altera o cpf do jogador humano:
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    // Método getter que retorna o cpf do jogador humano:
    public String getCpf(){
        return this.cpf;
    }

    // Implementação do método escolherJogo(), que está declarado na interface JogarComoHumano. 
    // Ele retornará o jogo escolhido pelo jogador, sendo 1 para o Jogo General e 2 para o Jogo de Azar:
    public int escolherJogo(){
        int escolha = 0;
        int input = 0;

        System.out.println("\n" + super.getNome() + ", escolha um jogo para fazer sua aposta(1 para o Jogo General ou 2 para o Jogo de Azar): ");
        do{
            do{
                try {
                    escolha = teclado.nextInt();
                    input = 1;
                } catch (java.util.InputMismatchException e) {
                    System.out.println("Erro: Entrada inválida. Por favor, digite um número inteiro.");
                    teclado.nextLine();
                    input = 0;
                }
            }while(input == 0);

            if(escolha != 1 && escolha != 2){
                System.out.println("Escolha inválida. Por favor escolha ou o Jogo General(1) ou o Jogo de Azar(2). \nInforme sua nova escolha: ");
            }
        }while(escolha != 1 && escolha != 2);

        return escolha;
    }

    // Este método pede ao jogador a quantidade de dinheiro que ele deseja apostar em uma rodada entre 0 e seu saldo
    // e retorna o valor escolhido.
    public float apostar(){
        float aposta = 0.0f;

        if(super.getSaldo() > 0){
            System.out.println("Insira o valor você gostaria de apostar nesta rodada do jogo escolhido (máximo de R$" + super.getSaldo() +  "): ");
            int input = 0;

            do{
                try {
                    aposta = teclado.nextFloat(); 
                    input = 1;
                } catch (java.util.InputMismatchException e) {
                    System.out.println("Erro: Entrada inválida. Por favor, digite um número inteiro ou decimal. ");
                    teclado.nextLine();
                    input = 0;
                }
        
                if(aposta  <= 0 || aposta > super.getSaldo()){
                    System.out.println("Aposta inválida. Por favor, digite um número inteiro ou decimal entre 0 e o seu saldo.");
                    input = 0;
                }
            }while(input == 0 || (aposta  <= 0 || aposta > super.getSaldo()));
        }

        super.setApostas(getIndiceLivre(), aposta);    // Apesar de o método getIndiceLivre() estar definido para encontrar o
                                                       // próximo índice livre no vetor de jogos, como as apostas são definidas
                                                       // para cada um deles, também encontra o próximo índice livre das apostas.

        return aposta;
    }

    // Implementação do método escolherJogada(), definido na interface JogarComoHumano.
    // Vai mostrar as jogadas disponíveis do jogo general e pedir ao jogador humano o 
    // que ele quer fazer (pular a vez, escolher uma jogada, ou rolar os dados de novo):
    public void escolherJogada(JogoGeneral jogoG){
        char confirma;
        Random random = new Random();

        System.out.println("\n" + super.getNome() + ", é a sua vez.\nRolando os dados... ");
        this.jogada(jogoG);
        System.out.println(mostraJogadasExecutadas(jogoG));
        
        int guia = 0; 
        int escolha = 0;
        int rolou = 0; // Variavel que marca se o jogador ja rolou os dados uma segunda vez.
        int input = 0;

        while(guia == 0){ // O guia grava se a jogada foi ou nao confirmada.
            do{ 
                if(rolou == 0){                        
                    do{
                        try {
                            System.out.println(super.getNome() + ", escolha uma jogada de 1 a 13 (digite 0 para pular a vez ou 14 para rolar os dados novamente): ");
                            escolha = teclado.nextInt();
                            input = 1;
                        } catch (java.util.InputMismatchException e) {
                            System.out.println("Erro: Entrada inválida. Por favor, digite um número inteiro.");
                            teclado.nextLine();
                            input = 0;
                    }
                    }while(input == 0);
                }
                else if(rolou == 1){
                    try {
                        System.out.println("Escolha uma jogada de 1 a 13 (digite 0 para pular a vez): ");
                        escolha = teclado.nextInt();
                        input = 1;
                    } catch (java.util.InputMismatchException e) {
                        System.out.println("Erro: Entrada inválida. Por favor, digite um número inteiro.");
                        teclado.nextLine();
                        input = 0;
                    }
                }
                    
                if(escolha < 0 || escolha > 14){
                    if(rolou == 0){
                        System.out.println("Favor, informar um numero entre 0 e 14. ");
                    }

                    if(rolou == 1){
                        System.out.println("Favor, informar um numero entre 0 e 13. ");
                    }
                }    
            }while(escolha < 0 || escolha > 14); // Verifica se eh um numero valido.
            
            if (escolha == 0) { // Caso pule a vez:
                int jogadaAleatoria;

                do {
                    jogadaAleatoria = random.nextInt(13) + 1;
                } while (!jogoG.validarJogada(jogadaAleatoria));
                
                System.out.println("Você pulou a vez. Sua jogada aleatória zerada foi: " + jogadaAleatoria);
                jogoG.setJogada(jogadaAleatoria, 0); // Escolhe-se uma jogada aleatoria e atribui zero a ela.
                guia = 1;
            }
            // Se o jogador escolher rolar os dados novamente,
            // ele ganha uma unica chance de fazer isso:
            else if(escolha == 14 && rolou == 0){ 
                this.jogada(jogoG);
                System.out.println(mostraJogadasExecutadas(jogoG));
                rolou = 1;
            }
            else if(escolha == 14 && rolou == 1){
                System.out.println("Os dados ja rolaram uma segunda vez. Escolha uma jogada entre 1 e 13 ou pule a vez. ");
            }
            else{
                // Verificar se a jogada é válida:
                if (jogoG.validarJogada(escolha)) {
                    // Calcular a pontuação da jogada:
                    int pontuacao = jogoG.pontuarJogada(escolha);

                    System.out.println("Essa jogada gera o seguinte numero de pontos: " + pontuacao);
                    // O jogador deve decidir se quer validar a jogada:
                    do{
                        System.out.println("Deseja confirma-la(S/N)? ");
                        confirma = teclado.next().charAt(0);
                        teclado.nextLine();
                    }while(confirma != 's' && confirma != 'S' && confirma != 'n' && confirma != 'N');

                    // Se o jogador confirmar, a escolha sera validada e a ponntuacao, gravada:
                    if(confirma == 'S' || confirma == 's'){
                        jogoG.setJogada(escolha, pontuacao);
                        guia = 1;
                    }
                    // Se a jogada for negada, outra sera pedida.
                    else if(confirma == 'N' || confirma == 'n'){
                       System.out.print("Ok. ");
                        teclado.nextLine();
                    }
                } 
                // Se a jogada estiver indisponivel, pede-se para informar outra:
                else {
                    System.out.println("Jogada inválida. Escolha outra jogada.");
                }
            }
        }
    } 
    
    // Este método retorna 'true' no caso de vitória do jogador no Jogo de Azar ou 'false' no caso de derrota:
    public boolean executarJogoDeAzar(JogoAzar jogoA){
        return jogoA.executarRegrasJogo();
    }
}