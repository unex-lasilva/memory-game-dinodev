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

    return readLine()?.toIntOrNull() ?: 1
}

fun mostrarPontuacao() {
    println("\nPontuação do último jogo:")
    println("Participante 1: 0 pontos") // Exemplo de pontuação, você pode adicionar a lógica para gerenciar isso
    println("Participante 2: 0 pontos") // Exemplo de pontuação, você pode adicionar a lógica para gerenciar isso
}

fun exibirRegras() {
    println("\nRegras do Jogo:")
    println("1. O objetivo do jogo é fazer o maior número de pares de cartas e pontos.")
    println("2. As cartas possuem cores e valores, e os jogadores devem tentar encontrar pares de cartas com a mesma cor ou valor.")
    println("3. A cada turno, o jogador escolhe duas cartas para virar.")
    println("4. Se as cartas viradas forem iguais, o jogador pontua.")
    println("5. O jogo termina quando todas as cartas forem viradas e todos os pares forem encontrados.")
    println("6. Aquele que fizer mais pontos vence o jogo.")
    println("Após isso, você poderá iniciar um novo jogo ou consultar a pontuação.")

    // Pedido para digitar "sair" para voltar ao menu
    println("\nDigite 'sair' para voltar ao menu principal.")
    var comando = readLine()?.lowercase()
    while (comando != "sair") {
        println("Comando inválido. Por favor, digite 'sair' para voltar ao menu.")
        comando = readLine()?.lowercase()
    }
}

fun main() {
    while (true) {
        // Exibindo o menu inicial
        val opcao = exibirMenu()

        when (opcao) {
            1 -> {
                // Escolher o tamanho do tabuleiro
                val opcaoTabuleiro = escolherTabuleiro()

                // Aqui você pode configurar o tabuleiro de acordo com a opção escolhida, sem a parte de geração das cartas
                println("Tabuleiro $opcaoTabuleiro escolhido. Agora você pode jogar!")
                // Lógica de jogo pode ser implementada aqui após a escolha do tabuleiro
            }

            2 -> {
                // Exibir a pontuação dos participantes no último jogo
                mostrarPontuacao()
            }

            3 -> {
                // Exibir as regras do jogo
                exibirRegras()
                // Após exibir as regras, retorna ao menu principal
            }

            4 -> {
                // Sair do jogo
                println("Saindo do jogo. Até logo!")
                break
            }

            else -> {
                println("Opção inválida.")
            }
        }
    }
}
