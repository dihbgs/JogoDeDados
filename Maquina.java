import java.util.Random;

// A classe Humano herda da classe Jogador, ou seja, possui todos os seus membros (campos e métodos).
public class Maquina extends Jogador{
    public Maquina(String nome, char tipo){
        super(nome, tipo);
    }

    // Este método sorteia um número alatório entre 1 e 2 para definir a escolha de jogo de uma máquina:
    public int escolherJogo(){
        Random random = new Random();
        int escolha = random.nextInt(2) + 1;

        System.out.print("\nO jogo escolhido pela máquina " + super.getNome() + " foi o ");
        if(escolha == 1){
            System.out.println("Jogo General. ");
        }
        else if(escolha == 2){
            System.out.println("Jogo de Azar. ");
        }

        return escolha;
    }

    // Este método sorteará um valor random entre 0.1 e o saldo do jogador máquina e o definirá como sua aposta:
    public float apostar(){
        if(super.getSaldo() > 0){
            Random random = new Random();
            float aposta = random.nextFloat(super.getSaldo()) + (float)0.1;
            System.out.println("A máquina " + super.getNome() + " apostou R$" + String.format("%.02f", aposta) + " nesta rodada. ");
            super.setApostas(getIndiceLivre(), aposta); // Apesar de o método getIndiceLivre() estar definido para encontrar o
                                                        // próximo índice livre no vetor de jogos, como as apostas são definidas
                                                        // para cada um deles, também encontra o próximo índice livre das apostas.

            return aposta;
        }

        return 0;
    }

    // Implementação do método aplicarEstrategia(), definido na interface JogarComoMaquina.
    // Para o Jogo General, a máquina escolherá a jogada de maior pontuação da rodada e retornará esse valor:
    public int aplicarEstrategia(JogoGeneral jogoG){
        System.out.println("\n" + super.getNome() + ", é a sua vez.\nRolando os dados... ");
        super.jogada(jogoG);
        int melhorPontuacao = 0;
        
        // Verifica se o jogador eh maquina:
        if(super.getTipo() == 'M' || super.getTipo() == 'm'){
            int melhorJogada = -1;
            
            // Percorre jogadas para determinar qual a melhor(que rende maior pontuacao):
            for (int choice = 1; choice <= 13; choice++) {
                if (jogoG.validarJogada(choice)) {
                    int pontuacao = jogoG.pontuarJogada(choice);
                    if (pontuacao > melhorPontuacao) {
                        melhorPontuacao = pontuacao;
                        melhorJogada = choice;
                    }
                }
            }

            // Caso todas as pontuacoes forem 0, escolhe uma aleatoria para zerar:
            if(melhorJogada == -1){
                Random random = new Random();
                melhorJogada = random.nextInt(13) + 1;
                while(!jogoG.validarJogada(melhorJogada)){
                    melhorJogada = random.nextInt(13) + 1;
                }
                melhorPontuacao = 0;
            }
            
            System.out.println(mostraJogadasExecutadas(jogoG));
            System.out.println("Essa jogada (" + melhorJogada+ ") gera o seguinte numero de pontos: " + melhorPontuacao);
            jogoG.setJogada(melhorJogada, melhorPontuacao);
        }

        return melhorPontuacao;
    }

    // Este método retorna 'true' no caso de vitória do jogador no Jogo de Azar ou 'false' no caso de derrota: 
    public boolean executarJogoDeAzar(JogoAzar jogoA){
        return jogoA.executarRegrasJogo();
    }
}