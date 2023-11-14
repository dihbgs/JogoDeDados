public class JogoAzar extends JogoDados{
    private float valorAposta;

    public JogoAzar(){
        super(2, "Jogo de Azar");
        this.valorAposta = 1;
    }

    public JogoAzar(float aposta){
        super(2, "Jogo de Azar");
        this.valorAposta = aposta;
    }

    public float executarRegrasJogo(float a){
        this.valorAposta = a;
        Dado[] dadosJogoAzar = super.getDados();
    
        dadosJogoAzar[0].roll();
        System.out.println("Valor do primeiro dado: " + dadosJogoAzar[0].getFaceSuperior());
    
        dadosJogoAzar[1].roll();
        System.out.println("Valor do segundo dado: " + dadosJogoAzar[1].getFaceSuperior());
    
        int somaFaces = dadosJogoAzar[0].getFaceSuperior() + dadosJogoAzar[1].getFaceSuperior(); 
        System.out.println("Valor total do lançamento: " + somaFaces);
    
        if(somaFaces == 7 || somaFaces == 11){
            System.out.println("O jogador ganhou a aposta! ");
            return this.valorAposta;
        }
        else if(somaFaces == 2 || somaFaces == 3 || somaFaces == 12){ 
            System.out.println("Que pena... O jogador perdeu a aposta. ");
            return -this.valorAposta;
        }
        else{
            System.out.println("Valor a ser encontrado: " + somaFaces);
    
            for(int i = 0; i < 3; i++){
                dadosJogoAzar[0].roll();
                dadosJogoAzar[1].roll(); 
                System.out.println("Valores dos dados do novo lançamento(" + (i + 2) + "): " + dadosJogoAzar[0].getFaceSuperior() + " e " + dadosJogoAzar[1].getFaceSuperior());
    
                int total = dadosJogoAzar[0].getFaceSuperior() + dadosJogoAzar[1].getFaceSuperior();
                System.out.println("Valor total do novo lançamento: " + total);
    
                if(total == somaFaces){
                    System.out.println("Parabéns! O jogador ganhou! ");
                    return this.valorAposta;
                }
            }
    
            System.out.println("Infelizmente, o jogador perdeu. ");
            return -this.valorAposta;
        }
    }
    
}