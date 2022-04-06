package searchmethods;

import agent.State;
import java.util.List;

public class AStarSearch extends InformedSearch {

    //f = g + h
    @Override
    public void addSuccessorsToFrontier(List<State> successors, Node parent) {

        //TODO
        for (State successor : successors) {
            double g = parent.getG() + successor.getAction().getCost();
            double h = heuristic.compute(successor);

            if (!frontier.containsState(successor)){
                if (!explored.contains(successor)){
                    frontier.add(new Node(successor,parent, g, g+h));

                }
            }else {
                if(frontier.getNode(successor).getG() > g){
                    frontier.removeNode(successor);
                    frontier.add(new Node(successor,parent, g, g+h));
                }
            }

        }

    }

    @Override
    public String toString() {
        return "A* search";
    }
}
