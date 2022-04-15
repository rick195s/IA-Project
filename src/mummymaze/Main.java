package mummymaze;

import agent.Action;
import agent.Solution;
import showSolution.SolutionPanel;

import java.util.LinkedList;
import java.util.List;

public class Main {

	public static void main(String[] args) {

		List<String> states = new LinkedList<>();


		String state = 	" S           \n" +
						" . . . . .|. \n" +
						"     -       \n" +
						" . . . . . . \n" +
						"     -       \n" +
						" . . . .|. . \n" +
						"       -   - \n" +
						" . . . . .|. \n" +
						"   - -       \n" +
						" . . . M . . \n" +
						"         -   \n" +
						" . . H . . . \n" +
						"             \n";

		states.add(state);

		//SolutionPanel.showSolution(states, states.size());

		// transforma a string dada numa matriz facilitando os calculos das posicoes
		int i=0, j=0;
		char matrix[][] = new char[13][13];
		for (char t :  state.toCharArray()){
			if(t!='\n') {
				matrix[i][j] = t;
				j++;
			}else{
				j=0;
				i++;
			}
		}


		MummyMazeState mummyMazeState = new MummyMazeState(matrix);
		MummyMazeAgent mummyMazeAgent = new MummyMazeAgent(mummyMazeState);
		Solution solution = mummyMazeAgent.solveProblem(new MummyMazeProblem(mummyMazeState));

		for (Action action : solution.getActions()) {
			mummyMazeAgent.getEnvironment().executeAction(action);
			states.add( mummyMazeAgent.getEnvironment().toString());
		}

		for (String s : states) {
			System.out.println(s);
		}
		SolutionPanel.showSolution(states, states.size());
		// SolutionPanel.showState(state);



		/*
		String state = 	"             \n" +
				" . . . . .|. \n" +
				"     -       \n" +
				" . . . . . . \n" +
				"     -       \n" +
				" . . . .|. . \n" +
				"       -   - \n" +
				" . . . . .|M \n" +
				"   - -       \n" +
				" . . . . H . \n" +
				"         -   \n" +
				" . . . . . . \n" +
				" S           \n";

		 */
	}

}
