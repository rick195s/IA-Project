package searchmethods;

import agent.State;
import java.util.List;

public class DepthLimitedSearch extends DepthFirstSearch {

    private int limit;

    public DepthLimitedSearch() {
        this(28);
    }

    public DepthLimitedSearch(int limit) {
        this.limit = limit;
    }

    @Override
    public void addSuccessorsToFrontier(List<State> successors, Node parent) {

        //TODO
        // para cada no sucessor
        //      se existir um no já explorado (isCycle, nós pai, avo, etc) com um estado igual ao estado "sucessor"
        //                  nao o adicionamos à fronteira
        //      se o no nao estiver na fronteira
        //              adicionar ao INICIO da fronteira

        if (parent.getDepth() < limit){
            super.addSuccessorsToFrontier(successors, parent);
        }
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public String toString() {
        return "Limited depth first search";
    }
}
