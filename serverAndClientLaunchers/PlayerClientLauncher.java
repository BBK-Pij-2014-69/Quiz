package quiz.serverAndClientLaunchers;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalTime;
import java.util.Scanner;

import quiz.interfaces.Answer;
import quiz.interfaces.Question;
import quiz.interfaces.QuizService;
import quiz.interfaces.User;
import quiz.interfaces.UserService;

/**
 * Client launcher for someone who wishes to create a quiz.
 * 
 * @author Kieren Millar
 */
public class PlayerClientLauncher {

	public Scanner input = new Scanner(System.in);
	public String nl = System.lineSeparator();
	public User user;// used to store the current user.
	
	public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
		PlayerClientLauncher pcl = new PlayerClientLauncher();
		System.out.println("Welcome to Kieren Millar's Quiz Player" + System.lineSeparator());
		pcl.loadLoginMenu();

	}
	
	private void loadLoginMenu() throws RemoteException, MalformedURLException, NotBoundException {
		System.out.println("To login please indicate if you are a new user (1) a returning user (2)" + nl);
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
			System.out.println("Welcome " + user.getName() + " what would you like to do:" + nl +
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
		QuizService qs = connectToQuizServer();
		System.out.println("Please select a quiz to play (1, 2, 3...)" + nl);
		int quizListSize = qs.getQuizs().size();
		if (quizListSize > 0){
			System.out.println("Currently availible Quiz's:" + nl);
			for (int i = 0; i < quizListSize; i++){
				System.out.println(qs.getQuizTitles(i));
			}
		}else{
			System.out.println("There are currently no active quizes" + nl + "GoodBye");
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
		QuizService qs = connectToQuizServer();
		int score = 0;
		int numberOfQuestions = qs.getNumberOfQuestions(quizId, "activeQuizList");
		int time = LocalTime.now().toSecondOfDay();//used to determine the time it takes the user to finish the quiz.
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
		time = LocalTime.now().toSecondOfDay() - time;//used to determine the time it takes the user to finish the quiz.
		System.out.println("Your score was: " + qs.finishTakingQuiz(quizId, user, score, time));
	}

	private void loginUser() throws RemoteException, MalformedURLException, NotBoundException {
		UserService us = conectToUserServer();
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
		UserService us = conectToUserServer();
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

	private UserService conectToUserServer() throws RemoteException, NotBoundException, MalformedURLException{
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		Remote service = Naming.lookup("//127.0.0.1:1099/user");
		return (UserService) service;
	}
	
	private QuizService connectToQuizServer() throws MalformedURLException, RemoteException, NotBoundException {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		Remote service = Naming.lookup("//127.0.0.1:1099/quiz");
		return (QuizService) service;
	}
}
