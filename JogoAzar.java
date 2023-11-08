public class JogoAzar{
    private float valorAposta;

    public JogoAzar(){
        this.valorAposta = 1;
    }

    public JogoAzar(float aposta){
        this.valorAposta = aposta;
    }

    public float executarRegrasJogo(float a){
        this.valorAposta = a;
        Dado dado1 = new Dado();
        dado1.roll();
        System.out.println("Valor do primeiro dado: " + dado1.getFaceSuperior());

        Dado dado2 = new Dado();
        dado2.roll();
        System.out.println("Valor do segundo dado: " + dado2.getFaceSuperior());

        int somaFaces = dado1.getFaceSuperior() + dado2.getFaceSuperior();
        System.out.println("Valor total do lançamento: " + somaFaces);

        if(somaFaces == 7 || somaFaces == 11){
            System.out.println("O jogador ganhou a aposta! ");
            return this.valorAposta;
        }
        else if(somaFaces == 2 || somaFaces == 3 || somaFaces == 11){
            System.out.println("Que pena...O jogador perdeu a aposta. ");
            return -this.valorAposta;
        }
        else{
            System.out.println("Valor a ser encontrado: " + somaFaces);

            for(int i = 0; i < 3; i++){
                dado1.roll();
                dado2.roll();
                System.out.println("Valores dos dados do novo lançamento(" + (i + 2) + "): " + dado1.getFaceSuperior() + " e " + dado2.getFaceSuperior());

                int total = dado1.getFaceSuperior() + dado2.getFaceSuperior();
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