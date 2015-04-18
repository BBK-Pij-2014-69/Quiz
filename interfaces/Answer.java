package quiz.interfaces;

import java.rmi.Remote;

public interface Answer extends Remote {

	int getAnswerId();
	
	String getAnswer();
	
}
