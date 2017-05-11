package centralsystem;

import databaseadapter.DatabaseAdapter;
import databaseadapter.*;
import java.io.IOException;
import java.net.ServerSocket;
import ticketCollector.Fine;

public class CSystem implements CentralSystemCollectorInterface,CentralSystemTicketInterface {

    private final int PORTA_SERVER = 5000;
    private final int NUMERO_MACCHINETTE = 3;

    private final DatabaseAdapter database;
    private ServerSocket socketListener;
    private SocketHandler scHandler;
    private BankAdapter bank;

    public CSystem() {
        this.database = new DatabaseAdapter();
        this.bank = new BankAdapter();
        initTickets();
        initUsers();
        initCollectors();
        initServer();
    }

    private void initTickets() {
        for (int i = 0; i < 10; i++) {
            database.addTicket(new TicketDB(TicketType.SINGLE));
        }

        for (int i = 0; i < 10; i++) {
            database.addTicket(new TicketDB(TicketType.SEASON));
        }
    }

    private void initUsers() {
        database.addUser("Gabriele", "Mazzola", "MZZGRL95B22L872K","gabriele.m1995@gmail.com", "pizza123");
        database.addUser("Manuele", "Longhi", "ASCAKJSCAKSBCAKSJBHC","manumanu@gmail.com", "manumanu");
    }

    public boolean checkUser(String cf) {
        return database.checkUser(cf);
    }

    public boolean addUser(String name, String surname, String username,String cf,String psw) {
        return database.addUser(name, surname, username,cf, psw);
    }
    
    private void initCollectors() {
        database.addCollector("Andrea", "Rossi", "RSSNDR95A13G388U", "IOBOHIOBOH");
        database.addCollector("Mario", "bioanchi", "mrreaosdnvaoen", "manumanu");
    }
    
    public boolean addCollector(String name, String surname, String cf,String psw) {
        return database.addCollector(name, surname, cf, psw);
    }
    
    public boolean addFine(Fine fine) {
        return database.addFine(fine);
    }

    @Override
    public boolean existsTicket(String ticketCode) {
        return database.existsTicket(ticketCode);
    }

    public boolean isValid(String ticketCode) {
        return database.isValid(ticketCode);
    }

    public void printTickets() {
        database.printTickets();
    }

    public void printUsers() {
        database.printUsers();
    }

    private void initServer() {
        try {
            socketListener = new ServerSocket(PORTA_SERVER);
        } catch (IOException ex) {
            System.err.println("Errore apertura porta serverSocket");
        }

        scHandler = new SocketHandler(socketListener, this);
        scHandler.start();
    }

   
    @Override
    public boolean userLogin(String username, String psw) {
        return database.userLogin(username, psw);
    }

    public boolean makeFine(Fine f) {
    	//TODO
    	return true;
    }

    @Override
    public String requestCodes() {        
        String result = "MAZZOLAINARRIVO";
        return result;
    }

    @Override
    public boolean cardPayment(String cardNumber) {
        
        //TODO
        
        return bank.checkCreditCard(cardNumber);
    }

    @Override
    public boolean collectorLogin(String username, String psw) {
        return database.collectorLogin(username, psw);
    }

    @Override
    public String centralSystemTEST(String sentTest) {
        return sentTest;
    }

	@Override
	public boolean createUser(String name, String surname, String username,String cf, String psw) {
		return database.addUser(name, surname, username, cf, psw);
	}

}
