#Programação Orientada a Objectos

##Descrição

Para este projecto é para assumirmos sempre que os valores de X[t+1] apenas dependem de X[t] e nao de X[t:0]

###Classe BN

Os parametros a guardar numa BN de um certo nó X são:
    - Uma tabela:
        o Valores possíveis de Xi v possiveis configurações do pai
        o Xir - Valores que a variavel X pode assumir
        o wir - compinações possiveis que o pai pode ter
        o O valor a guardar é: ø(ijk) = Nijk + N' / Nij + ri*N'
            Nijk é o número de ocorrencias do valor kth de X [Exemplo: X1 e {1, 4, 6} ou seja k=1 -> X = 1, k=2 -> X = 4, k=3 -> X = 6]
            N' = 0,5
            Nij é o número de instancias em que o pai i tem o valor j
            ri é o número de valores que X pode ter [no exemplo em cima ri = 3]

###Classe DBN

Os parametros a guardar numa DBN de um certo nó x são:
    - List <BN>
        Cada elemento da lista é uma transição que decorreu ao longo do tempo (em "modo" discreto)
    - int T
        Número de elementos na lista (a lista extende-se de 0 < t < T)

###Interface GHC

Algoritmos que fazem a transição de X[t] -> X[t+1] GHC (AKA Greedy Hill-Climbing) com TABU list / random resets
Algoritmos que gerem a função de "scoring": LL - Log Likelihood, MDL - Minimum Description Length

###In the End

Queremos que seja possível tendo X[t] dizer o valor que Xi[t] pode ter, de modo a poder inferir sobre acontecimentos futuros apenas com resultados do presente
