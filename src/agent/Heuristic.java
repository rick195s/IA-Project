package agent;


public abstract class Heuristic <P extends Problem, S extends State>{
    
    protected P problem;
    protected boolean admissivel;

    public Heuristic(){
        admissivel = true;
    }    
    
    public Heuristic(P problem){
        this.problem = problem;
    }
    
    public abstract double compute(S state);
    
    public P getProblem() {
        return problem;
    }

    public void setProblem(P problem) {
        this.problem = problem;
    }

    public void setAdmissivel(boolean admissivel) {
        this.admissivel = admissivel;
    }

    public boolean isAdmissivel() {
        return admissivel;
    }
}