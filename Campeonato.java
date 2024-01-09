import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Scanner;

public class Campeonato implements Serializable{
    private Jogador[] players;
    private Scanner teclado;

    public Campeonato(Scanner teclado){
        players = new Jogador[10];
        this.teclado = teclado;
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

    // Este método verifica se um nome já foi utilizado ou não por algum jogador já registrado:
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

    // Verifica se há jogadores humanos:
    public boolean semJogadorHumano(){
        boolean x = true;

        for(int i = 0; i < players.length; i++){
            if(players[i] != null && players[i] instanceof Humano){
                if(players[i].getNome() != null){
                    x = false;
                }
            }
        }
        
        return x;
    }

    // Verifica se há jogadores máquinas:
    public boolean semJogadorMaquina(){
        boolean x = true;

        for(int i = 0; i < players.length; i++){
            if(players[i] != null && players[i] instanceof Maquina){
                if(players[i].getNome() != null){
                    x = false;
                }
            }
        }
        
        return x;
    }

    // Inclui jogadores humanos pelo índice encontrado por jogadorLivre():
    public void incluirJogadorHumano(String nome, char tipo, String cpf, int i){
        // Se a posição já estiver livre, cria um novo jogador.
        if(players[i] == null && i < 10){
            players[i] = new Humano(nome, tipo, cpf, teclado);   // Ocorrência de polimorfismo, onde Jogador é declarado Humano.
        } 
        // Caso contrario, sobrescreve:
        else if((players[i] != null && i < players.length) || i==10){
            System.out.println("O número de jogadores máximo ja foi atingido!");
        }      
    }

    // Inclui jogadores máquinas pelo índice encontrado por jogadorLivre():
    public void incluirJogadorMaquina(String nome, char tipo, int i){
        // Se a posição já estiver livre, cria um novo jogador.
        if(players[i] == null && i < 10){
            players[i] = new Maquina(nome, tipo);       // Ocorrência de polimorfismo, onde Jogador é declarado Máquina.
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

    // Este método verifica se algum jogador registrado ainda pode executar jogos:
    public boolean aindaHaJogosASeremExecutados(){
        for(Jogador jogador: players){
            if(jogador != null){
                boolean verificador = jogador.verificaJogosLivres();

                if(verificador == true){
                    return true;
                }
            }
        }
        return false;
    }

    // Inicia a partida, marcando o jogo que cada jogador escolheu e então, executando as rodadas:
    public void iniciarCampeonato(){
        int contJogoG = 0, contJogoA = 0;

        // Se nenhum jogador tiver sido registrado, a partida nao sera iniciada:
        if(this.jogadorVazio()){
            System.out.println("Nenhum jogador foi registrado! ");
        }
        // Se tiver ao menos um jogador registrado, o jogo sera iniciado:
        else{
            contJogoG = 0;
            contJogoA = 0;
            //cada jogador escolhe seu jogo
            for(int i = 0; i < 10; i++){
                if(players[i] != null){
                    if((players[i].getTipo() == 'h' || players[i].getTipo() == 'H') && players[i].getSaldo() > 0 && players[i].verificaJogosLivres()){
                        Humano h = (Humano)players[i];
                        players[i].setEscolhaJogo(h.escolherJogo());
                        players[i].setValorDaAposta(h.apostar());
                    }
                    else if((players[i].getTipo() == 'm' || players[i].getTipo() == 'M') && players[i].getSaldo() > 0 && players[i].verificaJogosLivres()){
                        Maquina m = (Maquina)players[i];
                        players[i].setEscolhaJogo(m.escolherJogo());
                        players[i].setValorDaAposta(m.apostar());
                    }
                    else if(players[i].getSaldo() <= 0){
                        players[i].setValorDaAposta(0);
                    }

                    if(players[i].getEscolhaJogo() == 1 && players[i].verificaJogosLivres() && players[i].getSaldo() > 0)
                        contJogoG++;
                    else if(players[i].getEscolhaJogo() == 2 && players[i].verificaJogosLivres() && players[i].getSaldo() > 0)
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
                            if(jogador.getEscolhaJogo() == 1 && jogador.getSaldo() > 0 && !jogador.getEstaCheio()){          
                                if(jogador instanceof Humano){
                                    JogoGeneral jogoG;
                                    if(jaAdicionou == 0){
                                        jogoG = jogador.inicializarJogoG();
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
                                        jogoG = jogador.inicializarJogoG();
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
                        if(players[k].getEscolhaJogo() == 1 && players[k].getSaldo() > 0 && !players[k].getEstaCheio()){
                            if(players[k].resultado((JogoGeneral)players[k].getJogoAtual()) == true){
                                players[k].atualizarSaldo(true);
                                System.out.println(players[k].getNome() + " - saldo atual: R$" + String.format("%.02f", players[k].getSaldo()));
                            }
                            else{
                                players[k].atualizarSaldo(false);
                            }
                        }
                    }
                }

                for (Jogador jogador : players){
                    if(jogador != null){
                        if(jogador.getEscolhaJogo() == 1 && jogador.getSaldo() > 0 && !jogador.getEstaCheio()){
                            if(jogador.resultado((JogoGeneral)jogador.getJogoAtual()) == false){
                                System.out.println("\nInfelizmente, " + jogador.getNome() + " perdeu - saldo atual: R$" + String.format("%.02f", jogador.getSaldo()));
                            }
                        }
                    }
                }
            }
                        
            // Todos os jogadores de jogo de azar, se houverem, jogam
            if(contJogoA != 0){
                System.out.println("\nJogadores do Jogo de Azar se preparem...");

                for(int i = 0; i < 10; i++){
                    if(players[i] != null && players[i].getSaldo() > 0 && !players[i].getEstaCheio()){
                        if(players[i].getEscolhaJogo() == 2){
                            System.out.println("\nÉ a vez de " + players[i].getNome() + ":");

                            if(players[i] instanceof Humano){
                                Humano h = (Humano)players[i];
                                JogoAzar jogoA = h.inicializarJogoA();
                                boolean resultadoH = h.executarJogoDeAzar(jogoA);
                                players[i].atualizarSaldo(resultadoH);
                                System.out.println("Saldo de " + players[i].getNome() + " após essa rodada: " + String.format("%.02f", players[i].getSaldo()));
                            }
                            else if(players[i] instanceof Maquina){
                                Maquina m = (Maquina) players[i];
                                JogoAzar jogoA = m.inicializarJogoA();
                                boolean resultadoM = m.executarJogoDeAzar(jogoA);
                                players[i].atualizarSaldo(resultadoM);
                                System.out.println("Saldo de " + players[i].getNome() + " após essa rodada: " + String.format("%.02f", players[i].getSaldo()));
                            }                
                        }
                    }
                }
            }      
        }
    }

    // O seguinte método grava os dados de uma partida em arquivo:
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
            System.out.println("Estado atual do campeonato gravado: ");
            mostrarSaldos(0);
            if(!jogadorVazio())
                mostrarExtratos(0);
		}catch (Exception ex) {
			System.err.println("erro: " + ex.toString());
		}
    }

    // Mostra a lista de jogadores resgistrados:
    public void mostrarJogadores(){
        int cont = 1;
        String s = new String();
        char tipo;

        if(jogadorVazio()){ // Verifica se tem jogadores registrados.
            System.out.println("\nNenhum jogador foi registrado.");
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
                        s = "Máquina";
                    }
                    System.out.println(cont + " - " + players[i].getNome() + "\t" + s);
                    s = "-";
                    cont++;
                }
            }
        }
        
    }

    // Este método mostra os saldos de todos os jogadores, ou só dos humanos, ou só das máquinas:
    public void mostrarSaldos(int escolha){
        int cont = 0;

        if(!this.jogadorVazio()){
            if (escolha == 0){
                System.out.println("\n=== SALDOS DOS JOGADORES ===");

                for(Jogador jogador : players){
                    if(jogador != null){
                        System.out.println(jogador.getNome() + ": " + String.format("%.02f", jogador.getSaldo()));
                    }
                }
            }
            else if(escolha == 1){
                System.out.println("\n=== SALDOS DOS JOGADORES HUMANOS ===");
                for(Jogador jogador : players){
                    if(jogador != null){
                        if(jogador instanceof Humano){
                            Humano jog = (Humano) jogador;
                            System.out.println(jog.getNome() + " (CPF: " + jog.getCpf() + "): " + String.format("%.02f", jogador.getSaldo()));
                            cont ++;
                        }
                    }
                }
            }
            else if(escolha == 2){
                System.out.println("\n=== SALDOS DOS JOGADORES MÁQUINAS ===");
                for(Jogador jogador : players){
                    if(jogador != null){
                        if(jogador instanceof Maquina){
                            System.out.println(jogador.getNome() + ": " + String.format("%.02f", jogador.getSaldo()));
                            cont ++;
                        }
                    }
                }
            }

            if(cont == 0 && (escolha == 2 || escolha == 1)){
                System.out.println("Não há saldos disponíveis. ");
            }
        }else{
            System.out.println("Não há nenhum saldo, pois não há jogadores registrados. ");
        }
    }

    // Este método imprime os extratos de acordo com a escolha do usário (escolha: 0-todos, 1-humanos, 2-máquina):
    public void mostrarExtratos(int escolha){
        System.out.print("Se você gostaria de ver os extratos relacionados aos dois jogos, digite 0. \nSe quiser ver apenas do jogo general, digite 1. \nSe quiser ver apenas do jogo de azar, digite 2.\nSua escolha: ");
        int esc = 0;
        int input = 0;
        int cont = 0;

        do{
            try {
                esc = teclado.nextInt();
                input = 1;
            } catch (java.util.InputMismatchException e) {
                System.out.println("Erro: Entrada inválida. Por favor, digite um número inteiro. ");
                teclado.nextLine();
                input = 0;
            }

            if(esc != 0 && esc != 1 && esc != 2){
                System.out.println("Opção inválida. Por favor, digite 0, 1 ou 2: ");
            }
        }while(input == 0 || (esc != 0 && esc != 1 && esc != 2));

        
        if(!this.jogadorVazio()){
            if(escolha == 0){
                for(Jogador jogador : players){
                    if(jogador != null){
                        System.out.println("\n==== Extratos do jogador " + jogador.getNome() + " ======");
                        JogoDados[] jogos = jogador.getJogosAdicionados();
                        cont = 0;
                        
                        if(jogador.getIndiceLivre() > 0){ // Se tiver algum jogo adicionado...
                            for(int k = 0; k < jogador.getIndiceLivre(); k++){
                                // ...imprime os extratos(nome, valor da aposta, derrota ou vitória) desses jogos.
                                if(jogos[k] instanceof JogoGeneral && (esc == 0 || esc == 1)){
                                    JogoGeneral j = (JogoGeneral)jogos[k];
                                    System.out.println("\nJOGO " + (k+1) + ": " + j.getNome() + "\n");
                                    System.out.println(jogador.mostraJogadasExecutadas(j));
                                    System.out.println("\nO valor apostado foi de R$" + String.format("%.02f", jogador.getApostas(k)) + " pelo jogador." );
                                    if(j.calculaResultado()){
                                        System.out.println("O jogador ganhou a aposta!");
                                    }
                                    else{
                                        System.out.println("O jogador perdeu a aposta.");
                                    }
                                    cont++;
                                }
                                if(jogos[k] instanceof JogoAzar && (esc == 0 || esc == 2)){
                                    JogoAzar j = (JogoAzar)jogos[k];
                                    System.out.println("\nJOGO " + (k+1) + ": " + j.getNome() + "\n");
                                    System.out.println("O valor apostado foi de R$" + String.format("%.02f", jogador.getApostas(k)) + " pelo jogador." );
                                    if(j.getResultado()){
                                        System.out.println("O jogador ganhou a aposta!");
                                    }
                                    else{
                                        System.out.println("O jogador perdeu a aposta.");
                                    }
                                    cont++;
                                }
                            }
                        }

                        if(jogador.getIndiceLivre() == 0 || cont == 0){
                            System.out.println("Ainda não foi jogado.");
                        }
                    }
                }
            }
            else if (escolha == 1){
                if(!semJogadorHumano()){
                    for(Jogador jogador : players){
                        if(jogador != null && jogador instanceof Humano){
                            System.out.println("\n==== Extratos do jogador " + jogador.getNome() + " ======");
                            JogoDados[] jogos = jogador.getJogosAdicionados();
                            cont = 0;

                            if(jogador.getIndiceLivre() > 0){
                                for(int k = 0;k < jogador.getIndiceLivre();k++){
                                    
                                    if(jogos[k] instanceof JogoGeneral && (esc==0 || esc==1)){
                                        JogoGeneral j = (JogoGeneral)jogos[k];
                                        System.out.println("\nJOGO " + (k+1) + ": " + j.getNome() + "\n");
                                        System.out.println(jogador.mostraJogadasExecutadas(j));
                                        System.out.println("\nO valor apostado foi de R$" + String.format("%.02f", jogador.getApostas(k)) + " pelo jogador." );
                                        if(j.calculaResultado()){
                                            System.out.println("O jogador ganhou a aposta!");
                                        }
                                        else{
                                            System.out.println("O jogador perdeu a aposta.");
                                        }
                                        cont ++;
                                    }
                                    if(jogos[k] instanceof JogoAzar && (esc==0 || esc==2)){
                                        JogoAzar j = (JogoAzar)jogos[k];
                                        System.out.println("\nJOGO " + (k+1) + ": " + j.getNome() + "\n");
                                        System.out.println("O valor apostado foi de R$" + String.format("%.02f", jogador.getApostas(k)) + " pelo jogador." );
                                        if(j.getResultado()){
                                            System.out.println("O jogador ganhou a aposta!");
                                        }
                                        else{
                                            System.out.println("O jogador perdeu a aposta.");
                                        }
                                        cont++;
                                    }
                                }
                            }

                            if(jogador.getIndiceLivre() == 0 || cont == 0){
                                System.out.println("Ainda não foi jogado.");
                            }
                        }
                    }
                }
                else{
                    System.out.println("Não há jogadores humanos registrados.");
                }
            }
            else if(escolha == 2){
                if(!semJogadorMaquina()){
                    for(Jogador jogador : players){
                        if(jogador != null && jogador instanceof Maquina){
                            System.out.println("\n==== Extratos do jogador " + jogador.getNome() + " ======");
                            JogoDados[] jogos = jogador.getJogosAdicionados();
                            cont = 0;
                            
                            if(jogador.getIndiceLivre() > 0){
                                for(int k = 0; k < jogador.getIndiceLivre(); k++){
                                    
                                    if(jogos[k] instanceof JogoGeneral && (esc == 0 || esc == 1)){
                                        JogoGeneral j = (JogoGeneral)jogos[k];
                                        System.out.println("\nJOGO " + (k + 1) + ": " + j.getNome() + "\n");
                                        System.out.println(jogador.mostraJogadasExecutadas(j));
                                        System.out.println("\nO valor apostado foi de R$" + String.format("%.02f", jogador.getApostas(k)) + " pelo jogador." );
                                        if(j.calculaResultado()){
                                            System.out.println("O jogador ganhou a aposta!");
                                        }
                                        else{
                                            System.out.println("O jogador perdeu a aposta.");
                                        }
                                        cont ++;
                                    }
                                    if(jogos[k] instanceof JogoAzar && (esc == 0 || esc == 2)){
                                        JogoAzar j = (JogoAzar)jogos[k];
                                        System.out.println("\nJOGO " + (k+1) + ": " + j.getNome() + "\n");
                                        System.out.println("O valor apostado foi de R$" + String.format("%.02f", jogador.getApostas(k)) + " pelo jogador." );
                                        if(j.getResultado()){
                                            System.out.println("O jogador ganhou a aposta!");
                                        }
                                        else{
                                            System.out.println("O jogador perdeu a aposta.");
                                        }
                                        cont ++;
                                    }
                                }
                            }
                            
                            if (jogador.getIndiceLivre() == 0 || cont == 0){
                                System.out.println("Ainda não foi jogado.");
                            }
                        }
                    }
                }
                else{
                    System.out.println("Não há jogadores máquinas registrados.");
                }
            }
        }
        else{
            System.out.println("Não há jogadores para a exibição de extratos. ");
        }
    }

    // Este método imprime as estatísticas do campeonato na tela:
    public void mostrarEstatisticas(){
        
        int[] dadosJogos = {0,0,0,0,0,0};       // Guardará as quantidades de cada dado rolado em todo o campeonato.
        int[] dadosGeneral = {0,0,0,0,0,0};     // Guardará as quantidades de cada dado rolado para o jogo general.
        int[] dadosAzar = {0,0,0,0,0,0};        // Guardará as quantidades de cada dado rolado para o jogo de azar.
        int[] dadosHumanos = {0,0,0,0,0,0};     // Guardará as quantidades de cada dado rolado para os jogadores humanos.
        int[] dadosMaquinas = {0,0,0,0,0,0};    // Guardará as quantidades de cada dado rolado para os jogadores máquinas.

        char op;
        int total;
        double valor;
        System.out.println("========= Estatísticas ========");

        System.out.println("Escolha a poção:");
        System.out.println("a - Estatística do campeonato");
        System.out.println("b - Estatística do jogo general");
        System.out.println("c - Estatística do jogo de azar");
        System.out.println("d - Estatística dos jogadores humanos");
        System.out.println("e - Estatística dos jogadores máquinas");
        System.out.println("f - Estatística de cada jogador");
        System.out.println("g- Estatística de um jogador específico");
        System.out.println("Informe a sua escolha: ");

        do{
            op = teclado.next().charAt(0);

            if (op != 'a' && op != 'b' && op != 'c' && op != 'd' && op != 'e' && op != 'f' && op != 'g'){
                System.out.println("Opcao invalida! Tente novamente.");
            }
        }while(op != 'a' && op != 'b' && op != 'c' && op != 'd' && op != 'e' && op != 'f' && op != 'g');
        
        teclado.nextLine();

        switch(op){
            case 'a': 
                total = 0;
                System.out.println("\nDados no campeonato\n");
                for(Jogador jogador : players){
                    if(jogador != null && jogador.getIndiceLivre()>0){
                        JogoDados[] jogos = jogador.getJogosAdicionados();
                        
                        for(int k = 0; k < jogador.getIndiceLivre(); k++){
                            JogoDados j = jogos[k];
                            for(int i=0;i<6;i++){
                                dadosJogos[i] = dadosJogos[i] + j.getFacesRoladas()[i];
                            }
                        }
                    }
                }
                for(int i = 0; i < 6; i++){
                    total = total + dadosJogos[i];
                }
                if(total!=0){
                    System.out.println("Numero do dado\tQuantia rolada");
                    for(int i =0; i<6;i++){
                        valor = (double) dadosJogos[i] / total;
                        System.out.println("\t" + (i+1) + "\t\t" + dadosJogos[i] + " (" + String.format("%.02f", (valor*100)) + "% do total)");
                    }
                }
                else{
                    System.out.println("Ainda não jogado.");
                }
                break;
            case 'b':
                total = 0;
                System.out.println("\nDados no Jogo General\n");
                for(Jogador jogador : players){
                    if(jogador != null && jogador.getIndiceLivre()>0){
                        JogoDados[] jogos = jogador.getJogosAdicionados();
                        
                        for(int k=0; k<jogador.getIndiceLivre(); k++){
                            if(jogos[k] instanceof JogoGeneral){
                                JogoDados j = jogos[k];
                                for(int i=0;i<6;i++){
                                    dadosGeneral[i] = dadosGeneral[i] + j.getFacesRoladas()[i];
                                }
                            }
                        }
                    }
                }
                for(int i = 0; i < 6; i++){
                    total = total + dadosGeneral[i];
                }
                if(total!=0){
                    System.out.println("Numero do dado\tQuantia rolada");
                    for(int i =0; i<6;i++){
                        valor = (double) dadosGeneral[i] / total;
                        System.out.println("\t" + (i+1) + "\t\t" + dadosGeneral[i] + " (" + String.format("%.02f", (valor*100)) + "% do total)");
                    }
                }
                else{
                    System.out.println("Ainda não jogado.");
                }
                break;
            case 'c':
                total = 0;
                System.out.println("\nDados no Jogo de Azar\n");
                for(Jogador jogador : players){
                    if(jogador != null && jogador.getIndiceLivre()>0){
                        JogoDados[] jogos = jogador.getJogosAdicionados();
                        
                        for(int k = 0; k < jogador.getIndiceLivre(); k++){
                            if(jogos[k] instanceof JogoAzar){
                                JogoDados j = jogos[k];
                                for(int i = 0; i < 6;i++){
                                    dadosAzar[i] = dadosAzar[i] + j.getFacesRoladas()[i];
                                }
                            }
                        }
                    }
                }
                for(int i =0; i<6;i++){
                    total = total + dadosAzar[i];
                }
                if(total!=0){
                    System.out.println("Numero do dado\tQuantia rolada");
                    for(int i =0; i<6;i++){
                        valor = (double) dadosAzar[i] / total;
                        System.out.println("\t" + (i+1) + "\t\t" + dadosAzar[i] + " (" + String.format("%.02f", (valor*100)) + "% do total)");
                    }
                }
                else{
                    System.out.println("Ainda não jogado.");
                }
                break;
            case 'd': 
                total = 0;
                System.out.println("\nDados por humanos\n");
                for(Jogador jogador : players ){
                    if(jogador != null && jogador instanceof Humano && jogador.getIndiceLivre()>0){
                        JogoDados[] jogos = jogador.getJogosAdicionados();
                        
                        for(int k=0;k<jogador.getIndiceLivre();k++){
                            JogoDados j = jogos[k];
                            for(int i=0;i<6;i++){
                                dadosHumanos[i] = dadosHumanos[i] + j.getFacesRoladas()[i];
                            }
                        }
                    }
                }
                for(int i =0; i<6;i++){
                    total = total + dadosHumanos[i];
                }
                if(total!=0){
                    System.out.println("Numero do dado\tQuantia rolada");
                    for(int i =0; i<6;i++){
                        valor = (double) dadosHumanos[i] / total;
                        System.out.println("\t" + (i+1) + "\t\t" + dadosHumanos[i] + " (" + String.format("%.02f", (valor*100)) + "% do total)");
                    }
                }
                else{
                    System.out.println("Ainda não jogado.");
                }
                break;
            case 'e': 
                total = 0;
                System.out.println("\nDados por maquinas\n");
                for(Jogador jogador : players){
                    if(jogador != null && jogador instanceof Maquina && jogador.getIndiceLivre()>0){
                        JogoDados[] jogos = jogador.getJogosAdicionados();
                        
                        for(int k = 0; k < jogador.getIndiceLivre(); k++){
                            JogoDados j = jogos[k];
                            for(int i=0;i<6;i++){
                                dadosMaquinas[i] = dadosMaquinas[i] + j.getFacesRoladas()[i];
                            }
                        }
                    }
                }
                for(int i = 0; i<6;i++){
                    total = total + dadosMaquinas[i];
                }
                if(total!=0){
                    System.out.println("Numero do dado\tQuantia rolada");
                    for(int i =0; i<6;i++){
                        valor = (double) dadosMaquinas[i] / total;
                        System.out.println("\t" + (i+1) + "\t\t" + dadosMaquinas[i] + " (" + String.format("%.02f", (valor*100)) + "% do total)");
                    }
                }
                else{
                    System.out.println("Ainda não jogado.");
                }
                break;
            case 'f':
                if(players!=null && jogadorLivre()!=0){
                    System.out.println("\nDados por jogador");

                    for(Jogador jogador : players){
                        total = 0;
                        if(jogador != null){
                            int[] dadosJogador = {0,0,0,0,0,0};
                            System.out.println("\nJogador " + jogador.getNome() + " - " + jogador.getTipo());
                            JogoDados[] jogos = jogador.getJogosAdicionados();
                            
                            for(int k = 0; k < jogador.getIndiceLivre(); k++){
                                JogoDados j = jogos[k];
                                for(int i = 0; i < 6; i++){
                                    dadosJogador[i] = dadosJogador[i] + j.getFacesRoladas()[i];
                                }
                            }
    
                            for(int i = 0; i < 6; i++){
                                total = total + dadosJogador[i];
                            }
                            if(total!=0){
                                System.out.println("Numero do dado\tQuantia rolada");
                                for(int i = 0; i < 6; i++){
                                    valor = (double) dadosJogador[i] / total;
                                    System.out.println("\t" + (i+1) + "\t\t" + dadosJogador[i] + " (" + String.format("%.02f", (valor*100)) + "% do total)");
                                }
                            }
                            else{
                                System.out.println("Ainda não jogado.");
                            }
                        }
                    }
                }
                else{
                    System.out.println("Nenhum jogador registrado.");
                }
                break;
            case 'g':
                System.out.println("Informe o nome do jogador procurado: ");
                String nomeJogador = teclado.nextLine();
                boolean jogadorExiste = false;

                if(this.players != null){
                    for(Jogador jogador : players){
                        if(jogador != null){
                            if(jogador.getNome().equals(nomeJogador)){
                                jogadorExiste = true;
                                System.out.println("Você gostaria de ver as estatísticas de " + nomeJogador + " para o Jogo General(1) ou para o Jogo de Azar(2)? ");
                                int choice = teclado.nextInt();

                                total = 0;
                                int[] dadosJogadorEspecifico = {0,0,0,0,0,0};
                                JogoDados[] jogos = jogador.getJogosAdicionados();

                                for(int k = 0; k < jogador.getIndiceLivre(); k++){
                                    if(jogos[k] instanceof JogoGeneral && choice == 1){
                                        JogoDados j = jogos[k];
                                        for(int i = 0; i < 6; i++){
                                            dadosJogadorEspecifico[i] = dadosJogadorEspecifico[i] + j.getFacesRoladas()[i];
                                        }
                                    }
                                    else if(jogos[k] instanceof JogoAzar && choice == 2){
                                        JogoDados j = jogos[k];
                                        for(int i = 0; i < 6; i++){
                                            dadosJogadorEspecifico[i] = dadosJogadorEspecifico[i] + j.getFacesRoladas()[i];
                                        }
                                    }
                                }
        
                                for(int i = 0; i < 6; i++){
                                    total = total + dadosJogadorEspecifico[i];
                                }
                                if(total!=0){
                                    System.out.println("Numero do dado\tQuantia rolada");
                                    for(int i = 0; i < 6; i++){
                                        valor = (double) dadosJogadorEspecifico[i] / total;
                                        System.out.println("\t" + (i+1) + "\t\t" + dadosJogadorEspecifico[i] + " (" + String.format("%.02f", (valor*100)) + "% do total)");
                                    }
                                }
                                else{
                                    System.out.println("Ainda não jogado.");
                                }
                            }
                        }
                    }
                }

                if(!jogadorExiste){
                    System.out.println("Jogador não encontrado...");
                }

                break;
            default:
                System.out.println("ERRO");
        }

    }
}