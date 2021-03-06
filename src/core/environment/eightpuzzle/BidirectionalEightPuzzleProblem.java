package core.environment.eightpuzzle;

import core.agent.Action;
import core.search.framework.problem.BidirectionalProblem;
import core.search.framework.problem.GeneralProblem;
import core.search.framework.problem.GoalTest;
import core.search.framework.problem.Problem;

/**
 * @author Ruediger Lunde
 * 
 */
public class BidirectionalEightPuzzleProblem extends GeneralProblem<EightPuzzleBoard, Action>
		implements BidirectionalProblem<EightPuzzleBoard, Action> {

	private final Problem<EightPuzzleBoard, Action> reverseProblem;

	public BidirectionalEightPuzzleProblem(EightPuzzleBoard initialState) {
		super(initialState, EightPuzzleFunctions::getActions, EightPuzzleFunctions::getResult,
				GoalTest.isEqual(EightPuzzleFunctions.GOAL_STATE));

		reverseProblem = new GeneralProblem<>(EightPuzzleFunctions.GOAL_STATE,
				EightPuzzleFunctions::getActions, EightPuzzleFunctions::getResult,
				GoalTest.isEqual(initialState));
	}

	public Problem<EightPuzzleBoard, Action> getOriginalProblem() {
		return this;
	}

	public Problem<EightPuzzleBoard, Action> getReverseProblem() {
		return reverseProblem;
	}
}
