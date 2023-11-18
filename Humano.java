import java.util.Random;
import java.util.Scanner;

public class Humano extends Jogador implements JogarComoHumano{
    private int cpf;
    private String agencia = new String();
    private String conta = new String();
    private int numeroBanco;
    private double DinheiroDisponivel;

    public Humano(String nome, char tipo, int cpf, String ag, String conta, int banco){
        super(nome, tipo);
        this.cpf = cpf;
        this.agencia = ag;
        this.conta = conta;
        this.numeroBanco = banco;
        this.DinheiroDisponivel = 100;
    }

    public void setCpf(int cpf) {
        this.cpf = cpf;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public void setNumeroBanco(int numeroBanco) {
        this.numeroBanco = numeroBanco;
    }

    public String getConta() {
        return conta;
    }

    public int escolherJogo(){
        Scanner tec = new Scanner(System.in);
        int escolha = 0;

        System.out.println("Escolha um jogo para fazer sua aposta(1 para o Jogo General ou 2 para o Jogo de Azar): ");
        do{
            escolha = tec.nextInt();

            if(escolha != 1 || escolha != 2){
                System.out.println("Escolha inválida. Por favor escolha ou o Jogo General(1) ou o Jogo de Azar(2). \nInforme sua escolha: ");
            }
        }while(escolha != 1 || escolha != 2);

        return escolha;
    }

    public void escolherJogada(JogoGeneral jogo){
        Scanner teclado = new Scanner(System.in);
        char confirma;
        Random random = new Random();

        System.out.println("\n" + super.getNome() + ", é a sua vez.\nRolando os dados... ");
        this.jogada();
        System.out.println(mostraJogadasExecutadas());
        
            int guia = 0; 
            int escolha = 0;
            int rolou = 0; // Variavel que marca se o jogador ja rolou os dados uma segunda vez.

            while(guia == 0){ // O guia grava se a jogada foi ou nao confirmada.
                do{ 
                    if(rolou == 0){
                        System.out.println("Escolha uma jogada de 1 a 13 (digite 0 para pular a vez ou 14 para rolar os dados novamente): ");
                        escolha = teclado.nextInt();
                    }
                    else if(rolou == 1){
                        System.out.println("Escolha uma jogada de 1 a 13 (digite 0 para pular a vez): ");
                        escolha = teclado.nextInt();
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
                    } while (!this.validar(jogadaAleatoria));
                
                    System.out.println("Você pulou a vez. Sua jogada aleatória zerada foi: " + jogadaAleatoria);
                    jogo.setJogada(jogadaAleatoria, 0); // Escolhe-se uma jogada aleatoria e atribui zero a ela.
                    guia = 1;
                }
                // Se o jogador escolher rolar os dados novamente,
                // ele ganha uma unica chance de fazer isso:
                else if(escolha == 14 && rolou == 0){ 
                    this.jogada();
                    System.out.println(mostraJogadasExecutadas());
                    rolou = 1;
                }
                else if(escolha == 14 && rolou == 1){
                    System.out.println("Os dados ja rolaram uma segunda vez. Escolha uma jogada entre 1 e 13 ou pule a vez. ");
                }
                else{
                    // Verificar se a jogada é válida:
                    if (validar(escolha)) {
                        // Calcular a pontuação da jogada:
                        int pontuacao = jogo.pontuarJogada(escolha);

                        System.out.println("Essa jogada gera o seguinte numero de pontos: " + pontuacao);
                        // O jogador deve decidir se quer validar a jogada:
                        do{
                            System.out.println("Deseja confirma-la(S/N)? ");
                            confirma = teclado.next().charAt(0);
                            teclado.nextLine();
                        }while(confirma != 's' && confirma != 'S' && confirma != 'n' && confirma != 'N');

                        // Se o jogador confirmar, a escolha sera validada e a ponntuacao, gravada:
                        if(confirma == 'S' || confirma == 's'){
                            jogo.setJogada(escolha, pontuacao);
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
}