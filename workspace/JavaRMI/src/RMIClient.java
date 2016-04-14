import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;

public class RMIClient {
	public static void main(String arg[]) {
		if (arg == null || arg.length < 2){
			System.out.println("Wrong usage! Missing Arguments" +
								"java RMIClient [policy] [host:port]");
			return;
		}
//		System.setProperty("java.security.policy","client.policy");
		System.setProperty("java.security.policy", arg[0]);
		String host = arg[1];
		
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

}
