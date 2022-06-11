package searchmethods;

import agent.Problem;
import agent.Solution;
import agent.State;

import java.util.List;

public class IterativeDeepeningSearch extends DepthFirstSearch {
    /*
     * We do not use the code from DepthLimitedSearch because we can optimize
     * so that the algorithm only verifies if a state is a goal if its depth is
     * equal to the limit. Note that given a limit X we are sure not to
     * encounter a solution below this limit because a (failed) limited depth
     * search has already been done. That's why we do not extend this class from
     * DepthLimitedSearch. We extend from DepthFirstSearch so that we don't need
     * to rewrite method insertSuccessorsInFrontier again.
     * After the class, please see a version of the search algorithm without
     * this optimization.
     */

    private int limit;

    @Override
    public Solution search(Problem problem) {
        statistics.reset();
        stopped = false;
        limit = 0;
        int limitDuration = 1000;

        //TODO
        // procura limitada ate nivel 0
        //      se encontrar solucao devolve a solucao
        //      se nao encontrar colucao faz pesquisa limitada ate nivel 1
        //          se encontrar solucao devolve solucao
        // etc

        Solution solution = null;

        long start = System.currentTimeMillis();
        long current = System.currentTimeMillis();

        while(solution == null && current-start < limitDuration) {
            solution = graphSearch(problem);
            limit++;
            current = System.currentTimeMillis();
        }

        return solution;
        /*        do {
            solution = graphSearch(problem);
            limit++;

        }while(solution == null);*/
    }

    @Override
    protected Solution graphSearch(Problem problem) {

        //TODO
        //  only check if a node is the solution if at limit depth
        //  only expand if node is below limit depth

        Node node = new Node(problem.getInitialState());
        frontier.clear();
        frontier.addFirst(node);

        while (!frontier.isEmpty() && !stopped){
            Node fronteirNode = frontier.removeFirst();

            if (fronteirNode.getDepth() == limit){
                if (problem.isGoal(fronteirNode.getState())){
                    return new Solution(problem, fronteirNode);
                }

               return null;
            }

            List<State> successors = problem.executeActions(fronteirNode.getState());
            addSuccessorsToFrontier(successors, fronteirNode);
            computeStatistics(successors.size());

        }

        return null;
    }

    @Override
    public String toString() {
        return "Iterative deepening search";
    }
}


/*
 * 
 public class IterativeDeepeningSearch implements SearchMethod {

    @Override
    public Solution search(Problem problem) {
        DepthLimitedSearch dls = new DepthLimitedSearch();
        Solution solution;
        for (int i = 0;; i++) {
            dls.setLimit(i);
            solution = dls.search(problem);
            if (solution != null) {
                return solution;
            }
        }
    }

    @Override
    public String toString() {
        return "Iterative deepening search";
    }
 *
 */