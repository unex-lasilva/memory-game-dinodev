# Manga Rosa Memory Game
Seja bem-vinda(o) ao nosso processo seletivo para trainee da Manga Rosa. Como vocês sabem, nós somos uma das maiores plataformas de entregas do norte e nordeste do país. 
A nossa missão é entregar qualquer coisa, a qualquer hora e em qualquer lugar. Neste ano, os nossos processos seletivos serão coordenados por nossa parceira Jambo edu. O nosso principal objetivo durante esses próximos meses é avaliar os seus conhecimentos sobre estrutura de dados e desenvolvimento de sistemas utilizando a lingugaem de programação Java. 

Nós acreditamos que o bem-estar das pessoas potencializa o engajamento delas nas metas e objetivos da empresa. Por isso, todas as sextas-feiras durante a tarde, a empresa dedica um momento de descompressão e interação nas equipes. Nós chamamos esse período de “Momento deploy”. Nesse desafio queremos que vocês desenvolvam um jogo da memória. 

O jogo da memória clássico é basicamente um jogo de cartas que tem o objetivo principal exercitar a memória dos jogadores. Neste jogo, todas as cartas possuem figuras apenas em um dos lados. A mesma figura se repete em duas cartas diferentes. De início, as cartas são colocadas com as figuras para baixo e depois embaralhadas. Em cada rodada, cada participante pode virar duas cartas e, se forem iguais, recolhe o par e joga novamente. Contudo, se forem diferentes, a(o) participante deve virar as cartas e devolvê-las para o jogo, passando a vez para a(o) outra(o) participante. Fácil não? Mas você não acha que vamos deixar algo tão fácil assim, né? Vamos para algumas regrinhas do nosso Manga’s Memory Game: 

1.	No início do jogo, a(o) participante deve escolher o tamanho do tabuleiro: 4x4, 6x6, 8x8 ou 10x10. 
2.	Todos os pares de figuras possuem uma cor de fundo: vermelho, azul, amarelo ou preto. 
3.	Em todo jogo deve existir uma figura de fundo preto. 
4.	Em todo jogo metade das figuras devem ter fundo azul e vermelho. 
5.	As demais figuras que sobram no jogo devem ter fundo amarelo.

Vamos exemplificar essas regras iniciais para vocês. Um exemplo sempre é uma boa forma de aprender. Imagine que as(os) participantes escolheram o tamanho de tabuleiro 4x4. Nós temos o total de 16 cartas, ou seja, 8 pares (ou figuras). Dessas 8 figuras, vamos ter a seguinte distribuição:

1.	4 (quatro) figuras vermelhas e azuis (2 vermelhas e 2 azuis).
2.	1 (uma) figura preta.
3.	3 (três) figuras amarelas. 

**Bom, vamos para as outras regras? Vamos, né?! Acho que essa parte já deu para entender. Lá vamos nós:** 

1.	Cada participante deve ter atribuído a si uma cor (vermelho ou azul) no início do jogo. 
2.	Todo participante deve ter um nome registrado. Senão, o nome padrão “PARTICIPANTE01” e “PARTICIPANTE02” devem ser atribuídos a cada um das(os) participantes. 
3.	Cada participante possui uma pontuação atrelada a si. 
4.	Se a(o) participante encontrar um par de cartas com fundo amarelo, fatura 1 ponto. 
5.	Se a(o) participante encontrar um par de cartas com o fundo da sua cor, fatura 5 pontos. 
6.	Se a(o) participante encontrar um par de cartas com o fundo da cor de seu adversário e errar, perde 2 pontos. Porém, se acertar, ganha apenas 1 ponto. Essa regra deve ser aplicada a cor da primeira carta virada na rodada. 
7.	A(o) participante não pode ter pontuação negativa. Se ela(ele) perder mais pontos do que possui, ficará com a pontuação zerada. 
8.	Se a(o) participante encontrar uma carta com o fundo preto e errar o seu par, perde 50 pontos, mas se acertar, ganha os 50 pontos. 

**Outras regras importantes:** 

1.	Se a(o) participante informar uma opção de tamanho de tabuleiro inválida, você deve apresentar a seguinte mensagem de advertência: “Por favor, escolha umas das opções de tamanho de tabuleiro disponíveis”. 
2.	Participantes devem informar a linha e a coluna da carta que desejam virar. 
3.	Valores de linha e coluna inválidos devem exibir uma mensagem de erro: “Posição da carta inválida, por favor, insira uma posição válida”. Você deve continuar solicitando ao usuário uma nova posição até que uma posição válida seja informada ou até que o usuário erre 3 vezes. Caso a(o) participante erre três vezes, ela(ele) perde a vez para a(o) adversária(o). 
4.	Se a(o) participante escolher uma carta já virada, uma mensagem de atenção deve ser exibida: “A carta da posição informada já está virada, por favor, escolha outra posição”. Você deve continuar solicitando ao usuário uma nova posição até que uma posição válida seja informada ou até que o usuário erre 3 vezes. Caso a(o) participante erre três vezes, ela(ele) perde a vez para a(o) adversária(o).


**Team members:**
1. *Nome do membro* - *e-mail*
2. *Nome do membro* - *e-mail*
