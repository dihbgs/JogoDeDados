import java.util.Random;

public class Maquina extends Jogador implements JogarComoMaquina{
    public Maquina(String nome, char tipo){
        super(nome, tipo);
    }

    public int escolherJogo(){
        Random random = new Random();
        int escolha = random.nextInt(2) + 1;

        System.out.println("\nO jogo escolhido pela máquina " + super.getNome() + " foi o ");
        if(escolha == 1){
            System.out.println("Jogo General. ");
        }
        else if(escolha == 2){
            System.out.println("Jogo de Azar. ");
        }

        return escolha;
    }

    public int aplicarEstrategia(JogoGeneral jogoG){
        System.out.println(super.getNome() + ", é a sua vez.\nRolando os dados... ");
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
            System.out.println("Essa jogada gera o seguinte numero de pontos: " + melhorPontuacao);
            jogoG.setJogada(melhorJogada, melhorPontuacao);
        }

        return melhorPontuacao;
    }

    public boolean executarJogoDeAzar(JogoAzar jogoA){
        return jogoA.executarRegrasJogo();
    }

    public float apostar(){
        Random random = new Random();
        float aposta = random.nextFloat(super.getSaldo());
        String apostaCom2Caracteres = String.format("%.02f", aposta);
        System.out.println("\nA máquina " + super.getNome() + " apostou R$" + apostaCom2Caracteres + " nesta rodada. ");
        super.setApostas(getIndiceLivreExtrato(), aposta);

        return aposta;
    }

}