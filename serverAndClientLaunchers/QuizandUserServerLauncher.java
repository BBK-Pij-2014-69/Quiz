package quiz.serverAndClientLaunchers;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import quiz.implementations.QuizServer;
import quiz.implementations.UserServer;

/**
 * Just a simple server launcher.
 * 
 * @author Kieren Millar
 *
 */
public class QuizandUserServerLauncher {
	private static QuizServer quizServer = null;
	private static UserServer userServer = null;

	public static void main(String[] args) {
		QuizandUserServerLauncher qsl = new QuizandUserServerLauncher();
		Runtime.getRuntime().addShutdownHook(new Thread(){
			public void run(){
				quizServer.saveData();
				userServer.saveData();
			}
		});//shutdown hook used to ensure saving of data.
		qsl.launch();

	}
	
	/**
	 * Launch method for a server taken from pij classnotes for an rmis server.
	 */
	private void launch() {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		try {
			LocateRegistry.createRegistry(1099);
			quizServer = new QuizServer();
			userServer = new UserServer();
			String registryHostQS = "//localhost/quiz";
			String registryHostUS = "//localhost/user";
			Naming.rebind(registryHostQS,  quizServer);
			Naming.rebind(registryHostUS,  userServer);
			System.out.println("Server Running");
		} catch(MalformedURLException ex) {
			ex.printStackTrace();
		} catch(RemoteException ex) {
			ex.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
