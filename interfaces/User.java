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
	 * @throws RemoteException or IllegalArgumentException if a user with 
	 * that name already exists.
	 */
	String getName() throws RemoteException;
	
	/**
	 * Returns the password of the user.
	 * 
	 * @return the users password
	 * @throws RemoteException or IllegalArgumentException if an invalid
	 * password for the specific user is used.
	 */
	String getPassword() throws RemoteException;
	
}
