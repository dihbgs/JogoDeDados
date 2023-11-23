import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Campeonato implements Serializable{
    private Jogador[] players;

    public Campeonato(){
        players = new Jogador[10];
    }

    // Retorna o tamanho do vetor players(a quantidade de jogadores que eh permitido adicionar):
    public int getLength(){
        return players.length;
    }

    // Acha um indice livre para adicionar um jogador novo: 
    public int jogadorLivre() {
        int i = 0;

        while (i < players.length && players[i] != null && players[i].getNome() != null) {
            i++;
        }

        return i;
    }

    public int nomeLivre(String nome){
        int i = 0;

        while (i < players.length && players[i] != null && players[i].getNome() != null) {
            if (players[i].getNome().equalsIgnoreCase(nome)) {
                return -1; // Retorna -1 se o nome já existir
            }
            i++;
        }

        return 1; // Caso contrário, retorna 1.
    }

    // Verifica se ha jogadores registrados ou nao:
    public boolean jogadorVazio(){
        boolean x = true;

        for(int i = 0; i < players.length; i++){
            if(players[i] != null){
                if(players[i].getNome() != null){
                    x = false;
                }
            }
        }
        
        return x;
    }

    // Inclui jogadores pelo indice encontrado por jogadorLivre():
    public void incluirJogadorHumano(String nome, char tipo, int cpf, int banco, String agencia, String conta, int i){
        // Se a posicao ja estiver livre, cria um novo jogador.
        if(players[i] == null && i < 10){
            players[i] = new Humano(nome, tipo, cpf, agencia, conta, banco);
        } 
        // Caso contrario, sobrescreve:
        else if((players[i] != null && i < players.length) || i==10){
            System.out.println("O número de jogadores máximo ja foi atingido!");
        }      
    }

    // Inclui jogadores pelo indice encontrado por jogadorLivre():
    public void incluirJogadorMaquina(String nome, char tipo, int i){
        // Se a posicao ja estiver livre, cria um novo jogador.
        if(players[i] == null && i < 10){
            players[i] = new Maquina(nome, tipo);
            players[i].setNome(nome);
            players[i].setTipo(tipo);
        } 
        // Caso contrario, sobrescreve:
        else if((players[i] != null && i < players.length)|| i==10){
            System.out.println("O número de jogadores máximo ja foi atingido!");
        }      
    }

    // Remove jogadores pelo nome:
    public boolean removerJogador(String nome){        
        // Percorre array de jogador, procurando pelo nome. Caso encontre, retorna true. Caso nao, retorna falso:
        if(this.players != null){
            for(int i = 0; i < 10; i++){
                if(this.players[i] != null){
                    if(this.players[i].getNome().equals(nome)){
                        this.players[i] = null;
                        return true;
                    }
                }
            }
        }

        return false;
    }

    // Mostra a lista de jogadores:
    public void mostrarJogadores(){
        int cont = 1;
        String s = new String();
        char tipo;

        if(jogadorVazio()){ // Verifica se tem jogadores registrados.
            System.out.println("\nNenhum jogador foi registrado\n");
        }
        else{
            System.out.println("\n-------- JOGADORES -------\n");
    
            s = "-";
            
            for(int i = 0; i < players.length; i++){ // Percorre o array de Jogadores e imprime os registrados.
                if(players[i]!=null){ 
                    tipo = players[i].getTipo();
                    if(tipo == 'h'){
                        s = "Humano";
                    }
                    if(tipo == 'm'){
                        s = "Maquina";
                    }
                    System.out.println(cont + " - " + players[i].getNome() + "\t" + s);
                    s = "-";
                    cont++;
                }
            }
            System.out.print("\n");;
        }
        
    }

    // Inicia a partida, marcando o jogo que cada jogador escolheu e então, executando as rodadas:
    public void iniciarCampeonato(){
        int maior = 0, tot = 0, maiorInd = 0;
        int contJogoG = 0, contJogoA = 0;

        // Se nenhum jogador tiver sido registrado, a partida nao sera iniciada:
        if(this.jogadorVazio()){
            System.out.println("Nenhum jogador foi registrado! ");
        }
        // Se tiver ao menos um jogador registrado, o jogo sera iniciado:
        else{
            //cada jogador escolhe seu jogo
            for(int i = 0; i<10; i++){
                if(players[i]!=null){
                    if(players[i].getTipo()=='h'){
                        Humano h = (Humano)players[i];
                        players[i].setEscolhaJogo(h.escolherJogo());
                    }
                    else{
                        Maquina m = (Maquina)players[i];
                        players[i].setEscolhaJogo(m.escolherJogo());
                    }

                    if(players[i].getEscolhaJogo() == 1)
                        contJogoG++;
                    else
                        contJogoA++;
                }
            }

            //todos os jogadores de jogo general, se houverem, jogam
            if(contJogoG!=0){
                System.out.println("Jogadores de Jogo General, se preparem...");

                for(int i = 0; i < 10; i++){
                    if(players[i] != null){     
                        if(players[i].getEscolhaJogo() == 1){
        
                            for(Jogador jog : players){
                                if(jog != null){
                                    jog.inicializarPartida(); // Todas as jogadas precisam ser anuladas para uma nova partida ser iniciada.
                                }
                            }
                
                            // Como sao 13 as jogadas permitidas, cada partida tera 13 rodadas:
                            for (int rodada = 1; rodada <= 13; rodada++) {
                                System.out.println("\n-.-.-.-.-.-.-.-.-.-.-.-.-.-\nRodada " + rodada + "\n-.-.-.-.-.-.-.-.-.-.-.-.-.-");
                
                                // Loop para permitir que cada jogador realize sua jogada:
                                for (Jogador jogador : players) {
                                    if(jogador != null){
                                        if(jogador instanceof Humano){
                                            Humano h = (Humano)jogador;
                                            h.escolherJogada();
                                        }
                                        else if(jogador instanceof Maquina){
                                            Maquina m = (Maquina) jogador;
                                            m.aplicarEstrategia();
                                        }
                                    }     
                                }
                            }
                            
                            // Para definir o ganhador, calcula-se qual jogador conseguiu o maior numero de pontos:
                            for (int k = 0; k < 10; k++){
                                if(this.players[k] != null){
                                    tot = this.players[k].total();
                                    if(tot > maior){
                                        maior = tot;
                                        maiorInd = k;
                                    }
                                }
                            }
                    
                            // Informa-se o vencedor:
                            System.out.println("\nQuem venceu foi " + this.players[maiorInd].getNome() + ", com " + maior + " pontos. ");
                        }
                    }
                }
            }

            // Todos os jogadores de jogo de azar, se houverem, jogam
            if(contJogoA!=0){
                System.out.println("Jogadores do Jogo de Azar se preparem...");

                for(int i = 0; i < 10; i++){
                    if(players[i] != null){
                        if(players[i].getEscolhaJogo() == 2){
                            if(players[i] instanceof Humano){
                                Humano h = (Humano)players[i];
                                h.executarJogoDeAzar();
                            }
                            else if(players[i] instanceof Maquina){
                                Maquina m = (Maquina) players[i];
                                m.executarJogoDeAzar();
                            }                
                        }
                    }
                }
            }
        }
    }

    // O seguinte metodo grava os dados de uma partida em arquivo:
    public void gravarEmArquivo(){
        // O nome do arquivo criado sera Campeonato.dat:
		File arquivo = new File("Campeonato.dat");

        try {
			FileOutputStream fout = new FileOutputStream(arquivo);
			ObjectOutputStream oos = new ObjectOutputStream(fout);

			oos.writeObject(players);
            oos.flush();
			oos.close();
			fout.close();
		}catch(Exception ex) {
			System.err.println("erro: " + ex.toString());
        }
    }

    // O seguinte metodo abre o arquivo Campeonato.dat e imprime os dados da partida gravada:
    public void lerDoArquivo(){
        File arquivo = new File("Campeonato.dat");

        try {
			FileInputStream fin = new FileInputStream(arquivo);
			ObjectInputStream oin = new ObjectInputStream(fin);

			players = (Jogador[])oin.readObject();
			oin.close();
			fin.close();
            mostrarCartela(); // A cartela imprimira todas os jogadores, jogadas e pontuacoes da partida salva.
		}catch (Exception ex) {
			System.err.println("erro: " + ex.toString());
		}
    }

    /* O seguinte metodo vai imprimir uma tabela cujos conteudos sao o nome dos jogadores,
       seus tipos, pontuacoes por rodada, e pontuacao total: */
    public void mostrarCartela(){
        String s = new String();

        System.out.print("---- Cartela de Resultados ----\n         |    \t");

        for(int i = 0; i < players.length; i++){
            if(players[i] != null){
                s = s + players[i].getNome() + "(" + players[i].getTipo() + ")\t|\t";
            }
        }
        System.out.println(s +"\n");
        
        for(int j = 1; j <= 13; j++){
            s = "";
            s = s + j;
            if(j == 7){
                s = s + "(T)  ";
            }
            else if(j == 8){
                s = s + "(Q)  ";
            }
            else if(j == 9){
                s = s + "(F)  ";
            }
            else if(j == 10){
                s = s + "(S+)";
            }
            else if(j == 11){
                s = s + "(S-)";
            }
            else if(j == 12){
                s = s + "(G) ";
            }
            else if(j == 13){
                s = s + "(X) ";
            }
            else{
                s = s + "     ";
            }

            s = s + "   |   \t";
            for(int i = 0; i < players.length; i++){
                if(players[i]!= null){
                    s += players[i].cartela(j);
                }
            }
            s = s + "\n";
            System.out.println(s);
        }
        s = "------------------------------------\n Total   |   \t";

        for(int i = 0; i < players.length; i++){
            if(players[i]!= null){
                s += players[i].total() + "\t|\t";
            }
        }
        
        System.out.println(s);
    }

    public void mostrarSaldos(){

    }

    public void mostrarExtratos(){
        
    }

    public void mostrarEstatisticas(){
        
    }
}