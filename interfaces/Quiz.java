package quiz.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * A quiz is used to store all the information (a quiz name, a quiz id(should be unique),
 * a User (the creator), a list of questions and a list of users who have completed the quiz)
 * about a particular user made quiz.
 * note: the uniqueness of the id is not controlled here (so any implementation will need
 * to take this into account).
 * 
 * @author Kieren Millar
 */
public interface Quiz extends Remote {

	/**
	 * Method for accessing the name of the quiz.
	 * 
	 * @return String - the name of the quiz.
	 * @throws RemoteException
	 */
	String getQuizName() throws RemoteException;
	
	/**
	 * Method for accessing the quiz id.
	 * 
	 * @return int - the id of the quiz
	 * @throws RemoteException
	 */
	int getId() throws RemoteException;
	
	/**
	 * Method for accessing the questions stored in the quiz.
	 * 
	 * @return List - a list of type Question.
	 * @throws RemoteException
	 */
	LinkedList<Question> getQuestionList() throws RemoteException;
	
	/**
	 * Method for adding questions to a quiz.
	 * 
	 * @param Question - the question to be added
	 * @throws RemoteException
	 */
	void addQuestion(Question q) throws RemoteException;
	
	/**
	 * Method for accessing the creator of the quiz.
	 * (Used to ensure only the cretor is able to edit a quiz)
	 * 
	 * @return User - The details of the user who created the quiz.
	 * @throws RemoteException
	 */
	User getCreator() throws RemoteException;
	
	/**
	 * Method for accessing the list of users who have attempted the quiz.
	 * 
	 * @return List - A list of type CompletedQuizUser.
	 * @throws RemoteException
	 */
	ArrayList<CompletedQuizUser> getCompletedQuizUserList() throws RemoteException;
	
	/**
	 * Method for adding users who have attempted the quiz.
	 * 
	 * @param CompletedQuizUser 
	 * @throws RemoteException
	 */
	void addCompletedQuizUser(CompletedQuizUser user) throws RemoteException;
	
	/**
	 * Method for accessing the most recently added question.
	 * 
	 * @return Question
	 * @throws RemoteException
	 */
	Question accessLastQuestion() throws RemoteException;
}
