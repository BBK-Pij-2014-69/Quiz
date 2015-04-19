package quiz.implementations;

import java.io.Serializable;

import quiz.interfaces.Answer;

public class AnswerImpl implements Answer, Serializable {

	private static final long serialVersionUID = -1995337390384321481L;
	private int id;
	private String answer;
	
	public AnswerImpl(int id, String answer){
		this.id = id;
		this.answer = answer;
	}
	
	
	@Override
	public int getAnswerId() {
		return id;
	}

	@Override
	public String getAnswer() {
		return answer;
	}

}
