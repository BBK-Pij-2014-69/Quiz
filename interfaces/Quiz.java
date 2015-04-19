package quiz.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.LinkedList;

public interface Quiz extends Remote {

	String getQuizName() throws RemoteException;
	
	int getId() throws RemoteException;
	
	LinkedList<Question> getQuestionList() throws RemoteException;
	
	void addQuestion(Question q) throws RemoteException;
	
	User getCreator() throws RemoteException;
	
	User getWinner() throws RemoteException;
	
	void addCompletedQuizUser(CompletedQuizUser user) throws RemoteException;
	
	public Question accessLastQuestion() throws RemoteException;
}
