public class JogoGeneral extends JogoDados{
    private int[] valoresJogadas = new int[13];

    public JogoGeneral(){
        super(5, "Jogo General");
        
        for(int i = 0 ; i < 13 ; i++) { 
			valoresJogadas[i] = -1;
		}
    }
}