package quiz.implementations;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import quiz.interfaces.Answer;
import quiz.interfaces.Question;

/**
 * @see Question
 * 
 * @author Kieren Millar
 */
public class QuestionImpl implements Question, Serializable {

	private static final long serialVersionUID = -199888619757711060L;
	private int actualAnswer;
	private String name;
	private List<Answer> answers = new ArrayList<Answer>();
	private int answerId = 1;//used to assign id's to the answers.
	
	/**
	 * Constructor that accepts a string (the question to be asked).
	 * 
	 * @param String
	 */
	public QuestionImpl(String s){
		this.name = s;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ArrayList<Answer> getPossibleAnswers() {
		ArrayList<Answer> returnList = new ArrayList<Answer>();
		for(Answer a: answers) returnList.add(a);
		return returnList;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getTrueAnswer() {
		return actualAnswer;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setTrueAnswer(int answerId) {
		this.actualAnswer = answerId;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addPossibleAnswer(String s) {
		answers.add(new AnswerImpl(answerId, s));
		answerId++;
	}


}
