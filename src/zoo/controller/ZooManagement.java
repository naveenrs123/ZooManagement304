package zoo.controller;

import zoo.database.DatabaseConnectionHandler;
import zoo.delegates.LoginWindowDelegate;
import zoo.delegates.TerminalTransactionsDelegate;
import zoo.model.BranchModel;
import zoo.ui.LoginWindow;
import zoo.ui.TerminalTransactions;

/**
 * This is the main controller class that will orchestrate everything.
 */
public class ZooManagement implements LoginWindowDelegate, TerminalTransactionsDelegate {
	private DatabaseConnectionHandler dbHandler = null;
	private LoginWindow loginWindow = null;

	public ZooManagement() {
		dbHandler = new DatabaseConnectionHandler();
	}
	
	private void start() {
		loginWindow = new LoginWindow();
		loginWindow.showFrame(this);
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

			TerminalTransactions transaction = new TerminalTransactions();
			transaction.showMainMenu(this);
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
    public void terminalTransactionsFinished() {
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
