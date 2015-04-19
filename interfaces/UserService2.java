package quiz.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserService2 extends Remote {
	
	User addUser(String userName, String password) throws RemoteException;
	
	User checkUser(String userName, String password) throws RemoteException;
	
	void saveData() throws RemoteException;
	
}
