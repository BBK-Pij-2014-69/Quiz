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

public class SetupClientLauncher {
	public User user;
	public int currentQuizId = 0;
	public Scanner input = new Scanner(System.in);
	public String nl = System.lineSeparator();

	public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
		SetupClientLauncher scl = new SetupClientLauncher();
		scl.loadLoginMenu();
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
		boolean finished = false;
		do{
			System.out.println("What would you like to do?" + nl + 
					"0 - exit" + nl + 
					"1 - Make new quiz" + nl +
					"2 - Amend Quiz" + nl + 
					"3 - Activate Quiz" + nl +
					"4 - Close Quiz");
			switch (input.nextLine()){
			case "0" : finished = true;
				break;
			case "1" : makeNewQuiz();
				break;
			case "2" : amendQuiz();
				break;
			default : System.out.println("invalid choice");
			}
		}while(!finished);
	}

	
	private void makeNewQuiz() throws MalformedURLException, RemoteException, NotBoundException{
		QuizService2 qs = connectToQuizServer();
		boolean finished = false;
		do{
			try{
				System.out.println("Please give your quiz a title: ");
				String title = input.nextLine();
				currentQuizId = qs.createEmptyQuiz(user, title);
				finished = true;
			}catch (IllegalArgumentException e){
				System.out.println(e.getMessage());
			}
		}while (!finished);
		amendQuiz();
	}
	
	private void amendQuiz() throws MalformedURLException, RemoteException, NotBoundException{
		QuizService2 qs = connectToQuizServer();
		while (currentQuizId == 0){
			try{
				System.out.println("Please enter the id for the quiz you wish to amend: ");
				currentQuizId = qs.checkQuizandCreator(user, Integer.parseInt(input.nextLine()));
			}catch (IllegalArgumentException e){
				System.out.println(e.getMessage());
			}			
		}
		boolean finished = false;
		do{
		System.out.println("Would you like to add a new question (1) or delete a question (2)?" + nl + "0 to finish");
			switch (input.nextLine()){
				case "0": finished = true;
						break;
				case "1": addQuestion();
						break;
				case "2": deleteQuestion();
						break;
				default : System.out.println("invalid choice, please try again");
						break;
			}
		}while (!finished);
	}	
	
	private void deleteQuestion() {
		// TODO Auto-generated method stub
		
	}

	private void addQuestion() throws MalformedURLException, RemoteException, NotBoundException {
		QuizService2 qs = connectToQuizServer();
		System.out.println("What is your question: ");
		qs.addQuestionToQuiz(input.nextLine(), currentQuizId);
		int numberOfAnswersLeft = 5;
		do{
			System.out.println("Please enter " + numberOfAnswersLeft + " more answers:");
			String answer = input.nextLine();
			qs.addAnswerstoQuestions(answer, currentQuizId);
			numberOfAnswersLeft --;
		}while(numberOfAnswersLeft != 0);
		System.out.println("Which answer (1,2,3,4 or 5) was correct? ");
		qs.setActualAnswer(currentQuizId, Integer.parseInt(input.nextLine()));//catch number format exception
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