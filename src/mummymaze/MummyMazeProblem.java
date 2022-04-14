package mummymaze;

import agent.Action;
import agent.Problem;

import java.util.ArrayList;
import java.util.List;

public class MummyMazeProblem extends Problem<MummyMazeState> {

    //TODO
    /*
    Please, implement the EightPuzzleProblem class, which represents the Eight Puzzle problem and
    which extends the Problem class. In this class’s constructor, besides the initial state, you
    should also define the four actions Up, Down, Left and Right corresponding, respectively, to
    classes ActionUp, ActionDown, ActionLeft e ActionRight, already implemented (please note that
    all actions have cost 1 in this problem). You should also redefine methods executeActions(), isGoal()
    and computePathCost(). Also note that, for this problem, the goal test (isGoal() method) consists
    in comparing some state to the previously defined goal state. Therefore, you should also define a
    goal state as attribute (goalState). For simplicity, we consider that the goal state is always the
    same and that it corresponds to the goalMatrix attribute defined in the EightPuzzleState class.*/


    private MummyMazeState goalState;

    public MummyMazeProblem(MummyMazeState initialState) {
        super(initialState, new ArrayList<>(4));
        super.actions.add(new ActionUp());
        super.actions.add(new ActionRight());
        super.actions.add(new ActionDown());
        super.actions.add(new ActionLeft());
        super.actions.add(new ActionDontMove());

        goalState = new MummyMazeState(MummyMazeState.GOAL_MATRIX);
    }

    // devole a lista de estados sucessores ( estados para onde é possivel transitar a partir
    // daquele qye é passado por argumento)
    @Override
    public List<MummyMazeState> executeActions(MummyMazeState state) {
        // para cada acao disponivel
        //      se a acao for valida
        //          criar um novo estado sucessor (igual ao original)
        //          executar a acao sobre o novo estado
        //          adicionar o novo estado à lista de sucessores
        // devolver a lista de estados sucessores

        ArrayList<MummyMazeState> sucessors = new ArrayList<>(4) ;

        for (Action action:
             actions) {

            if (action.isValid(state)){
                MummyMazeState sucessorState = (MummyMazeState) state.clone();
                sucessorState.executeAction(action);
                sucessors.add(sucessorState);
            }

        }

        return sucessors;
    }

    @Override
    public boolean isGoal(MummyMazeState state) {
        // precisamos de aceder ao finalState esperado para verificar se
        // o state é igual ao finalState
        // neste caso é nos dito que o finalState é o GOAL_MATRIX que está dentro
        // do objeto state
        return state.heroInExit();
    }

    @Override
    protected double computePathCost(List<Action> path) {
        return path.size(); // porque as ações no eightpuzzleproblem têm todas o mesmo custo, custo 1
    }

    public MummyMazeState getGoalState(){
        return goalState;
    }
}
