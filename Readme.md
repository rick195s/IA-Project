
iterative deepening search com tempo limite ao fazer estatisticas

O que esta a fazer:

	Atraves da funcao heroInExit() ele consegue perceber quando é que está na saida ou nao (logo percebe se um estado é bom ou mau);
	escolhe estados em que o heroi nao morreu;
	consegue apanhar chave para abrir porta;
	consegue ir para a saida com paredes à volta;
    O inimigo que se mexe é que mata;
	
mostrar os enimigos a mexerem-se:

    para vermos os inimigos a mexer a unica coisa que foi preciso fazer foi dar firePuzzleChange ou updateGUI quando o heroi se mexe;
    variavel "moved" adicionada à classe Being porque a GUI so vai ser atualizada se o being se mexer;

a mumia nao esta a desaparecer no nivel 1 quando uma mumia fica em cima dela porque nao estamos a remover a mumia da lista de inimigos do estado

o inimigo que se mexer para cima de outro inimigo é que morre

o inimigo que se mexe primeiro é o que esta mais perto do heroi ou simplesmente pela ordem que foram inseridos na lista ligada?

Heuristicas:
    
    se a mumia for branca move nas laterais e se for vermelha mover para as horizontais;
    Distancia aos inimigos + distancia á saida;
    numero de paredes que o inimigo tem à sua volta;
	inimigos têm parede no mesmo sitio que a saida;
	menor numero de inimigos possiveis;
    manhathan distance;
    
    ver se o inimigos tem para parede na mesma direcao que a saida

    Atraves da funcao heorInExit conseguimos criar outra heuristica

    quanto maior a distancia do heroi aos inimigos melhor (nao admissivel nivel 11)

    quanto menor a distancia entre inimigos melhor (mais probabilidade de se matarem) (heuristica nao admisivel nivel 11)

Mandar estatisticas das heuristicas para excel:

    Qual a melhor heuristica para cada nivel;

    Qual a pior heuristica para cada nivel;

    Qual a melhor heuristica em todos os niveis (pode ser ma num nivel mas boa em maior parte dos niveis)

    Verificar se as heuristicas sao admissiveis
