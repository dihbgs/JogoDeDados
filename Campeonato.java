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
    public void incluirJogadorHumano(String nome, char tipo, String cpf, int banco, String agencia, String conta, int i){
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
            for(int i = 0; i < 10; i++){
                if(players[i] != null){
                    if((players[i].getTipo() == 'h' || players[i].getTipo() == 'H') && players[i].getSaldo() > 0){
                        Humano h = (Humano)players[i];
                        players[i].setEscolhaJogo(h.escolherJogo());
                        players[i].setValorDaAposta(h.apostar());
                    }
                    else if((players[i].getTipo() == 'm' || players[i].getTipo() == 'M') && players[i].getSaldo() > 0){
                        Maquina m = (Maquina)players[i];
                        players[i].setEscolhaJogo(m.escolherJogo());
                        players[i].setValorDaAposta(m.apostar());
                    }
                    else if(players[i].getSaldo() <= 0){
                        players[i].setValorDaAposta(0);
                    }

                    if(players[i].getEscolhaJogo() == 1)
                        contJogoG++;
                    else
                        contJogoA++;
                }
            }

            //todos os jogadores de jogo general, se houverem, jogam
            if(contJogoG != 0){
                System.out.println("Jogadores de Jogo General, se preparem...");
                int jaAdicionou = 0;

                // Como sao 13 as jogadas permitidas, cada partida tera 13 rodadas:
                for (int rodada = 1; rodada <= 13; rodada++) {
                    System.out.println("\n-.-.-.-.-.-.-.-.-.-.-.-.-.-\nRodada " + rodada + "\n-.-.-.-.-.-.-.-.-.-.-.-.-.-");
                    
                    // Loop para permitir que cada jogador realize sua jogada:
                    for (Jogador jogador : players) {
                        if(jogador != null){
                            if(jogador.getEscolhaJogo() == 1 && jogador.getSaldo() > 0){          
                                if(jogador instanceof Humano){
                                    JogoGeneral jogoG;
                                    if(jaAdicionou == 0){
                                        jogoG = inicializarJogoG(jogador);
                                    }
                                    else{
                                        jogoG = (JogoGeneral)jogador.getJogoAtual();
                                    }

                                    Humano h = (Humano)jogador;
                                    h.escolherJogada(jogoG);
                                }
                                else if(jogador instanceof Maquina){
                                    JogoGeneral jogoG;
                                    if(jaAdicionou == 0){
                                        jogoG = inicializarJogoG(jogador);
                                    }
                                    else{
                                        jogoG = (JogoGeneral)jogador.getJogoAtual();
                                    }

                                    Maquina m = (Maquina) jogador;
                                    m.aplicarEstrategia(jogoG);
                                }
                            }    
                        }     
                    }
                    jaAdicionou = 1;
                }
                
                System.out.println("\nOs vencedores do Jogo General foram: ");
                for (int k = 0; k < 10; k++){
                    if(this.players[k] != null){
                        if(players[k].getEscolhaJogo() == 1 && players[k].getSaldo() > 0){
                            if(players[k].resultado((JogoGeneral)players[k].getJogoAtual()) == true){
                                System.out.println(players[k].getNome());
                                players[k].atualizarSaldo(true);
                            }
                            else{
                                players[k].atualizarSaldo(false);
                            }
                        }
                    }
                }
            }
                        
            // Todos os jogadores de jogo de azar, se houverem, jogam
            if(contJogoA != 0){
                System.out.println("\nJogadores do Jogo de Azar se preparem...");

                for(int i = 0; i < 10; i++){
                    if(players[i] != null && players[i].getSaldo() > 0){
                        if(players[i].getEscolhaJogo() == 2){
                            System.out.println("\nÉ a vez de " + players[i].getNome() + ":");

                            if(players[i] instanceof Humano){
                                Humano h = (Humano)players[i];
                                JogoAzar jogoA = inicializarJogoA(h);
                                boolean resultadoH = h.executarJogoDeAzar(jogoA);
                                players[i].atualizarSaldo(resultadoH);
                            }
                            else if(players[i] instanceof Maquina){
                                Maquina m = (Maquina) players[i];
                                JogoAzar jogoA = inicializarJogoA(m);
                                boolean resultadoM = m.executarJogoDeAzar(jogoA);
                                players[i].atualizarSaldo(resultadoM);
                            }                
                        }
                    }
                }
            }      
        }
    }

    public JogoGeneral inicializarJogoG(Jogador j){
        JogoGeneral jogo = new JogoGeneral();
        j.adicionarJogoNoVetor(jogo);
        return jogo;
    }

    public JogoAzar inicializarJogoA(Jogador j){
        JogoAzar jogo = new JogoAzar();
        j.adicionarJogoNoVetor(jogo);
        return jogo;
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
            //mostrarCartela(); // A cartela imprimira todas os jogadores, jogadas e pontuacoes da partida salva.
		}catch (Exception ex) {
			System.err.println("erro: " + ex.toString());
		}
    }
    
    public boolean aindaHaJogosASeremExecutados(){
        for(Jogador jogador: players){
            if(jogador != null){
                boolean verificador = jogador.verificaJogosLivres();

                if(verificador == false){
                    return false;
                }
            }
        }
        return true;
    }

    public void mostrarSaldos(){
        System.out.println("=== SALDOS DOS JOGADORES ===");

        for(Jogador jogador : players){
            if(jogador != null){
                System.out.println(jogador.getNome() + ": " + jogador.getSaldo());
            }
        }
    }

    public void mostrarExtratos(){
        for(Jogador jogador : players){
            if(jogador != null){
                System.out.println("\n=== Extrato de " + jogador.getNome() + " ===");
                jogador.imprimirHistoricoDeApostas();
            }
        }
    }

    public void mostrarEstatisticas(){
        
    }
}