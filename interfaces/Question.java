package quiz.interfaces;

import java.util.ArrayList;

/**
 * A question is stored in a Quiz and is used to store a string(the question to be asked),
 * a list of possible answers and an integer to correspond to the correct answer.
 * The implementation uses the Answer interface (for the answers), the number of  answers 
 * allowed is controlled by the main class that uses this. The id for the answers also needs
 * be implemented from here.
 * 
 * @author Kieren Millar
 */
public interface Question {

	/**
	 * Method to access the question.
	 * 
	 * @return String - the question
	 */
	String getName();
	
	/**
	 * Method for accesing the Answers.
	 * 
	 * @return List - list of Answers
	 */
	ArrayList<Answer> getPossibleAnswers();
	
	/**
	 * Method to access the id of the correct answer.
	 * 
	 * @return int - corresponds to the id of the correct answer.
	 */
	int getTrueAnswer();
	
	/**
	 * Method to add an Answer to the list of possible answers.
	 * The id is added within the implementation.
	 * 
	 * @param String - a possible answer.
	 */
	void addPossibleAnswer(String s);
	
	/**
	 * Method for storing the id of the correct answer.
	 * 
	 * @param int - id of correct answer
	 */
	void setTrueAnswer(int answer);
	
}
