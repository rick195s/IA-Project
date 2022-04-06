package searchmethods;

import agent.State;
import java.util.List;
import utils.NodePriorityQueue;

public class UniformCostSearch extends GraphSearch<NodePriorityQueue> {

    public UniformCostSearch(){
        frontier = new NodePriorityQueue();
    }    
    
    // f = g
    @Override
    public void addSuccessorsToFrontier(List<State> successors, Node parent) {

        //TODO
        // para cada no sucessor
        //      obter custo do no
        //          se o no nao estiver na fronteira
        //              se nao estiver nos explorados
        //                  adicionar à fronteira com f=g
        //          se o no tiver na fronteir
        //              se este no tem menor custo do que o que está na fronteira
        //                  remover o no que esta na fronteira
        //                  acrescentar o no na fronteira com f=g

        for (State successor : successors) {
            double g = parent.getG() + successor.getAction().getCost();

            if (!frontier.containsState(successor)){
                if (!explored.contains(successor)){
                    frontier.add(new Node(successor,parent, g, g));

                }
            }else {
                if(frontier.getNode(successor).getG() > g){
                    frontier.removeNode(successor);
                    frontier.add(new Node(successor,parent, g, g));
                }
            }

        }
    }

    @Override
    public String toString() {
        return "Uniform cost search";
    }
}
