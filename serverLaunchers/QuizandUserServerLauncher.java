package quiz.serverLaunchers;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import quiz.implementations.QuizServer2;
import quiz.implementations.UserServer2;


public class QuizandUserServerLauncher {
	private static QuizServer2 quizServer = null;
	private static UserServer2 userServer = null;

	public static void main(String[] args) {
		QuizandUserServerLauncher qsl = new QuizandUserServerLauncher();
		Runtime.getRuntime().addShutdownHook(new Thread(){
			public void run(){
				quizServer.saveData();
			}
		});
		qsl.launch();

	}
	private void launch() {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		try {
			LocateRegistry.createRegistry(1099);
			quizServer = new QuizServer2();
			userServer = new UserServer2();
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
