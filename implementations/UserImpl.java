package quiz.implementations;

import java.io.Serializable;
import java.rmi.RemoteException;
import quiz.interfaces.User;

/**
 * @see User
 * @author Kieren Millar
 *
 */
public class UserImpl implements User, Serializable {
	
	private static final long serialVersionUID = -6935053537097396014L;
	private String name;
	private String password;

	/**
	 * Constructor accepting a name and password.
	 * @param name
	 * @param password
	 */
	public UserImpl(String name, String password){
		this.name = name;
		this.password = password;
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public String getName() throws RemoteException {
		return name;
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public String getPassword() throws RemoteException {
		return password;
	}
	
	/**
	 * Overridden equals method for comparing two Users,
	 * only returns true if the id, and passwords are equal.
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
	 * Overridden hashcode method using name and password as parameters
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
