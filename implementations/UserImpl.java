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
	
	/**
	 * Overridden equals method for comparing two Users,
	 * only returns true if the id, name, and notes are equal.
	 * 
	 */
	@Override
	public boolean equals(Object o){
		if (o == this) return false;
		if (!(o instanceof UserImpl)) return false;
		UserImpl c = (UserImpl)o;
		try {
			if (this.getName().equals(c.getName()) && this.getPassword().equals(c.getPassword()))
				return true;
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Overridden hashcode method using id, name and notes as parameters
	 * to produce the hashcode. 
	 */
	@Override
    public int hashCode() {
		int hash = 5;
		hash = 83 * hash + name.hashCode();
		hash = 83 * hash + password.hashCode();
		return hash;
	}

}
