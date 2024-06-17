package RMI;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIServer {
    public static void main(String[] args) {
        try {
            // Set the hostname to the server's IP address
            System.setProperty("java.rmi.server.hostname", "25.39.100.34");

            MatrixService matrixService = new MatrixServiceImpl();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("MatrixService", matrixService);

            System.out.println("Server started on " + System.getProperty("java.rmi.server.hostname"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
