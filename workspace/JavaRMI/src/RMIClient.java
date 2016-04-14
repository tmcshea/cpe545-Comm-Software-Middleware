import java.net.Inet4Address;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;

public class RMIClient {

	final static String CALL_EXAMPLE = "\nUsage:\n"
			+ "    java RMIClient [policy file] [host:port]\n" 
			+ "Example:\n"
			+ "    java RMIClient ..\\client.policy 127.0.0.1\n";

	public static void main(String args[]) {
		if (validateArgs(args))
			return;

		// System.setProperty("java.security.policy","client.policy");
		System.setProperty("java.security.policy", args[0]);

		String host = args[1];

		String message = "blank";

		// I download server's stubs ==> must set a SecurityManager
		System.setSecurityManager(new RMISecurityManager());

		try {
			Hello obj = (Hello) Naming.lookup("//" + host + "/HelloServer"); // objectname
																				// in
																				// registry
			System.out.println(obj.sayHello());
		} catch (Exception e) {
			System.out.println("RMIClient exception: " + e.getMessage());
			e.printStackTrace();
		}

	}

	private static boolean validateArgs(String[] args) {
		int expectedNumberOfArgs = 2;
		if (args != null && args.length >= expectedNumberOfArgs) {

			if (!new java.io.File(args[0]).exists()) {
				System.out.println("Invalid argument. File " + args[0] + " could not be found.");
			} else {
				try {
					Inet4Address.getByName(args[1]);
					return true;
				} catch (UnknownHostException e) {
					// e.printStackTrace();
					System.out.println("Invalid argument. You must provide a valid IP Address.");
				}
			}
		} else {
			System.out.println("Incorrect call. " + "Expected " + expectedNumberOfArgs + " arguments.");
		}
		System.out.println(CALL_EXAMPLE);
		return false;
	}

}
