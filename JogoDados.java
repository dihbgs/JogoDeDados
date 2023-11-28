public abstract class JogoDados implements Estatistica{
    private int nDados;
    private String nomeJogo;
    private Dado[] dados;
    private int[] facesRoladas;

    public JogoDados(int nDados, String nomeJogo) {
        this.nDados = nDados;
        this.nomeJogo = nomeJogo;
        this.dados = new Dado[nDados];
        this.facesRoladas = new int[6];
        for(int i=0;i<6;i++){
            facesRoladas[i]=0;
        }

        for (int i = 0; i < nDados; i++) {
            this.dados[i] = new Dado();
        }
    }

    public void atualizarEstatisticaDeDados(int i){
        this.facesRoladas[i-1] = this.facesRoladas[i-1]+1;
    }

    public int[] getFacesRoladas() {
        return facesRoladas;
    }

    public Dado[] getDados(){
        return this.dados;
    }

    public int[] somarFacesSorteadas(Dado[] dados, int retorno){
        int[] soma = new int[6];
        if(retorno == 0){
            for (Dado dado : dados) {
                soma[dado.getFaceSuperior() - 1] = soma[dado.getFaceSuperior() - 1] + 1; 
            }
        }

        return soma;
    }

    public void imprimirEstatisticaDoCampeonato(){
        int[] soma = somarFacesSorteadas(dados, 1);
        int total = 0;

        for(int i = 0; i < 6; i++){
            total += soma[i];
        }

        if(total != 0){
            System.out.println("=== ESTATÍSTICA ===");
            for(int j = 0; j < 6; j++){
                System.out.println("A face " + (j + 1) + " apareceu virada para cima em " + soma[j]/total + "% das jogadas. ");
            }
        }
        else{
            System.out.println("Nenhuma dado foi sorteado. ");
        }
    }

    public void rolarDados() {
        for (Dado dado : dados) {
            dado.roll();
            atualizarEstatisticaDeDados(dado.getFaceSuperior());
        }

        int[] soma = somarFacesSorteadas(dados, 0);
    }

    public void imprimirResultado() {
        System.out.println("Resultado do " + nomeJogo + ":");

        for (Dado dado : dados) {
            System.out.print(dado.getFaceSuperior() + " ");
        }

        System.out.println();
    }
}
