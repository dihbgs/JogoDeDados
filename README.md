# Campeonato_de_Jogos_de_Dados
Projeto de programação em Java referente à disciplina de Programação Orientada a Objetos no qual um simulador de campeonato de apostas em jogos de dados é desenvolvido, permitindo aos jogadores presentes realizar apostas em dois tipos de jogos de dados, o Jogo de Azar e o Jogo General.

## Regras do Campeonato:
• O campeonato permitirá no máximo dez jogadores (humano/máquina) e cada jogador poderá realizar até dez apostas nos jogos de sua escolha, enquanto houver saldo suficientepara tal.

• Para a execução de uma rodada de apostas é necessário ao menos UM jogador participante, com saldo disponível para realização de aposta (até no máximo dez) em jogos de sua escolha (um jogo por aposta).

• A cada rodada do campeonato, será perguntado a cada jogador sobre o valor a ser apostado e para qual jogo. Na sequência será realizada a rodada do jogo e a vez será passada ao próximo jogador.

• Para cada jogador serão armazenados os resultados de até dez jogos realizados (do tipo General ou de Azar) com seus respectivos valores de aposta.

– O resultado do Jogo de Azar compreende se o jogador ganhou ou perdeu determinadajogada, de acordo com as regras do jogo.

– O resultado do Jogo General compreende os valores obtidos para as 13 jogadas. O jogador ganha a aposta se a soma dos valores das jogadas de 1 a 12 for maior que o dobro do valor obtido na jogada 13 (aleat´oria).

• Quando o jogador é inserido no campeonato, o mesmo inicia com o saldo de R$ 100,00. No início de cada rodada, o jogador indicará o quanto deseja apostar. Se o jogador ganhar, este receberá a mesma quantia que apostou, caso contrário ele perderá a quantia que apostou, por exemplo:

– O jogador inicialmente tinha R$ 100,00, apostou R$ 10,00 e ganhou. Então o novo saldo do jogador é de R$ 110,00. Em um segundo momento o jogador se sentiu confiante e apostou R$ 50,00 e perdeu, logo seu saldo ficou em R$ 60,00;

• Poderão ser executadas n rodadas de apostas desde que sejam cumpridas as condições para cada jogador (saldo suficiente e não ultrapassar mais que dez apostas).

• Em cada rodada, para cada jogo escolhido, deverão ser contabilizadas a quantidade que cada face de cada dado já fora sorteada.

• A aplicação produzirá diferentes relatórios de saldos, extratos de resultado dos jogos e estatística das faces sorteadas, por tipo de jogador, por tipo de jogo, por rodadas e por total de campeonato.

## Jogo de Azar

Um jogo de azar faz uso de dois dados e possui a seguinte regra: O jogador lança os dois dados:

• Se a soma das faces dos dados for 7 ou 11 o jogador ganha;

• Se a soma for 2, 3 ou 12 o jogador perde;

• Se a soma obtida no primeiro lançamento de dados não for qualquer um dos valores acima, esta soma será tratada como o valor a ser buscado pelo jogador nos lançamentos subsequentes, ou seja, o jogador só irá ganhar se ele conseguir novamente atingir a soma obtida com o primeiro lançamento.

## Jogo General

### Introdução

General é um jogo de dados para dois ou mais jogadores. Para jogar General são necessários cinco dados comuns (hexaédricos) e uma cartela de marcação. O objetivo do jogo é marcar o maior número de pontos, através de algumas combinações de resultados nos dados.

Essa aplicação executa n (indeterminadas) partidas, cada uma com 13 rodadas, para os jogadores (humanos ou máquinas) participantes do Campeonato do Jogo General. Em toda rodada, cada jogador (humano ou máquina), por sua vez, joga os dados e, conforme o resultado obtido, rola os dados mais uma única vez, pula, ou então marca a jogada prevista em sua cartela. Uma vez marcada, aquela jogada não poderá ser repetida pelo mesmo jogador até o final da rodada.

### Regras:

(1) Sendo 13 o número de jogadas possíveis e 13 o número máximo de linhas para cada coluna na cartela de marcação, uma rodada consiste de 13 jogadas para cada jogador.

(2) Cada jogador (humano ou máquina), em sua vez, tem apenas uma chance de arremessar os dados.

(3) O resultado obtido ao final do arremesso deve ser classificado, pelo próprio jogador, como uma das seguintes 13 possibilidades:

-Jogada de 1: um certo número de dados (de 0 a 5) marcando o número 1; sendo que a jogada vale mais pontos conforme a quantidade de dados que marcarem o número 1. Por exemplo: 1-1-1-4-5 vale 3 pontos.

-Jogadas de 2, 3, 4, 5 e 6: correspondentes à jogada de 1 para os demais números. Por exemplo: 3-3-4-4-5 vale 6 pontos se for considerada uma jogada de 3; ou 8 pontos se for considerada uma jogada de 4; ou ainda 5 pontos se for uma jogada de 5.

-Trinca (T): três dados marcando o mesmo número. Vale a soma dos 5 dados. Exemplo: 4-4-4-5-6 vale 23 pontos.

-Quadra (Q): quatro dados marcando o mesmo n´umero. Vale a soma dos 5 dados. Exemplo: 1-5-5-5-5 vale 21 pontos.

-Full-hand (F) ou Full-house: uma trinca e um par (exemplo: 2-2-2-6-6). Vale 25 pontos para qualquer combinação.

-Sequência alta (S+): 2-3-4-5-6. Vale 30 pontos.

-Sequência baixa (S-): 1-2-3-4-5. Vale 40 pontos.

-General (G): cinco dados marcando o mesmo número (por exemplo: 4-4-4-4-4). Vale 50 pontos.

-Jogada aleatória (X) : qualquer combinação. Vale a soma dos 5 dados. Por exemplo: 1-4-4-5-6 vale 20 pontos.

(4) O resultado deverá ser mostrado na forma de cartela, na coluna do jogador e na linha correspondente à jogada. Aquela linha (e portanto aquela jogada) não poderá mais ser utilizada pelo jogador na mesma rodada.

(5) Se um determinado resultado não cumprir os requisitos para a jogada escolhida, o jogador zera a respectiva jogada. E ainda, se um determinado resultado não puder ser classificado como nenhuma das jogadas ainda restantes para aquele jogador, ele deverá escolher qual das jogadas restantes será descartada, marcando 0 (zero) para a jogada correspondente.

(6) Ao final dos 13 arremessos de dados, o jogador ganha o jogo se a soma dos valores das jogadas de 1 a 6, trinca, quadra, full-hand, sequências alta e baixa e general (jogadas de 1 a 12) for maior que o dobro do valor obtido na jogada aleatória (jogada 13).
