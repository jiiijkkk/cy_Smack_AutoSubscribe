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
			try {
				conn_tmp.connect();
				try {
					conn_tmp.getAccountManager().createAccount( name_prefix + i , name_prefix );
					//conn_tmp.disconnect();
				} catch (XMPPException e) {
					System.out.println( "Warning : test " + i + " is existed!" );
				}
			} catch (XMPPException e1) {
				System.err.println("Warning : Can not connected!");
			}
		}
	}
	public void deleteAccounts(){
		int i = 0;
		for ( ; i< account_number ; i++ ){
			Connection conn_tmp = new XMPPConnection(this.domain);
			try {
				conn_tmp.connect();
				try {
					conn_tmp.login( this.name_prefix + i , this.name_prefix );
					try {
						conn_tmp.getAccountManager().deleteAccount();
					} catch (XMPPException e_delete) {
						System.err.println("Warning : Can not deleted!");
					}
					conn_tmp.disconnect();
				} catch (XMPPException e_login) {
					System.err.println("Warning : Can not login!");
				}
			} catch (XMPPException e_connect) {
				System.err.println("Warning : Can not connected!");
			}
		}
	}
	public void createListeners(){
		int i = 0;
		for ( ; i< account_number ; i++ ){
			Connection conn_tmp = new XMPPConnection(this.domain);
			try {
				conn_tmp.connect();
				try {
					conn_tmp.login( this.name_prefix + i , this.name_prefix );
					subscriptionListener thisSubscriptionListener = new subscriptionListener(conn_tmp);
					thisSubscriptionListener.addRosterListener();
					this.conns.add(conn_tmp);
				} catch (XMPPException e_login) {
					System.err.println("Warning : Can not login!");
				}
			} catch (XMPPException e_connect) {
				System.err.println("Warning : Can not connected!");
			}
		}
	}
	public void disconnectListeners(){
		for ( Connection conn_tmp : this.conns ){
			conn_tmp.disconnect();
		}
	}
}
