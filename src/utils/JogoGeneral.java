package src.utils;
// A classe JogoGeneral herda da classe JogoDados, ou seja, possui todos os seus membros (campos e métodos).
public class JogoGeneral extends JogoDados{
    private int[] jogadas;

    // Método construtor da classe JogoAzar, inicializando sua superclasse JogoDados, bem como seus próprios atributos:
    public JogoGeneral(){
        super(5, "Jogo General");
        this.jogadas = new int[13]; 
        
        for(int i = 0 ; i < 13 ; i++) { 
			jogadas[i] = -1;
		}
    }

    public void setJogada(int i, int pontuacao){
        this.jogadas[i - 1] = pontuacao;    // Marca a pontuacao da jogada em sua posicao no vetor.
    }

    public void inicializarJogadas(){
        for(int j = 0; j < 13; j++){
            this.jogadas[j] = -1;   // Se o indice marca -1, significa que a jogada ainda nao foi realizada.
        }
    }

    public void imprimirResultado(){ // Imprime os valores dos dados.
        int i;
        String str = new String();
        Dado[] dadosJogoGeneral = super.getDados();

        System.out.println("Valores obtidos:");

        for(i = 0; i < 5; i++){
            str = str + dadosJogoGeneral[i].toString();

            if (i < 4){
                str = str + "-";
            }
            else{
                str = str + "\n";
            }
        }

        System.out.println(str);
    }

    // O seguinte método retorna 'false' para jogadas já executadas e 'true' para as disponiveis:
    public boolean validarJogada(int n){
        if(jogadas[n - 1] == -1){
            return true;
        }
        else{
            return false;
        }
    }

    // O seguinte metodo verifica qual jogada foi escolhida e retorna a pontuacao que ela rende:
    public int pontuarJogada(int n){
        int i, j;
        int[] armazenaValores = new int[6];
        Dado[] dadosJogoGeneral = super.getDados();

        for(i = 0; i < 5; i++){
            armazenaValores[dadosJogoGeneral[i].getFaceSuperior() - 1] += 1;
        }

        if(n == 1){
            return armazenaValores[0] * 1;
        }
        else if(n == 2){
            return armazenaValores[1] * 2;
        }
        else if(n == 3){
            return armazenaValores[2] * 3;
        }
        else if(n == 4){
            return armazenaValores[3] * 4;
        }
        else if(n == 5){
            return armazenaValores[4] * 5;
        }
        else if(n == 6){
            return armazenaValores[5] * 6;
        }
        else if(n == 7){
            int soma = 0;

            for(i = 0; i < 6; i++){
                if(armazenaValores[i] >= 3){
                    for(j = 0; j < 5; j++){
                        soma += dadosJogoGeneral[j].getFaceSuperior();
                    }
                    break;
                }
            }

            return soma;
        }
        else if(n == 8){
            int sum = 0;

            for(i = 0; i < 6; i++){
                if(armazenaValores[i] >= 4){
                    for(j = 0; j < 5; j++){
                        sum += dadosJogoGeneral[j].getFaceSuperior();
                    }
                    break;
                }
            }

            return sum;
            
        }
        else if(n == 9){
            for(i = 0; i < 6; i++){
                if(armazenaValores[i] == 3){
                    for(j = 0; j < 6; j++){
                        if(armazenaValores[j] == 2){
                            return 25;
                        }
                    }
                    break;
                }
            }

            return 0;
        }
        else if(n == 10){
            int cont = 0;

            for(i = 1; i < 6; i++){
                if(armazenaValores[i] == 1){
                    cont += 1;
                }
                else{
                    return 0;
                }
            }
            
            if(cont == 5){
                return 30;
            }
        }
        else if(n == 11){
            int c = 0;

            for(i = 0; i < 5; i++){
                if(armazenaValores[i] == 1){
                    c += 1;
                }
                else{
                    return 0;
                }
            }
            
            if(c == 5){
                return 40;
            }
        }
        else if(n == 12){
            for(i = 0; i < 6; i++){
                if(armazenaValores[i] == 5){
                    return 50;
                }
            }

            return 0;
        }
        else if(n == 13){
            int s = 0;

            for(j = 0; j < 5; j++){
                s += dadosJogoGeneral[j].getFaceSuperior();
            }
            return s;
        }

        return 0;
    }
   
    // O seguinte método calcula se o jogador venceu o Jogo General (pontuou nas 
    // outras jogadas mais que o dobro do que pontuou na jogada aleatória). Retorna
    // 'true' se sim, ou 'false' caso tenha perdido:
    public boolean calculaResultado(){
        int totalDe1a12 = 0;
        int i;

        for(i = 0; i < 12; i++){
            totalDe1a12 += this.jogadas[i];
        }

        if(totalDe1a12 > 2*this.jogadas[i]){
            return true;
        }

        return false;
    }

    // O seguinte metodo grava em string a pontuacao de uma jogada especifica:
    public String montarTabela(int i){
        String s = new String();

        if(jogadas[i - 1] == -1){
            s = s + "-\t";
        }
        else{
            s = s + jogadas[i - 1] + "\t";
        }

        return s;
    }
    
}