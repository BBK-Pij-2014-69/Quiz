package quiz.implementations;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import quiz.interfaces.CompletedQuizUser;
import quiz.interfaces.Question;
import quiz.interfaces.Quiz;
import quiz.interfaces.QuizService;
import quiz.interfaces.User;

/**
 * @see QuizService
 * @author Kieren Millar
 *
 */
public class QuizServer extends UnicastRemoteObject implements QuizService {

	private static final long serialVersionUID = -6976763377726698928L;
	private static List<Quiz> quizList = new ArrayList<Quiz>();//Stores non-active quiz's (editable)
	private static List<Quiz> activeQuizList = new ArrayList<Quiz>();//Stores activated quiz's (non-Editable)
	private static int quizId = 0;//used to give unique ids to a quiz's.
	private static final CompletedQuizUser defaultUser = new CompletedQuizUserImpl(new UserImpl("no one", "a"), 0, 0); //used for comparison and empty list.
	private static final File file = new File("quizServer.txt");
	
	/**
	 * Constructor that calls super for UnicastRemoteObject and loads data from
	 * a file if it it exists.
	 * @throws RemoteException
	 */
	public QuizServer() throws RemoteException {
		super();
		if (file.exists()){
			try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))){
				quizList = (ArrayList<Quiz>) in.readObject();
				activeQuizList = (ArrayList<Quiz>) in.readObject();
				quizId = (int) in.readObject();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized int createEmptyQuiz(User creator, String quizTitle) throws RemoteException {
		CheckNull(quizTitle);
		quizId++;
		quizList.add( new QuizImpl(quizTitle, quizId, creator));
		return quizId;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addQuestionToQuiz(String question, int quizId) throws RemoteException {
		CheckNull(question);
		for (Quiz q : quizList){
			if (quizId == q.getId()) q.addQuestion(new QuestionImpl(question));
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addAnswerstoQuestions(String answer, int quizId) throws RemoteException {
		CheckNull(answer);
		for (Quiz q : quizList){
			if (quizId == q.getId()) q.accessLastQuestion().addPossibleAnswer(answer);
		}
	}

	/**
	 * {@inheritDoc}
	 */
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

	/**
	 * Private method for checking if an argument is null.
	 * @param argument
	 */
	private void CheckNull(String argument){
		if (argument.equals(null) || argument.equals("")){
			throw new IllegalArgumentException("null argument");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Quiz> getQuizs() throws RemoteException {
		return activeQuizList;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int checkQuizandCreator(User user, int id, String listToCheck) throws RemoteException {
		List<Quiz> whichList = null;//used to distinguish which list is to be accessed.
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getNumberOfQuestions(int currentQuizId, String listToCheck) throws RemoteException {
		int temp = 0;
		List<Quiz> whichList = null;//used to distinguish which list is to be accessed.
		if (listToCheck.equals("activeQuizList")){
			whichList = activeQuizList;
		}else{
			whichList = quizList;
		}
		for (Quiz q : whichList){
			if (q.getId() == currentQuizId) temp = q.getQuestionList().size();
		}
		return temp;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Question getQuestion(int currentQuizId, int i, String listToCheck) throws RemoteException {
		Question temp = null;
		List<Quiz> whichList = null;
		if (listToCheck.equals("activeQuizList")){
			whichList = activeQuizList;
		}else{
			whichList = quizList;
		}
		for (Quiz q : whichList){
			if (q.getId() == currentQuizId) temp = q.getQuestionList().get(i);
		}
		return temp;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteQuestion(int currentQuizId, int index) throws RemoteException {
		for (Quiz q : quizList){
			if (q.getId() == currentQuizId) q.getQuestionList().remove(index);
		}
	}

	/**
	 * {@inheritDoc}
	 */
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CompletedQuizUser closeQuiz(int id) throws RemoteException {
		CompletedQuizUser temp = defaultUser;//holds and returns the winner.
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
			if (c.getScore() > temp.getScore()){//determines winner based on score
				temp = c;
			}else if (c.getScore() == temp.getScore()){//determines winner based on time taken when scores are equal.
				if (c.getTime() < temp.getTime()) temp = c;
			}
		}
		activeQuizList.remove(indexToRemove);//deletes selected quiz from list.
		return temp;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveData(){
		if (file.exists()){
			try{
				file.delete();
			} catch (Exception e) {
				e.printStackTrace();
	        }
		}
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))){
			out.writeObject(quizList);
			out.writeObject(activeQuizList);
			out.writeObject(quizId);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getQuizTitles(int i) throws RemoteException {
		String stringtoReturn = "";
		stringtoReturn = activeQuizList.get(i).getId() + ". " + activeQuizList.get(i).getQuizName();
		return stringtoReturn;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int checkIfActiveQuiz(String quizId) throws RemoteException {
		int intToReturn = 0;
		try{
			for (Quiz q : activeQuizList){
				if (q.getId() == Integer.parseInt(quizId)) intToReturn = q.getId();
			}
			if (intToReturn == 0) throw new IllegalArgumentException("Not an active quiz");
		}catch (NumberFormatException e){
			throw new IllegalArgumentException("Invallid input, please enter a number");
		}
		return intToReturn;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int finishTakingQuiz(int quizId, User user, int score, int time) throws RemoteException {
		for (Quiz q : activeQuizList){
			if (q.getId() == quizId) q.addCompletedQuizUser(new CompletedQuizUserImpl(user, score, time));;
		}
		return score;
	}
}
