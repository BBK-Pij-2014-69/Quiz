package quiz.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * A completedQuizUser is used for storing a users attempt at a quiz and they are stored
 * within a quiz.
 * 
 * This stores the User(the user who took the quiz), their score and the time they took to 
 * complete the quiz.
 * 
 * @author Kieren Millar
 *
 */
public interface CompletedQuizUser extends Remote {

	/**
	 * Method for accessing the user who completed the quiz.
	 * @return User
	 * @throws RemoteException
	 */
	User getUser() throws RemoteException;
	
	/**
	 * Method for accessing the user's score.
	 * @return int (the score)
	 * @throws RemoteException
	 */
	int getScore() throws RemoteException;
	
	/**
	 * Method for accessing the time it took for the user to complete the quiz.
	 * @return long (time taken for completion)
	 * @throws RemoteException
	 */
	long getTime() throws RemoteException;
}
