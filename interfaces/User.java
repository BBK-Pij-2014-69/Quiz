package quiz.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 * A User is someone who can log onto the server and either create 
 * or participate in quiz's.
 * 
 * A user has a unique name and a password(not necessarily unique).
 * 
 * @author Kieren Millar
 */
public interface User extends Remote {

	/**
	 * Returns the name of the user.
	 * 
	 * @return the users name
	 * @throws RemoteException
	 */
	String getName() throws RemoteException;
	
	/**
	 * Returns the password of the user.
	 * 
	 * @return the users password
	 * @throws RemoteException
	 */
	String getPassword() throws RemoteException;
	
}
