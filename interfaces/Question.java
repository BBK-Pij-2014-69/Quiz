package quiz.interfaces;

import java.util.ArrayList;

public interface Question {

	String getName();
	
	ArrayList<Answer> getPossibleAnswers();
	
	int getTrueAnswer();
	
	void addPossibleAnswer(String s);
	
	void setTrueAnswer(int answer);
	
}
