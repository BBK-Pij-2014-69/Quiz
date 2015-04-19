package quiz.implementations;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import quiz.interfaces.CompletedQuizUser;
import quiz.interfaces.Question;
import quiz.interfaces.Quiz;
import quiz.interfaces.QuizService2;
import quiz.interfaces.User;

public class QuizServer2 extends UnicastRemoteObject implements QuizService2 {

	private static final long serialVersionUID = -6976763377726698928L;
	private static List<Quiz> quizList = new ArrayList<Quiz>();
	private static List<Quiz> activeQuizList = new ArrayList<Quiz>();
	private static int quizId = 0;
	private static final CompletedQuizUser defaultUser = new CompletedQuizUserImpl(new UserImpl("no one", "a"), 0, 0);
	
	public QuizServer2() throws RemoteException {
		super();
	}

	@Override
	public int createEmptyQuiz(User creator, String quizTitle) throws RemoteException {
		CheckNull(quizTitle);
		quizId++;
		quizList.add( new QuizImpl(quizTitle, quizId, creator));
		return quizId;
	}

	@Override
	public void addQuestionToQuiz(String question, int quizId) throws RemoteException {
		CheckNull(question);
		for (Quiz q : quizList){
			if (quizId == q.getId()) q.addQuestion(new QuestionImpl(question));
		}

	}

	@Override
	public void addAnswerstoQuestions(String answer, int quizId) throws RemoteException {
		CheckNull(answer);
		for (Quiz q : quizList){
			if (quizId == q.getId()) q.accessLastQuestion().addPossibleAnswer(answer);
		}
	}

	@Override
	public void setActualAnswer(int quizId, int answerId) throws RemoteException {
		if (answerId > 0 && answerId < 6){
			for (Quiz q : quizList){
				if (quizId == q.getId()) q.accessLastQuestion().setTrueAnswer(answerId);
			}
		}else{
			throw new IllegalArgumentException("invalid id. please use a number 1-5");
		}
	}

	private void CheckNull(String argument){
		if (argument.equals(null) || argument.equals("")){
			throw new IllegalArgumentException("null argument");
		}
	}

	@Override
	public List<Quiz> getQuizs() throws RemoteException {
		return quizList;
	}

	@Override
	public int checkQuizandCreator(User user, int id, String listToCheck) throws RemoteException {
		List<Quiz> whichList = null;
		if (listToCheck.equals("activeQuizList")){
			whichList = activeQuizList;
		}else{
			whichList = quizList;
		}
		if (whichList.isEmpty()) return 0;
		for (Quiz q : whichList){
			if (q.getId() == id){
				if (q.getCreator().equals(user)){
					return id;
				}else{
					throw new IllegalArgumentException("you are not the creator of this quiz");
				}
			}
		}
		throw new IllegalArgumentException("this id does not correspond to a quiz");
	}

	@Override
	public int getNumberOfQuestions(int currentQuizId) throws RemoteException {
		int temp = 0;
		for (Quiz q : quizList){
			if (q.getId() == currentQuizId) temp = q.getQuestionList().size();
		}
		return temp;
	}

	@Override
	public Question getQuestion(int currentQuizId, int i) throws RemoteException {
		Question temp = null;
		for (Quiz q : quizList){
			if (q.getId() == currentQuizId) temp = q.getQuestionList().get(i);
		}
		return temp;
	}

	@Override
	public void deleteQuestion(int currentQuizId, int i) throws RemoteException {
		for (Quiz q : quizList){
			if (q.getId() == currentQuizId) q.getQuestionList().remove(i);
		}
	}

	@Override
	public void ActivateQuiz(int currentQuizId) throws RemoteException {
		Quiz temp = null;
		for (Quiz q : quizList){
			if (q.getId() == currentQuizId) { 
				activeQuizList.add(q);
				temp = q;
			}
		}
		quizList.remove(temp);
	}

	@Override
	public CompletedQuizUser closeQuiz(int id) throws RemoteException {
		CompletedQuizUser temp = defaultUser;
		ArrayList<CompletedQuizUser> tempList = null;
		int indexToRemove = -1;
		for (Quiz q : activeQuizList){
			if (q.getId() == id){
				tempList = q.getCompletedQuizUserList();
				if (tempList.isEmpty()){
					q.addCompletedQuizUser(defaultUser);
					throw new IllegalArgumentException("No one has taken this quiz");
				}
				indexToRemove = activeQuizList.indexOf(q);
			}
		}
		for (CompletedQuizUser c : tempList){
			if (c.getScore() > temp.getScore()){
				if (c.getTime() < temp.getTime()) temp = c;
			}
		}
		activeQuizList.remove(indexToRemove);
		return temp;
	}

	
}
