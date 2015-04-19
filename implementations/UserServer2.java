package quiz.implementations;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import quiz.interfaces.User;
import quiz.interfaces.UserService2;

public class UserServer2 extends UnicastRemoteObject implements UserService2 {

	private static final long serialVersionUID = -4235598071289639752L;
	private static List<User> userList = new ArrayList<User>();
	private static final File file = new File("userServer.txt");

	public UserServer2() throws RemoteException {
		super();
		if (file.exists()){
			try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))){
				userList = (ArrayList<User>) in.readObject();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public User addUser(String userName, String password) throws RemoteException {
		checkNull(userName, password);
		for (User u : userList){
			if (userName.equals(u.getName())){
				throw new IllegalArgumentException("User with this name already exists");
			}
		}
		User user = new UserImpl(userName, password);
		userList.add(user);
		return user;
	}

	@Override
	public User checkUser(String userName, String password) throws RemoteException {
		checkNull(userName, password);
		for (User u : userList){
			if (u.getName().equals(userName)){
				if (u.getPassword().equals(password)){
					return u;
				}else{
					throw new IllegalArgumentException("invalid password");
				}
			}
		}
		throw new IllegalArgumentException("user does not exist");
	}
	
	private void checkNull(String... arguments) throws RemoteException{
		for (String s : arguments){
			if (s.equals(null) || s.equals("")){
				throw new IllegalArgumentException("invalid username/password");
			}
		}
	}
	
	@Override
	public void saveData(){
		if (file.exists()){
			try{
				file.delete();
			} catch (Exception e) {
				e.printStackTrace();
	        }
		}
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))){
			out.writeObject(userList);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
