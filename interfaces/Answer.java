package quiz.interfaces;

import java.rmi.Remote;

/**
 * An Answer stores strings and an id (for comparing to correct answer).
 * The class depends on the Question interface for its id.
 * 
 * @author Kieren Millar
 *
 */
public interface Answer extends Remote {

	/**
	 * Method for accessing the answers id.
	 * @return the answer id
	 */
	int getAnswerId();
	
	/**
	 * Method for accessing the answer (a String).
	 * @return the answer as a string
	 */
	String getAnswer();
	
}
