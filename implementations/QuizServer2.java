package quiz.implementations;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import quiz.interfaces.Question;
import quiz.interfaces.Quiz;
import quiz.interfaces.QuizService2;
import quiz.interfaces.User;

public class QuizServer2 extends UnicastRemoteObject implements QuizService2 {

	private static final long serialVersionUID = -6976763377726698928L;
	private static List<Quiz> quizList = new ArrayList<Quiz>();
	private static int quizId = 0;
	private Quiz tempQuiz;
	private Question tempQuestion;
	
	public QuizServer2() throws RemoteException {
		super();
	}

	@Override
	public int createEmptyQuiz(User creator, String quizTitle) throws RemoteException {
		CheckNull(quizTitle);
		quizId++;
		tempQuiz = new QuizImpl(quizTitle, quizId, creator);
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

	@Override
	public void AddQuestionToQuiz() throws RemoteException {
		tempQuiz.addQuestion(tempQuestion);

	}

	@Override
	public int finishQuizSetUp() throws RemoteException {
		quizList.add(tempQuiz);
		return tempQuiz.getId();
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
	public int checkQuizandCreator(User user, int id) throws RemoteException {
		for (Quiz q : quizList){
			if (q.getId() == id){
				if (q.getCreator().equals(user)){
					return id;
				}else{
					throw new IllegalArgumentException("you are not the creator of this quiz");
				}
			}else{
				throw new IllegalArgumentException("this id does not correspond to a quiz");
			}
		}
		return 0;
	}
}
