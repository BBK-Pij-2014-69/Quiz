package quiz.implementations;

import java.io.Serializable;
import java.rmi.RemoteException;

import quiz.interfaces.CompletedQuizUser;
import quiz.interfaces.User;

/**
 * @see CompletedQuizUser
 * @author Kieren Millar
 *
 */
public class CompletedQuizUserImpl implements CompletedQuizUser, Serializable {

	private static final long serialVersionUID = 6815463083445351700L;
	private User user;
	private int score;
	private long time;
	
	/**
	 * Constructor accepting the user, their score for a particular
	 * quiz and the time taken for that quiz.
	 * 
	 * @param user
	 * @param score
	 * @param time
	 */
	public CompletedQuizUserImpl(User user, int score, long time){
		this.user = user;
		this.score = score;
		this.time = time;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User getUser() throws RemoteException {
		return user;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getScore() throws RemoteException {
		return score;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long getTime() throws RemoteException {
		return time;
	}

}
