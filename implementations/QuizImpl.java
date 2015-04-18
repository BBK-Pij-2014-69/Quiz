package quiz.implementations;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import quiz.interfaces.CompletedQuizUser;
import quiz.interfaces.Question;
import quiz.interfaces.Quiz;
import quiz.interfaces.User;

public class QuizImpl implements Quiz, Serializable {

	private static final long serialVersionUID = 4690010803983285974L;
	private String quizName;
	private int quizId;
	private List<Question> questionList = new LinkedList<Question>();
	private User creator;
	private List<CompletedQuizUser> userList = new ArrayList<CompletedQuizUser>();
	
	public QuizImpl(String s, int quizId, User creator){
		this.quizName = s;
		this.quizId = quizId;
		this.creator = creator;
	}

	@Override
	public String getQuizName() throws RemoteException {
		return quizName;
	}

	@Override
	public int getId() throws RemoteException {
		return quizId;
	}

	@Override
	public ArrayList<Question> getQuestionList() throws RemoteException {
		return (ArrayList<Question>) questionList;
	}

	@Override
	public void addQuestion(Question q) throws RemoteException {
		questionList.add(q);
	}
	
	@Override
	public Question accessLastQuestion(){
		return questionList.get(questionList.size()-1);
	}

	@Override
	public User getCreator() throws RemoteException {
		return creator;
	}

	@Override
	public User getWinner() throws RemoteException {
		return null;
	}

	@Override
	public void addCompletedQuizUser(CompletedQuizUser user) throws RemoteException {
		userList.add(user);
	}

}
