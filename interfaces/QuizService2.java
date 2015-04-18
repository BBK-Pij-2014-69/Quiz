package quiz.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface QuizService2 extends Remote {
	
	int createEmptyQuiz(User creator, String quizTitle) throws RemoteException;
	
	void addQuestionToQuiz(String question, int quizId) throws RemoteException;
	
	void addAnswerstoQuestions(String answer, int quizId) throws RemoteException;
	
	void setActualAnswer(int quizId, int answerId) throws RemoteException;
	
	void AddQuestionToQuiz() throws RemoteException;
	
	int finishQuizSetUp() throws RemoteException;
	
	List<Quiz> getQuizs() throws RemoteException;
	
	int checkQuizandCreator(User user, int id) throws RemoteException;

}
