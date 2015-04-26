package quiz.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This is a service for creating and storing User data on the server.
 * 
 * @author Kieren Millar
 */
public interface UserService extends Remote {
	
	/**
	 * Method for adding and storing a User on the server.
	 * 
	 * @param userName
	 * @param password
	 * @return User
	 * @throws RemoteException
	 */
	User addUser(String userName, String password) throws RemoteException;
	
	/**
	 * Method for accessing a User stored on the server.
	 * 
	 * @param userName
	 * @param password
	 * @return User
	 * @throws RemoteException
	 */
	User checkUser(String userName, String password) throws RemoteException;
	
	/**
	 * Method for saving data to disk.
	 * 
	 * @throws RemoteException
	 */
	void saveData() throws RemoteException;
	
}
