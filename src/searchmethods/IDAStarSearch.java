package searchmethods;

import agent.Problem;
import agent.Solution;
import agent.State;
import java.util.List;

public class IDAStarSearch extends InformedSearch {
    /*
     * Note that, on each iteration, the search is done in a depth first search way.    
     */
    
    private double limit;
    private double newLimit;    

    @Override
    public Solution search(Problem problem) {
        statistics.reset();        
        stopped = false;
        this.heuristic = problem.getHeuristic();
        limit = heuristic.compute(problem.getInitialState());

        //TODO
        Solution solution = null;
        boolean stop = false;

         while (solution == null || solution.getCost() >= limit){
            solution = graphSearch(problem);
            limit = newLimit;
        }


        return solution;
    }

    @Override
    protected Solution graphSearch(Problem problem) {
        newLimit = Double.MAX_VALUE;

        frontier.clear();
        frontier.add(new Node(problem.getInitialState()));

        while (!frontier.isEmpty() && !stopped) {
            Node frontierNode = frontier.remove();

            if (problem.isGoal(frontierNode.getState())) {
                return new Solution(problem, frontierNode);
            }

            List<State> successors = problem.executeActions(frontierNode.getState());
            addSuccessorsToFrontier(successors , frontierNode);
            computeStatistics(successors.size());
        }

        return null;
    }

    @Override
    public void addSuccessorsToFrontier(List<State> successors, Node parent) {
        
        for (State est : successors) {
            double g = parent.getG() + est.getAction().getCost();
            if (!frontier.containsState(est)) {
                double f = g + heuristic.compute(est);
                if (f <= limit) {
                    if (!parent.isCycle(est)) {
                        frontier.add(new Node(est, parent, g, f));
                    }
                } else {
                    newLimit = Math.min(newLimit, f);
                }
            } else if (frontier.getNode(est).getG() > g) {
                frontier.removeNode(est);
                frontier.add(new Node(est, parent, g, g + heuristic.compute(est)));
            }
        }
    }

    @Override
    public String toString() {
        return "IDA* search";
    }
}
