package quiz.implementations;

import java.io.Serializable;

import quiz.interfaces.Answer;

/**
 * @see Answer
 * @author Kieren Millar
 *
 */
public class AnswerImpl implements Answer, Serializable {

	private static final long serialVersionUID = -1995337390384321481L;
	private int id;
	private String answer;
	
	/**
	 * Constructor accepting an int for the id and a string for the answer.
	 * @param id
	 * @param answer
	 */
	public AnswerImpl(int id, String answer){
		this.id = id;
		this.answer = answer;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getAnswerId() {
		return id;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getAnswer() {
		return answer;
	}

}
