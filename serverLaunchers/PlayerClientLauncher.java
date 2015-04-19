package quiz.serverLaunchers;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalTime;
import java.util.Scanner;

import quiz.interfaces.Answer;
import quiz.interfaces.Question;
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
	
	private void loadMainMenu() throws MalformedURLException, RemoteException, NotBoundException{
		boolean finished = false;
		do{
			System.out.println("Welcome, what would you like to do:" + nl +
					"1. Play Quizes" + nl + 
					"0. LogOut");
			switch (input.nextLine()){
			case "0" : finished = true;
				break;
			case "1" : loadQuizMenu();
				break;
			default : System.out.println("invalid choice");
				break;
			}
		}while(!finished);
		System.exit(0);
	}
	
	private void loadQuizMenu() throws MalformedURLException, RemoteException, NotBoundException {
		QuizService2 qs = connectToQuizServer();
		System.out.println("Please select a quiz to play (1, 2, 3..." + nl);
		int quizListSize = qs.getQuizs().size();
		if (quizListSize > 0){
			for (int i = 0; i < quizListSize; i++){
				System.out.println(qs.getQuizTitles(i));
			}
		}else{
			System.out.println("There are currently no active quizes");
			System.exit(0);
		}
		int quizToPlay = 0;
		do{
			try{
				quizToPlay = qs.checkIfActiveQuiz(input.nextLine());
			}catch (IllegalArgumentException e){
			System.out.println(e.getMessage());
			}
		}while (quizToPlay == 0);
		playQuiz(quizToPlay);
	}

	private void playQuiz(int quizId) throws MalformedURLException, RemoteException, NotBoundException {
		QuizService2 qs = connectToQuizServer();
		int score = 0;
		int numberOfQuestions = qs.getNumberOfQuestions(quizId, "activeQuizList");
		int time = LocalTime.now().toSecondOfDay();
		for(int i = 0; i < numberOfQuestions; i++){
			Question tempQuestion = qs.getQuestion(quizId, i, "activeQuizList");
			System.out.println(tempQuestion.getName() + nl);
			for(Answer a : tempQuestion.getPossibleAnswers()){
				System.out.println(a.getAnswerId() + ". " + a.getAnswer() + nl);
			}
			System.out.println("Please choose your answer (1, 2, 3, 4 or 5)");
			boolean finished = false;
			do{
				try{
					if (tempQuestion.getTrueAnswer() == Integer.parseInt(input.nextLine())) score++;
					finished = true;
				}catch (NumberFormatException e){
					System.out.println("not a number");
				}
			}while(!finished);
		}
		time = LocalTime.now().toSecondOfDay() - time;
		System.out.println("Your score was: " + qs.finishTakingQuiz(quizId, user, score, time));
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
