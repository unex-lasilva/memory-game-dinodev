import kotlin.random.Random

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
    while (true) {
        println("\nEscolha o tamanho do tabuleiro:")
        println("1. 4x4")
        println("2. 6x6")
        println("3. 8x8")
        println("4. 10x10")
        print("Digite a opção: ")

        val opcao = readLine()?.toIntOrNull()

        if (opcao in 1..4) {
            return when (opcao) {
                1 -> 4
                2 -> 6
                3 -> 8
                4 -> 10
                else -> 4 // Nunca será alcançado devido à verificação acima
            }
        } else {
            println("Por favor, escolha uma das opções existentes (1 a 4).")
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
    while (true) {
        println("QUAL A COR DO PARTICIPANTE $participante? (Vermelho ou Azul)")
        print("DIGITE A COR: ")

        when (readLine()?.lowercase()) {
            "vermelho" -> return Cor.VERMELHO
            "azul" -> return Cor.AZUL
            else -> println("Por favor, escolha uma cor válida (Vermelho ou Azul).")
        }
    }
}

fun criarTabuleiro(tamanho: Int): List<List<Carta>> {
    val totalPares = (tamanho * tamanho) / 2


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


    val cartas = mutableListOf<Carta>()
    var idAtual = 0


    repeat(paresAzulVermelho) {
        cartas.add(Carta(idAtual, CartaCor.AZUL))
        cartas.add(Carta(idAtual, CartaCor.VERMELHO))
        idAtual++
    }


    repeat(paresPreto) {
        cartas.add(Carta(idAtual, CartaCor.PRETO))
        cartas.add(Carta(idAtual, CartaCor.PRETO))
        idAtual++
    }


    repeat(paresAmarelo) {
        cartas.add(Carta(idAtual, CartaCor.AMARELO))
        cartas.add(Carta(idAtual, CartaCor.AMARELO))
        idAtual++
    }


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

fun escolherCarta(tabuleiro: List<List<Carta>>, tamanhoTabuleiro: Int): Pair<Int, Int>? {
    var tentativas = 0
    while (tentativas < 3) {
        print("Escolha uma linha e uma coluna (ex: 1 2): ")
        val entrada = readLine()

        if (entrada.isNullOrBlank() || entrada.split(" ").size < 2) {
            println("Entrada inválida. Por favor, digite uma linha e coluna (exemplo: 1 2). Tentativas restantes: ${2 - tentativas}")
            tentativas++
            continue
        }

        val partes = entrada.split(" ")
        val linha = partes[0].toIntOrNull()?.minus(1) ?: -1
        val coluna = partes[1].toIntOrNull()?.minus(1) ?: -1

        if (linha !in 0 until tamanhoTabuleiro || coluna !in 0 until tamanhoTabuleiro) {
            println("Posição inválida. Tente novamente. Tentativas restantes: ${2 - tentativas}")
            tentativas++
            continue
        }

        val carta = tabuleiro[linha][coluna]
        if (carta.virada) {
            println("A carta da posição informada já está virada, por favor, escolha outra posição. Tentativas restantes: ${2 - tentativas}")
            tentativas++
        } else {
            return Pair(linha, coluna)
        }
    }

    println("Você errou 3 vezes e perdeu a vez.")
    return null
}

fun verificarPontosCorParticipante(carta1: Carta, carta2: Carta, corParticipante: Cor): Pair<Int, Boolean> {
    return if (carta1.cor == CartaCor.valueOf(corParticipante.name) && carta2.cor == CartaCor.valueOf(corParticipante.name)) {
        Pair(5, true)
    } else {
        Pair(0, false)
    }
}

fun verificarPontosCorAdversario(carta1: Carta, carta2: Carta, corAdversario: Cor): Pair<Int, Boolean> {
    return if (carta1.cor == CartaCor.valueOf(corAdversario.name) && carta2.cor == CartaCor.valueOf(corAdversario.name)) {
        Pair(1, true)
    } else {
        Pair(0, false)
    }
}

fun verificarErroCorAdversario(carta1: Carta, carta2: Carta, corAdversario: Cor): Pair<Int, Boolean> {
    return if ((carta1.cor == CartaCor.valueOf(corAdversario.name) || carta2.cor == CartaCor.valueOf(corAdversario.name))) {
        if (carta1.id != carta2.id) {
            Pair(-2, false)
        } else {
            Pair(0, false)
        }
    } else {
        Pair(0, false)
    }
}

fun verificarPontos(
    carta1: Carta,
    carta2: Carta,
    corParticipante1: Cor,
    corParticipante2: Cor,
    vezDoParticipante1: Boolean,
    pontuacaoParticipante1: Int,
    pontuacaoParticipante2: Int
): Pair<Int, Boolean> {
    val corParticipante = if (vezDoParticipante1) corParticipante1 else corParticipante2
    val corAdversario = if (vezDoParticipante1) corParticipante2 else corParticipante1


    if (carta1.cor == CartaCor.AMARELO && carta2.cor == CartaCor.AMARELO) {
        return Pair(1, true)
    }


    val pontosCorParticipante = verificarPontosCorParticipante(carta1, carta2, corParticipante)
    if (pontosCorParticipante.first > 0) {
        return pontosCorParticipante
    }


    val pontosCorAdversario = verificarPontosCorAdversario(carta1, carta2, corAdversario)
    if (pontosCorAdversario.first > 0) {
        return pontosCorAdversario
    }


    val erroCorAdversario = verificarErroCorAdversario(carta1, carta2, corAdversario)
    if (erroCorAdversario.first < 0) {
        return erroCorAdversario
    }


    if (carta1.cor == CartaCor.PRETO && carta2.cor == CartaCor.PRETO) {
        return Pair(50, true)
    }


    if ((carta1.cor == CartaCor.PRETO || carta2.cor == CartaCor.PRETO) && carta1.id != carta2.id) {
        if (vezDoParticipante1) {
            if (pontuacaoParticipante1 < 50) {
                println("Você errou e perderá seus pontos!")
                return Pair(-pontuacaoParticipante1, false)
            } else {
                println("Você acaba de perder 50 pontos!")
                return Pair(-50, false)
            }
        } else {
            if (pontuacaoParticipante2 < 50) {
                println("Você errou e perderá seus pontos!")
                return Pair(-pontuacaoParticipante2, false)
            } else {
                println("Você acaba de perder 50 pontos!")
                return Pair(-50, false)
            }
        }
    }

    // Outros casos: sem pontuação
    return Pair(0, false)
}

fun main() {
    var pontuacaoParticipante1 = 0
    var pontuacaoParticipante2 = 0
    var jogoTerminou = false

    while (true) {
        val opcao = exibirMenu(jogoTerminou)

        when (opcao) {
            1 -> {
                println("Opção INICIAR selecionada.")
                iniciarJogo()
                jogoTerminou = true
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
                    jogoTerminou = true
                } else {
                    println("Opção inválida. O jogo ainda não terminou.")
                }
            }
            0 -> {
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


        if (tabuleiro.flatten().all { it.virada }) {
            jogoAtivo = false
            println("\nJogo terminado!")
            println("Pontuação final:")
            println("$nomeParticipante1: $pontuacaoParticipante1 pontos")
            println("$nomeParticipante2: $pontuacaoParticipante2 pontos")
            println("O vencedor é ${if (pontuacaoParticipante1 > pontuacaoParticipante2) nomeParticipante1 else nomeParticipante2}!")

            var opcaoValida = false
            while (!opcaoValida) {
                println("\nDeseja jogar novamente?")
                println("1. Sim")
                println("2. Não")
                print("Escolha uma opção: ")

                when (readLine()?.toIntOrNull()) {
                    1 -> {
                        println("Reiniciando o jogo...")
                        iniciarJogo()
                        return
                    }
                    2 -> {
                        println("Obrigado por jogar! Até logo!")
                        return
                    }
                    else -> {
                        println("Opção inválida. Por favor, escolha 1 para Sim ou 2 para Não.")
                    }
                }
            }
        }

        val participanteAtual = if (vezDoParticipante1) nomeParticipante1 else nomeParticipante2
        println("Vez de $participanteAtual")


        val posicaoCarta1 = escolherCarta(tabuleiro, tamanhoTabuleiro)
        if (posicaoCarta1 == null) {
            vezDoParticipante1 = !vezDoParticipante1
            continue
        }

        val (linha1, coluna1) = posicaoCarta1
        val carta1 = tabuleiro[linha1][coluna1]
        carta1.virada = true
        exibirTabuleiro(tabuleiro)


        val posicaoCarta2 = escolherCarta(tabuleiro, tamanhoTabuleiro)
        if (posicaoCarta2 == null) {
            carta1.virada = false
            vezDoParticipante1 = !vezDoParticipante1
            continue
        }

        val (linha2, coluna2) = posicaoCarta2
        val carta2 = tabuleiro[linha2][coluna2]
        carta2.virada = true
        exibirTabuleiro(tabuleiro)


        val (pontos, continuarVez) = verificarPontos(
            carta1,
            carta2,
            corParticipante1,
            corParticipante2,
            vezDoParticipante1,
            pontuacaoParticipante1,
            pontuacaoParticipante2
        )

        if (pontos > 0) {
            println("Par encontrado! Pontuação +$pontos para $participanteAtual.")
            if (vezDoParticipante1) pontuacaoParticipante1 += pontos else pontuacaoParticipante2 += pontos
        } else if (pontos < 0) {
            println("Par errado. Você perdeu ${-pontos} pontos!")
            if (vezDoParticipante1) {
                pontuacaoParticipante1 = maxOf(0, pontuacaoParticipante1 + pontos)
            } else {
                pontuacaoParticipante2 = maxOf(0, pontuacaoParticipante2 + pontos)
            }
        } else {
            println("Nenhum par encontrado. Segue o jogo.")
        }


        println("\nPontuação atual:")
        println("$nomeParticipante1: $pontuacaoParticipante1 pontos")
        println("$nomeParticipante2: $pontuacaoParticipante2 pontos")


        if (continuarVez) {
            println("Você encontrou um par! Continue jogando.")
            cartasViradas.clear()
            continue
        }


        if (!continuarVez) {
            vezDoParticipante1 = !vezDoParticipante1
        }


        if (carta1.id != carta2.id) {
            carta1.virada = false
            carta2.virada = false
            exibirTabuleiro(tabuleiro)
        }

        cartasViradas.clear()
    }
}