package quiz.implementations;

import java.io.Serializable;
import java.rmi.RemoteException;

import quiz.interfaces.User;

public class UserImpl implements User, Serializable {
	
	private static final long serialVersionUID = -6935053537097396014L;
	private String name;
	private String password;

	public UserImpl(String name, String password){
		this.name = name;
		this.password = password;
	}

	@Override
	public String getName() throws RemoteException {
		return name;
	}

	@Override
	public String getPassword() throws RemoteException {
		return password;
	}


}
