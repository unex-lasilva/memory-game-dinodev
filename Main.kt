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

fun exibirMenu(): Int {
    println("Manga Rosa Memory Game")
    println("1. INICIAR")
    println("2. PONTUAÇÃO PARTICIPANTES")
    println("3. REGRAS DO JOGO")
    println("4. SAIR")
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
    val cores = listOf(CartaCor.VERMELHO, CartaCor.AZUL, CartaCor.AMARELO, CartaCor.PRETO)
    val cartas = mutableListOf<Carta>()
    val totalPares = (tamanho * tamanho) / 2

    val idsPorCor = mutableMapOf<CartaCor, Int>()
    var idAtual = 0

    repeat(totalPares) {
        val cor = cores.random()
        val id = idsPorCor.getOrPut(cor) { idAtual++ }
        cartas.add(Carta(id, cor))
        cartas.add(Carta(id, cor))
    }

    cartas.shuffle()

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

fun main() {
    var pontuacaoParticipante1 = 0
    var pontuacaoParticipante2 = 0

    while (true) {

        val opcao = exibirMenu()

        when (opcao) {
            1 -> {
                println("Opção INICIAR selecionada.")


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


                var jogoAtivo = true
                var vezDoParticipante1 = true
                var cartasViradas = mutableListOf<Pair<Int, Int>>()

                while (jogoAtivo) {
                    exibirTabuleiro(tabuleiro)

                    val participanteAtual = if (vezDoParticipante1) nomeParticipante1 else nomeParticipante2
                    println("Vez de $participanteAtual")

                    print("Escolha uma linha e uma coluna (ex: 1 2): ")
                    val (linha, coluna) = readLine()?.split(" ")?.map { it.toInt() - 1 } ?: continue

                    if (linha !in 0 until tamanhoTabuleiro || coluna !in 0 until tamanhoTabuleiro) {
                        println("Posição inválida. Tente novamente.")
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

                        if (carta1.id == carta2.id) {
                            println("Par encontrado! Pontuação +1 para $participanteAtual.")
                            if (vezDoParticipante1) pontuacaoParticipante1++ else pontuacaoParticipante2++
                        } else {
                            println("Par errado. As cartas serão desviradas.")
                            carta1.virada = false
                            carta2.virada = false
                        }

                        cartasViradas.clear()
                        vezDoParticipante1 = !vezDoParticipante1
                    }
                }
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
                println("Saindo do jogo. Até logo!")
                break
            }
            else -> {
                println("Opção inválida.")
        }
    }
}

// Aguardando features restantes