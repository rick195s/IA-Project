Erros:

    nivel 9, 10 e 11 sem solução;
    nivel 11 da erro de concorrencia;

O que esta a fazer:

	Atraves da funcao heroInExit() ele consegue perceber quando é que está na saida ou nao (logo percebe se um estado é bom ou mau);
	escolhe estados em que o heroi nao morreu;
	consegue apanhar chave para abrir porta;
	consegue ir para a saida com paredes à volta;
	
mostrar os enimigos a mexerem-se:

    para vermos os inimigos a mexer a unica coisa que foi preciso fazer foi dar firePuzzleChange ou updateGUI quando o heroi se mexe;
    variavel "moved" adicionada à classe Being porque a GUI so vai ser atualizada se o being se mexer;

a mumia nao esta a desaparecer no nivel 1 quando uma mumia fica em cima dela porque nao estamos a remover a mumia da lista de inimigos do estado

o inimigo que se mexer para cima de outro inimigo é que morre

<<<<<<< HEAD

Matriz Nao estatica:
    
    Para cada estado ao darmos clone do estado vamos percorrer a matriz e ver quais as posicoes onde estao as entidades. 
    E para cada estado faziamos comparações com a matriz desse estado

Matriz Estatica:
    
    1º Estado:
        Descobrimos quais as posicoes das entidades (percorrendo a matriz estatica)
        
        Sempre que quisermos comparar um estado vamos à matriz estatica, limpamos a matriz e depois colocamos as entidades na matriz e continuamos os calculos        
        



Heuristicas:
    
    se a mumia for branca move nas laterais e se for vermelha mover para as horizontais
    Distancia aos inimigos + distancia á saida