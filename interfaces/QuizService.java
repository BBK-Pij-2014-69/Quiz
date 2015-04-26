package quiz.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * This service is used for creating and storing quiz's on a server.
 * 
 * @author Kieren Millar
 *
 */
public interface QuizService extends Remote {
	
	/**
	 * Method for creating a quiz object to be stored on the server.
	 * 
	 * @param creator
	 * @param quizTitle
	 * @return int - the id of the quiz
	 * @throws RemoteException
	 */
	int createEmptyQuiz(User creator, String quizTitle) throws RemoteException;
	
	/**
	 * Method for adding a question to an already created quiz.
	 * 
	 * @param question
	 * @param quizId
	 * @throws RemoteException
	 */
	void addQuestionToQuiz(String question, int quizId) throws RemoteException;
	
	/**
	 * Method for adding answers to question.
	 * 
	 * @param answer
	 * @param quizId
	 * @throws RemoteException
	 */
	void addAnswerstoQuestions(String answer, int quizId) throws RemoteException;
	
	/**
	 * Method for setting/saving the id of the correct answer.
	 * 
	 * @param quizId
	 * @param answerId
	 * @throws RemoteException
	 */
	void setActualAnswer(int quizId, int answerId) throws RemoteException;
	
	/**
	 * Method for accessing a list of stored quiz's.
	 * 
	 * @return List - a list of quiz's.
	 * @throws RemoteException
	 */
	List<Quiz> getQuizs() throws RemoteException;
	
	/**
	 * Method for checking whether the current user is the creator of the quiz.
	 * 
	 * @param user - the current user trying to access a quiz.
	 * @param id
	 * @param listToCheck - active or non active quiz list.
	 * @return int - id of quiz.
	 * @throws RemoteException
	 */
	int checkQuizandCreator(User user, int id, String listToCheck) throws RemoteException;

	/**
	 * Method for accessing the number of questions a quiz holds.
	 * 
	 * @param currentQuizId
	 * @param listToCheck - active or non active quiz list.
	 * @return int - number of questions.
	 * @throws RemoteException
	 */
	int getNumberOfQuestions(int currentQuizId, String listToCheck) throws RemoteException;

	/**
	 * Method for accessing a question of a particular quiz.
	 * 
	 * @param currentQuizId
	 * @param index - index of question
	 * @param listToCheck - active or non active quiz list.
	 * @return
	 * @throws RemoteException
	 */
	Question getQuestion(int currentQuizId, int index, String listToCheck) throws RemoteException;

	/**
	 * Method for deleting a question from a particular quiz.
	 * 
	 * @param currentQuizId
	 * @param index - index of question to delete.
	 * @throws RemoteException
	 */
	void deleteQuestion(int currentQuizId, int index) throws RemoteException;
	
	/**
	 * Method for activating a quiz. (moving it to the active quiz list).
	 * Once active should not be able to edit (except for closing purposes).
	 * 
	 * @param currentQuizId
	 * @throws RemoteException
	 */
	void ActivateQuiz(int currentQuizId) throws RemoteException;
	
	/**
	 * Method for closing a quiz. Returns the details of the quiz winner.
	 *
	 * @param id
	 * @return CompletedQuizUser
	 * @throws RemoteException
	 */
	CompletedQuizUser closeQuiz(int id) throws RemoteException;
	
	/**
	 * Method for saving the data to a file.
	 * @throws RemoteException
	 */
	void saveData() throws RemoteException;

	/**
	 * Method for accessing the title/name of a quiz.
	 * 
	 * @param index
	 * @return String - title/name of the quiz
	 * @throws RemoteException
	 */
	String getQuizTitles(int index) throws RemoteException;

	/**
	 * Method for checking whether the requested quiz is active or not.
	 * 
	 * @param quizId
	 * @return int - the quizId
	 * @throws RemoteException
	 */
	int checkIfActiveQuiz(String quizId) throws RemoteException;

	/**
	 * Method for adding a User who has finished a quiz (as a CompletedQuizUser) to the current quiz being taken.
	 * 
	 * @param quizId
	 * @param user
	 * @param score
	 * @param time
	 * @return int - the score of the of the taken quiz.
	 * @throws RemoteException
	 */
	int finishTakingQuiz(int quizId, User user, int score, int time) throws RemoteException;
	

}
