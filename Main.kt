enum class Cor {
    VERMELHO, AZUL
}

enum class CartaCor(val simbolo: String) {
    VERMELHO("V"), AZUL("A"), AMARELO("Y"), PRETO("P")
}

data class Carta(val id: Int, val cor: CartaCor, var virada: Boolean = false)

val reset = "\u001B[0m"
val preto = "\u001B[30m"
val vermelho = "\u001B[31m"
val amarelo = "\u001B[33m"
val azul = "\u001B[34m"

fun getCorAnsi(cor: CartaCor): String {
    return when (cor) {
        CartaCor.VERMELHO -> vermelho
        CartaCor.AZUL -> azul
        CartaCor.AMARELO -> amarelo
        CartaCor.PRETO -> preto
    }
}

fun exibirMenu(jogoTerminou: Boolean): Int {
    println("Manga Rosa Memory Game")
    println("1. INICIAR")
    println("2. PONTUAÇÃO PARTICIPANTES")
    println("3. REGRAS DO JOGO")

    if (jogoTerminou) {
        println("4. JOGAR NOVAMENTE")
    }

    println("0. SAIR")
    print("Informe sua opção: ")

    return readLine()?.toIntOrNull() ?: 1
}

fun escolherTabuleiro(): Int {
    println("\nEscolha o tamanho do tabuleiro:")
    println("1. 4x4")
    println("2. 6x6")
    println("3. 8x8")
    println("4. 10x10")
    print("Digite a opção: ")

    return when (readLine()?.toIntOrNull()) {
        1 -> 4
        2 -> 6
        3 -> 8
        4 -> 10
        else -> {
            println("Opção inválida. Usando tamanho padrão 4x4.")
            4
        }
    }
}

fun mostrarPontuacao(pontuacaoParticipante1: Int, pontuacaoParticipante2: Int) {
    println("\nPontuação do último jogo:")
    println("Participante 1: $pontuacaoParticipante1 pontos")
    println("Participante 2: $pontuacaoParticipante2 pontos")
}

fun exibirRegras() {
    println("\nRegras do Jogo:")
    println("1. O objetivo do jogo é fazer o maior número de pares de cartas.")
    println("2. As cartas possuem cores e valores, e os jogadores devem tentar encontrar pares de cartas com a mesma cor ou valor.")
    println("3. A cada turno, o jogador escolhe duas cartas para virar.")
    println("4. Se as cartas viradas forem iguais, o jogador pontua.")
    println("5. O jogo termina quando todas as cartas forem viradas e todos os pares forem encontrados.")
    println("6. Aquele que fizer mais pontos vence o jogo.")
    println("Após isso, você poderá iniciar um novo jogo ou consultar a pontuação.")

    println("\nDigite 'sair' para voltar ao menu principal.")
    var comando = readLine()?.lowercase()
    while (comando != "sair") {
        println("Comando inválido. Por favor, digite 'sair' para voltar ao menu.")
        comando = readLine()?.lowercase()
    }
}

fun obterNomeParticipante(participante: Int): String {
    print("QUAL O APELIDO DA(O) PARTICIPANTE $participante?\nDIGITE O APELIDO: ")
    return readLine()?.takeIf { it.isNotBlank() } ?: "PARTICIPANTE$participante"
}

fun escolherCor(participante: Int): Cor {
    println("QUAL A COR DO PARTICIPANTE $participante? (Vermelho ou Azul)")
    print("DIGITE A COR: ")

    return when (readLine()?.lowercase()) {
        "vermelho" -> Cor.VERMELHO
        "azul" -> Cor.AZUL
        else -> {
            println("Por favor, escolha uma cor válida (Vermelho ou Azul).")
            escolherCor(participante)
        }
    }
}

fun criarTabuleiro(tamanho: Int): List<List<Carta>> {
    val totalPares = (tamanho * tamanho) / 2

    // Definindo a distribuição de cores com base no tamanho do tabuleiro
    val paresAzulVermelho = when (tamanho) {
        4 -> 4
        6 -> 9
        8 -> 16
        10 -> 25
        else -> 0
    }

    val paresPreto = when (tamanho) {
        4 -> 1
        6 -> 2
        8 -> 4
        10 -> 6
        else -> 0
    }

    val paresAmarelo = totalPares - paresAzulVermelho - paresPreto

    // Criando as cartas
    val cartas = mutableListOf<Carta>()
    var idAtual = 0

    // Adicionando pares de azul e vermelho
    repeat(paresAzulVermelho) {
        cartas.add(Carta(idAtual, CartaCor.AZUL))
        cartas.add(Carta(idAtual, CartaCor.VERMELHO))
        idAtual++
    }

    // Adicionando pares de preto
    repeat(paresPreto) {
        cartas.add(Carta(idAtual, CartaCor.PRETO))
        cartas.add(Carta(idAtual, CartaCor.PRETO))
        idAtual++
    }

    // Adicionando pares de amarelo
    repeat(paresAmarelo) {
        cartas.add(Carta(idAtual, CartaCor.AMARELO))
        cartas.add(Carta(idAtual, CartaCor.AMARELO))
        idAtual++
    }

    // Embaralhando as cartas
    cartas.shuffle()

    // Dividindo o tabuleiro em linhas e colunas
    return cartas.chunked(tamanho)
}

fun exibirTabuleiro(tabuleiro: List<List<Carta>>) {
    tabuleiro.forEachIndexed { linhaIndex, linha ->
        print("${linhaIndex + 1} ")
        linha.forEach { carta ->
            if (carta.virada) {
                val corAnsi = getCorAnsi(carta.cor)
                print("$corAnsi[${carta.cor.simbolo}]$reset ")
            } else {
                print("[ ] ")
            }
        }
        println()
    }
    println("   " + (1..tabuleiro.size).joinToString("  ") { it.toString() })
}

fun verificarPontos(carta1: Carta, carta2: Carta, corParticipante1: Cor, corParticipante2: Cor, vezDoParticipante1: Boolean): Int {
    return when {
        // Par de preto: ganha 50 pontos e continua a vez
        carta1.cor == CartaCor.PRETO && carta2.cor == CartaCor.PRETO -> 50

        // Par de amarelo: ganha 1 ponto
        carta1.cor == CartaCor.AMARELO && carta2.cor == CartaCor.AMARELO -> 1

        // Par da cor do jogador: ganha 5 pontos
        carta1.cor == CartaCor.valueOf(corParticipante1.name) && carta2.cor == CartaCor.valueOf(corParticipante1.name) && vezDoParticipante1 -> 5
        carta1.cor == CartaCor.valueOf(corParticipante2.name) && carta2.cor == CartaCor.valueOf(corParticipante2.name) && !vezDoParticipante1 -> 5

        // Par da cor do adversário: se acertar, ganha 2 pontos; se errar, perde 2 pontos
        carta1.cor == CartaCor.valueOf(corParticipante2.name) && carta2.cor == CartaCor.valueOf(corParticipante2.name) && vezDoParticipante1 -> if (carta1.id == carta2.id) 2 else -2
        carta1.cor == CartaCor.valueOf(corParticipante1.name) && carta2.cor == CartaCor.valueOf(corParticipante1.name) && !vezDoParticipante1 -> if (carta1.id == carta2.id) 2 else -2

        // Erro ao encontrar par de preto: perde 50 pontos
        carta1.cor == CartaCor.PRETO || carta2.cor == CartaCor.PRETO -> -50

        // Outros casos: sem pontuação
        else -> 0
    }
}

fun main() {
    var pontuacaoParticipante1 = 0
    var pontuacaoParticipante2 = 0
    var jogoTerminou = false // Variável para controlar se o jogo terminou

    while (true) {
        val opcao = exibirMenu(jogoTerminou) // Passar o estado do jogo para o menu

        when (opcao) {
            1 -> {
                println("Opção INICIAR selecionada.")
                iniciarJogo()
                jogoTerminou = false // Reiniciar o estado do jogo
            }
            2 -> {
                println("Opção PONTUAÇÃO PARTICIPANTES selecionada.")
                mostrarPontuacao(pontuacaoParticipante1, pontuacaoParticipante2)
            }
            3 -> {
                println("Opção REGRAS DO JOGO selecionada.")
                exibirRegras()
            }
            4 -> {
                if (jogoTerminou) {
                    println("Reiniciando o jogo...")
                    iniciarJogo()
                    jogoTerminou = false // Reiniciar o estado do jogo
                } else {
                    println("Opção inválida. O jogo ainda não terminou.")
                }
            }
            5 -> {
                println("Saindo do jogo. Até logo!")
                break
            }
            else -> {
                println("Opção inválida.")
            }
        }
    }
}

fun iniciarJogo() {
    var pontuacaoParticipante1 = 0
    var pontuacaoParticipante2 = 0

    val tamanhoTabuleiro = escolherTabuleiro()

    val nomeParticipante1 = obterNomeParticipante(1)
    val nomeParticipante2 = obterNomeParticipante(2)

    val corParticipante1 = escolherCor(1)
    val corParticipante2 = escolherCor(2)

    println("Configurações concluídas!")
    println("Tabuleiro de tamanho $tamanhoTabuleiro x $tamanhoTabuleiro")
    println("$nomeParticipante1 escolheu a cor ${corParticipante1.name}.")
    println("$nomeParticipante2 escolheu a cor ${corParticipante2.name}.")

    val tabuleiro = criarTabuleiro(tamanhoTabuleiro)
    val totalPares = (tamanhoTabuleiro * tamanhoTabuleiro) / 2

    var jogoAtivo = true
    var vezDoParticipante1 = true
    var cartasViradas = mutableListOf<Pair<Int, Int>>()

    while (jogoAtivo) {
        exibirTabuleiro(tabuleiro)

        val participanteAtual = if (vezDoParticipante1) nomeParticipante1 else nomeParticipante2
        println("Vez de $participanteAtual")

        var tentativas = 0
        var entradaValida = false
        var linha = -1
        var coluna = -1

        while (!entradaValida && tentativas < 3) {
            print("Escolha uma linha e uma coluna (ex: 1 2): ")
            val entrada = readLine()

            if (entrada.isNullOrBlank() || entrada.split(" ").size < 2) {
                tentativas++
                if (tentativas < 3) {
                    println("Por favor, digite uma linha e coluna (exemplo: 1 2). Tentativas restantes: ${3 - tentativas}")
                }
            } else {
                val partes = entrada.split(" ")
                linha = partes[0].toIntOrNull()?.minus(1) ?: -1
                coluna = partes[1].toIntOrNull()?.minus(1) ?: -1

                if (linha !in 0 until tamanhoTabuleiro || coluna !in 0 until tamanhoTabuleiro) {
                    tentativas++
                    if (tentativas < 3) {
                        println("Posição inválida. Tente novamente. Tentativas restantes: ${3 - tentativas}")
                    }
                } else {
                    entradaValida = true
                }
            }
        }

        if (!entradaValida) {
            println("Número máximo de tentativas excedido. $participanteAtual perdeu a vez.")
            vezDoParticipante1 = !vezDoParticipante1
            continue
        }

        val cartaSelecionada = tabuleiro[linha][coluna]

        if (cartaSelecionada.virada) {
            println("Carta já virada. Tente novamente.")
            continue
        }

        cartaSelecionada.virada = true
        cartasViradas.add(Pair(linha, coluna))
        exibirTabuleiro(tabuleiro)

        if (cartasViradas.size == 2) {
            val (linha1, coluna1) = cartasViradas[0]
            val (linha2, coluna2) = cartasViradas[1]
            val carta1 = tabuleiro[linha1][coluna1]
            val carta2 = tabuleiro[linha2][coluna2]

            val pontos = verificarPontos(carta1, carta2, corParticipante1, corParticipante2, vezDoParticipante1)

            if (pontos > 0) {
                println("Par encontrado! Pontuação +$pontos para $participanteAtual.")
                if (vezDoParticipante1) pontuacaoParticipante1 += pontos else pontuacaoParticipante2 += pontos

                // Se for um par de preto, o jogador continua a vez
                if (carta1.cor == CartaCor.PRETO && carta2.cor == CartaCor.PRETO) {
                    println("Você encontrou um par de preto! Continue jogando.")
                    cartasViradas.clear()
                    continue
                }
            } else if (pontos < 0) {
                println("Par errado. Você perdeu ${-pontos} pontos!")
                if (vezDoParticipante1) pontuacaoParticipante1 += pontos else pontuacaoParticipante2 += pontos

                // Verificar se o jogador perdeu o jogo (ficou com menos de 50 pontos após perder 50)
                if ((vezDoParticipante1 && pontuacaoParticipante1 < 0) || (!vezDoParticipante1 && pontuacaoParticipante2 < 0)) {
                    if (pontos == -50) {
                        println("$participanteAtual não tinha pontos suficientes para perder 50 pontos. Fim de jogo!")
                        jogoAtivo = false
                        break
                    } else {
                        // Zerar a pontuação se ficar negativa (exceto no caso de perder 50 pontos)
                        if (vezDoParticipante1) pontuacaoParticipante1 = 0 else pontuacaoParticipante2 = 0
                        println("Pontuação zerada para $participanteAtual.")
                    }
                }
            }

            // Passar a vez se não for um par de preto
            if (carta1.cor != CartaCor.PRETO || carta2.cor != CartaCor.PRETO) {
                vezDoParticipante1 = !vezDoParticipante1
            }

            // Virar as cartas de volta se não forem iguais
            if (carta1.id != carta2.id) {
                carta1.virada = false
                carta2.virada = false
            }

            cartasViradas.clear()
        }

        if (tabuleiro.flatten().all { it.virada }) {
            jogoAtivo = false
            println("\nJogo terminado!")
            println("Pontuação final:")
            println("$nomeParticipante1: $pontuacaoParticipante1 pontos")
            println("$nomeParticipante2: $pontuacaoParticipante2 pontos")
            println("O vencedor é ${if (pontuacaoParticipante1 > pontuacaoParticipante2) nomeParticipante1 else nomeParticipante2}!")
        }
    }
}