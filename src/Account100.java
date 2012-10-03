import java.io.IOException;
import java.util.Scanner;

public class Account100 {
	private static String domain = "192.168.200.10";
	private int account_number;
	private static String name_prefix = "admin";
	
	private SmackCore core;
	
	public static void main (String[] args){
		Account100 main = new Account100();
		main.run();
	}
	public void run(){
		System.out.print("Admin number : ");
		Scanner scanner = new Scanner(System.in);
		this.account_number = Integer.valueOf(scanner.next());
		
		this.core = new SmackCore(
				domain ,
				account_number ,
				name_prefix
				);
		
		System.out.println( "Creating " + this.account_number + " accounts..." );
		this.core.createAccounts();
		System.out.println( account_number + " accounts created!" );
		
		System.out.println("Creating " + this.account_number + " listeners");
		this.core.createListeners();
		System.out.println( account_number + " listener created!");
		
		pause();
		
		System.out.println("Deleting " + this.account_number + " accounts...");
		this.core.deleteAccounts();
		System.out.println( account_number + " accounts deleted!");
		
		System.out.println("Disconnecting " + this.account_number + " listeners...");
		this.core.disconnectListeners();
		System.out.println( account_number + " listener disconnected!");
		
		System.out.println("Exit!");
	}
	public static void pause(){
		Scanner scanner = new Scanner(System.in);
		System.out.print("Press the enter key to continue");
		scanner.nextLine();
	}
}
