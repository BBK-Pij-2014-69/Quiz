package quiz.implementations;

import quiz.interfaces.Answer;

public class AnswerImpl implements Answer {

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
