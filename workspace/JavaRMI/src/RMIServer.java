import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMIServer extends UnicastRemoteObject implements Hello {

	final static String CALL_EXAMPLE = "\nUsage:\n" 
			+ "    java RMIServer [ip address]\n" 
			+ "Example:\n"
			+ "    java RMIServer 127.0.0.1\n";

	public RMIServer() throws RemoteException {
	}

	public String sayHello() {
		return "Hello world!";
	}

	public static void main(String args[]) throws UnknownHostException {
		if (!validateArgs(args)) {
			return;
		}

		// windows: run >> start rmiregistry
		// ubuntu: run >> rmiregistry &
		final String bindName = "HelloServer";
		final String hostname = args[0];// InetAddress.getLocalHost().getHostAddress();
		System.setProperty("java.rmi.server.hostname", hostname);
		System.out.println("Hostname set to: " + hostname);
		System.out.println("Bind name set to: " + bindName);

		try {
			RMIServer obj = new RMIServer();
			// Bind this object instance to the name
			Naming.rebind(bindName, obj);
			System.out.println("Object binded to name: " + bindName);
			System.out.println("Ready for connections...");

		} catch (Exception e) {
			System.out.println("RMIServer err: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private static boolean validateArgs(String[] args) {
		int expectedNumberOfArgs = 1;
		if (args != null && args.length >= expectedNumberOfArgs){
			try {
			  Inet4Address.getByName(args[0]);
			  return true;
			} catch (UnknownHostException e) {
//				e.printStackTrace();
				System.out.println("Invalid argument. You must provide a valid IP Address.");
			}			
		}
		else{
			System.out.println("Incorrect call. "
					+ "Expected " + expectedNumberOfArgs + " arguments.");
		}
		System.out.println(CALL_EXAMPLE);
		return false;
	}
}
