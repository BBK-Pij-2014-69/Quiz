package quiz.serverLaunchers;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Scanner;

import quiz.interfaces.QuizService2;
import quiz.interfaces.User;
import quiz.interfaces.UserService2;

public class PlayerClientLauncher {

	public Scanner input = new Scanner(System.in);
	public String nl = System.lineSeparator();
	public User user;
	
	public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
		PlayerClientLauncher pcl = new PlayerClientLauncher();
		pcl.loadLoginMenu();

	}
	
	private void loadLoginMenu() throws RemoteException, MalformedURLException, NotBoundException {
		System.out.println("To login please indicate if you are a new user (1) a returning user (2)");
		boolean finished = false;
		do{
			switch (input.nextLine()){
				case "1": createNewUser(); finished = true;
						break;
				case "2": loginUser(); finished = true;
						break;
				default : System.out.println("invalid choice, please try again");
						break;
			}
		}while (!finished);
		loadMainMenu();
	}
	
	private void loadMainMenu() throws MalformedURLException, RemoteException, NotBoundException {
		QuizService2 qs = connectToQuizServer();
		System.out.println("Welcome. Please select a quiz to play (1, 2, 3..." + nl);
		int quizListSize = qs.getQuizs().size();
		if (quizListSize > 0){
			for (int i = 0; i < quizListSize; i++){
				System.out.println(qs.getQuizTitles(i));
			}
		}else{
			System.out.println("There are currently no active quizes");
			System.exit(0);
		}
	}

	private void loginUser() throws RemoteException, MalformedURLException, NotBoundException {
		UserService2 us = conectToUserServer();
		boolean finished = false;
		do{
			try{
				System.out.println("Please enter a user name: ");
				String userName = input.nextLine();
				System.out.println("please enter a password:");
				String password = input.nextLine();
				user = us.checkUser(userName, password);
				finished = true;
			}catch (IllegalArgumentException e){
				System.out.println(e.getMessage());
			}
		}while (!finished);
	}
	
	private void createNewUser() throws RemoteException, MalformedURLException, NotBoundException {
		UserService2 us = conectToUserServer();
		boolean finished = false;
		do{
			try{
				System.out.println("Please enter a user name: ");
				String userName = input.nextLine();
				System.out.println("please enter a password:");
				String password = input.nextLine();
				user = us.addUser(userName, password);
				finished = true;
			}catch (IllegalArgumentException e){
				System.out.println(e.getMessage());
			}
		}while (!finished);
	}

	private UserService2 conectToUserServer() throws RemoteException, NotBoundException, MalformedURLException{
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		Remote service = Naming.lookup("//127.0.0.1:1099/user");
		return (UserService2) service;
	}
	
	private QuizService2 connectToQuizServer() throws MalformedURLException, RemoteException, NotBoundException {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		Remote service = Naming.lookup("//127.0.0.1:1099/quiz");
		return (QuizService2) service;
	}
}
