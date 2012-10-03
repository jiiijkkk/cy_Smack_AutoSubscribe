import java.util.ArrayList;
import java.util.List;

import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;


public class SmackCore {	
	private String domain;
	private int account_number;
	private String name_prefix;
	private List<Connection> conns = new ArrayList<Connection>();
	
	public SmackCore(
			String domain ,
			int account_number ,
			String name_prefix
			){
		
		//XMPPConnection.DEBUG_ENABLED = true;
		
		this.domain = domain;
		this.account_number = account_number;
		this.name_prefix = name_prefix;
	}
	public void createAccounts(){
		int i = 0;
		for ( ; i < account_number ; i++ ){
			Connection conn_tmp = new XMPPConnection(this.domain);
			this.conns.add(conn_tmp);
			try {
				conn_tmp.connect();
				try {
					conn_tmp.getAccountManager().createAccount( name_prefix + i , name_prefix );
				} catch (XMPPException e) {
					System.err.println( "Warning : admin " + i + " is existed!" );
				}
			} catch (XMPPException e1) {
				System.err.println("Warning : can not connected!");
			}
		}
	}
	public void deleteAccounts(){
		int i = 0;
		for ( Connection conn_tmp : this.conns ){
			try {
				conn_tmp.getAccountManager().deleteAccount();
			} catch (XMPPException e_delete) {
				System.err.println("Warning : admin " + i + " can not be deleted!");
			}
			conn_tmp.disconnect();
			i++;
		}
	}
	public void createListeners(){
		int i = 0;
		for ( Connection conn_tmp : this.conns ){
			try {
				conn_tmp.login( this.name_prefix + i , this.name_prefix );
				subscriptionListener thisSubscriptionListener = new subscriptionListener(conn_tmp);
				thisSubscriptionListener.addRosterListener();
			} catch (XMPPException e_login) {
				System.err.println("Warning : can not login!");
			}
			i++;
		}
	}
	public void disconnectListeners(){
		for ( Connection conn_tmp : this.conns ){
			conn_tmp.disconnect();
		}
	}
}
