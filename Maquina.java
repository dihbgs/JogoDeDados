import java.util.Random;

public class Maquina extends Jogador implements JogarComoMaquina{
    public Maquina(String nome, char tipo){
        super(nome, tipo);
    }

    public int aplicarEstrategia(){
        int melhorPontuacao = 0;
        
        // Verifica se o jogador eh maquina:
        if(super.getTipo() == 'M' || super.getTipo() == 'm'){
            int melhorJogada = -1;
            
            // Percorre jogadas para determinar qual a melhor(que rende maior pontuacao):
            for (int choice = 1; choice <= 13; choice++) {
                if (validar(choice)) {
                    int pontuacao = jogo.pontuarJogada(choice);
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
                while(!validar(melhorJogada)){
                    melhorJogada = random.nextInt(13) + 1;
                }
                melhorPontuacao = 0;
            }
                        
            System.out.println("Essa jogada gera o seguinte numero de pontos: " + melhorPontuacao);
            jogo.setJogada(melhorJogada, melhorPontuacao);
        }

        return melhorPontuacao;
    }
}