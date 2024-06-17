package RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MatrixService extends Remote {
    void sendMatrix(int[][] matrix) throws RemoteException;
    void setMethod(String method) throws RemoteException;
    int getGreatestNumber() throws RemoteException;
    double getTime() throws RemoteException;
}
