package searchmethods;

import agent.Problem;
import agent.Solution;
import agent.State;
import java.util.List;
import utils.NodeLinkedList;

public class DepthFirstSearch extends GraphSearch<NodeLinkedList> {

    public DepthFirstSearch() {
        frontier = new NodeLinkedList();
    }

    //Graph Search without explored list
    @Override
    protected Solution graphSearch(Problem problem) {

        //TODO
        Node node = new Node(problem.getInitialState());
        frontier.clear();
        frontier.addFirst(node);

        while (!frontier.isEmpty() && !stopped){
            Node fronteirNode = frontier.removeFirst();

            if (problem.isGoal(fronteirNode.getState())){
                return new Solution(problem, fronteirNode);
            }

            List<State> successors = problem.executeActions(fronteirNode.getState());
            addSuccessorsToFrontier(successors, fronteirNode);
            computeStatistics(successors.size());

        }

        return null;
    }

    @Override
    public void addSuccessorsToFrontier(List<State> successors, Node parent) {

        //TODO
        // para cada no sucessor
        //      se existir um no já explorado (isCycle, nós pai, avo, etc) com um estado igual ao estado "sucessor"
        //                  nao o adicionamos à fronteira
        //      se o no nao estiver na fronteira
        //              adicionar ao INICIO da fronteira

        for (State successor : successors ) {
            if (!frontier.containsState(successor)) {
                // unica diferença do breath first search para o depth first search
                if(!parent.isCycle(successor)) {
                    frontier.addFirst(new Node(successor, parent));
                }
            }
        }

    }

    @Override
    public String toString() {
        return "Depth first search";
    }
}
