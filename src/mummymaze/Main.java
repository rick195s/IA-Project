package mummymaze;

import showSolution.SolutionPanel;

import java.util.LinkedList;
import java.util.List;

public class Main {

	public static void main(String[] args) {

		List<String> states = new LinkedList<>();


		String state = 	"             \n" +
						" . . . . .|. \n" +
						"     -       \n" +
						" . . . H . . \n" +
						"     -       \n" +
						" . . . .|. . \n" +
						"       -   - \n" +
						" . . . . .|. \n" +
						"   - -       \n" +
						" . . . M . . \n" +
						"         -   \n" +
						" . . . . . . \n" +
						"     S       \n";

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

		mummyMazeState.humanInExit();
		System.out.println(state);
		SolutionPanel.showState(state);

		//SolutionPanel.showSolution(states,7);

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
		SolutionPanel.showState(state);

		 */
	}

}
