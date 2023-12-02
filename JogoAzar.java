public class JogoAzar extends JogoDados{
    private boolean resultado;

    public boolean getResultado(){
        return this.resultado;
    }

    // Método construtor da classe JogoAzar, inicializando sua superclasse JogoDados:
    public JogoAzar(){
        super(2);
    }

    // Método que efetivamente faz o Jogo de Azar ser executado, retornando 'true' no caso de uma vitória e 'false' no de uma derrota.
    public boolean executarRegrasJogo(){
        Dado[] dadosJogoAzar = getDados();   // O método getDados() está na superclasse e é herdado por JogoAzar.
    
        // Rolam-se os dados:
        dadosJogoAzar[0].roll();
        dadosJogoAzar[1].roll();

        // Gravam-se as faces sorteadas para futuro cálculo de estatística:
        somarFacesSorteadas(dadosJogoAzar[0].getFaceSuperior());
        somarFacesSorteadas(dadosJogoAzar[1].getFaceSuperior());
        
        // Mostra-se para o usuário os valores dos dados:
        imprimirResultado();
    
        // Calcula-se e mostra-se para o usuário a soma das faces sorteadas:
        int somaFaces = dadosJogoAzar[0].getFaceSuperior() + dadosJogoAzar[1].getFaceSuperior(); 
        System.out.println("Valor total do lançamento: " + somaFaces);
    
        // Verifica-se a vitória ou a derrota do jogador. Se a soma das faces for 7 ou 11, o jogador vence. Se for
        // 2, 3 ou 12, perde automaticamente. Caso contrário, ele pode rolar os dados até 3 vezes mais, tentando 
        // encontrar a soma do sorteio inicial. Se achar, ganha. Se não achar, perde.
        if(somaFaces == 7 || somaFaces == 11){
            System.out.println("O jogador ganhou a aposta! ");
            resultado = true;
            return true;
        }
        else if(somaFaces == 2 || somaFaces == 3 || somaFaces == 12){ 
            System.out.println("Que pena... O jogador perdeu a aposta. ");
            resultado = false;
            return false;
        }
        else{
            System.out.println("Valor a ser encontrado: " + somaFaces);
    
            for(int i = 0; i < 3; i++){
                dadosJogoAzar[0].roll();
                dadosJogoAzar[1].roll(); 
                somarFacesSorteadas(dadosJogoAzar[0].getFaceSuperior());
                somarFacesSorteadas(dadosJogoAzar[1].getFaceSuperior());
                System.out.println("Valores dos dados do novo lançamento(" + (i + 2) + "): " + dadosJogoAzar[0].getFaceSuperior() + " e " + dadosJogoAzar[1].getFaceSuperior());
    
                int total = dadosJogoAzar[0].getFaceSuperior() + dadosJogoAzar[1].getFaceSuperior();
                System.out.println("Valor total do novo lançamento: " + total);
    
                if(total == somaFaces){
                    System.out.println("Parabéns! O jogador ganhou! ");
                    resultado = true;
                    return true;
                }
            }
    
            System.out.println("Infelizmente, o jogador perdeu. ");
            resultado = false;
            return false;
        }
    }

    // O seguinte método sobrescreve 'imprimirResultado()', que é um método herdado da superclasse JogoDados, para adequar-se
    // às especificações que JogoAzar requer:
    @Override
    public void imprimirResultado(){
        int i = 1;

        for(Dado dado : getDados()){
            System.out.println("Valor do dado " + i + ": " + dado.getFaceSuperior());
            i++;
        }
    }
}