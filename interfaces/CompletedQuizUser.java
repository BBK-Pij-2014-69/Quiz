package quiz.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CompletedQuizUser extends Remote {

	User getUser() throws RemoteException;
	
	int getScore() throws RemoteException;
	
	long getTime() throws RemoteException;
}
