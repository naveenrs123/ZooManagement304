package zoo.controller;

import zoo.database.DatabaseConnectionHandler;
import zoo.delegates.LoginWindowDelegate;
import zoo.delegates.WelcomeWindowDelegate;
import zoo.model.AnimalModel;
import zoo.model.ZooEmployeeModel;
import zoo.ui.LoginWindow;
import zoo.ui.WelcomeWindow;

/**
 * This is the main controller class that will orchestrate everything.
 */
public class ZooManagement implements LoginWindowDelegate, WelcomeWindowDelegate {
	private DatabaseConnectionHandler dbHandler = null;
	private LoginWindow loginWindow = null;
	private WelcomeWindow welcomeWindow = null;

	public ZooManagement() {
		dbHandler = new DatabaseConnectionHandler();
	}
	
	private void start() {
		loginWindow = new LoginWindow();
		loginWindow.showFrame(this);
		welcomeWindow = new WelcomeWindow(this.dbHandler);
	}
	
	/**
	 * LoginWindowDelegate Implementation
	 * 
     * connects to Oracle database with supplied username and password
     */ 
	public void login(String username, String password) {
		boolean didConnect = dbHandler.login(username, password);

		if (didConnect) {
			// Once connected, remove login window and start text transaction flow
			loginWindow.dispose();
			welcomeWindow.showFrame();

			//finished();
		} else {
			loginWindow.handleLoginFailed();

			if (loginWindow.hasReachedMaxLoginAttempts()) {
				loginWindow.dispose();
				System.out.println("You have exceeded your number of allowed attempts");
				System.exit(-1);
			}
		}


	}

    /**
	 * TerminalTransactionsDelegate Implementation
	 * 
     * The TerminalTransaction instance tells us that it is done with what it's 
     * doing so we are cleaning up the connection since it's no longer needed.
     */ 
    public void finished() {
    	dbHandler.close();
    	dbHandler = null;

    	System.exit(0);
    }

	/**
	 * Main method called at launch time
	 */
	public static void main(String args[]) {
		ZooManagement zm = new ZooManagement();
		zm.start();
	}
}
