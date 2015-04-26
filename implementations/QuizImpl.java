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

/**
 * @see Quiz
 * @author Kieren Millar
 */
public class QuizImpl implements Quiz, Serializable {

	private static final long serialVersionUID = 4690010803983285974L;
	private String quizName;
	private int quizId;
	private List<Question> questionList = new LinkedList<Question>();
	private User creator;
	private List<CompletedQuizUser> userList = new ArrayList<CompletedQuizUser>();//stores users who have completed the quiz.
	
	/**
	 * Constructor accepting a quiz name, an id (unique), and a user (the creator of the quiz).
	 * 
	 * @param quizTitle
	 * @param quizId
	 * @param creator
	 */
	public QuizImpl(String quizTitle, int quizId, User creator){
		this.quizName = quizTitle;
		this.quizId = quizId;
		this.creator = creator;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getQuizName() throws RemoteException {
		return quizName;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getId() throws RemoteException {
		return quizId;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LinkedList<Question> getQuestionList() throws RemoteException {
		return (LinkedList<Question>) questionList;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addQuestion(Question q) throws RemoteException {
		questionList.add(q);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Question accessLastQuestion(){
		return questionList.get(questionList.size()-1);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User getCreator() throws RemoteException {
		return creator;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ArrayList<CompletedQuizUser> getCompletedQuizUserList() throws RemoteException {
		return (ArrayList<CompletedQuizUser>) userList;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addCompletedQuizUser(CompletedQuizUser user) throws RemoteException {
		userList.add(user);
	}

}
