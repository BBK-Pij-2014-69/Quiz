package quiz.implementations;

import java.io.Serializable;
import java.rmi.RemoteException;

import quiz.interfaces.CompletedQuizUser;
import quiz.interfaces.User;

public class CompletedQuizUserImpl implements CompletedQuizUser, Serializable {

	private static final long serialVersionUID = 6815463083445351700L;
	private User user;
	private int score;
	private long time;
	
	public CompletedQuizUserImpl(User user, int score, long time){
		this.user = user;
		this.score = score;
		this.time = time;
	}

	@Override
	public User getUser() throws RemoteException {
		return user;
	}

	@Override
	public int getScore() throws RemoteException {
		return score;
	}

	@Override
	public long getTime() throws RemoteException {
		return time;
	}

}
