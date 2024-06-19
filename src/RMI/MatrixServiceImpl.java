package RMI;

import GreatestNumber.Executor;
import GreatestNumber.ForkJoin;
import GreatestNumber.Secuencial;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class MatrixServiceImpl extends UnicastRemoteObject implements MatrixService {
    private List<int[][]> matrices = new ArrayList<>();
    private String method;
    private int[][] concatenatedMatrix;
    private int greatestNumber;
    private double duration;

    private JTextArea matrixTextArea;
    private JTextArea methodTextArea;
    private JButton clearButton;

    protected MatrixServiceImpl() throws RemoteException {
        super();
        drawWindow();
    }

    @Override
    public synchronized void sendMatrix(int[][] matrix) throws RemoteException {
        matrices.add(matrix);
        if (matrices.size() == 2) {
            concatenateMatrices();
            displayConcatenatedMatrix();
        }
    }

    private void concatenateMatrices() {
        if (matrices.size() == 2) {
            int totalRows = matrices.get(0).length + matrices.get(1).length;
            int columns = matrices.get(0)[0].length;
            concatenatedMatrix = new int[totalRows][columns];
            int row = 0;
            for (int[][] matrix : matrices) {
                for (int[] rows : matrix) {
                    concatenatedMatrix[row++] = rows;
                }
            }
        }
    }

    private void displayConcatenatedMatrix() {
        StringBuilder sb = new StringBuilder();
        for (int[] row : concatenatedMatrix) {
            for (int element : row) {
                sb.append(element).append(" ");
            }
            sb.append("\n");
        }
        matrixTextArea.setText(sb.toString());
    }

    @Override
    public synchronized void setMethod(String method) throws RemoteException {
        this.method = method;
        methodTextArea.append(method + "\n");
    }

    @Override
    public int getGreatestNumber() throws RemoteException {
        if (concatenatedMatrix == null) {
            throw new RemoteException("Matrices have not been concatenated yet.");
        }

        long startTime = System.nanoTime();

        switch (method) {
            case "Secuencial":
                Secuencial sequential = new Secuencial();
                greatestNumber = sequential.lookForGreatest(concatenatedMatrix, concatenatedMatrix.length, concatenatedMatrix[0].length);
                break;
            case "ForkJoin":
                greatestNumber = ForkJoin.lookForGreatest(concatenatedMatrix);
                break;
            case "Executor":
                try {
                    greatestNumber = new Executor().lookForGreatest(concatenatedMatrix, concatenatedMatrix.length, concatenatedMatrix[0].length);
                } catch (Exception e) {
                    throw new RemoteException("Error executing Executor method", e);
                }
                break;
            default:
                throw new RemoteException("Unknown method: " + method);
        }

        long endTime = System.nanoTime();
        duration = (endTime - startTime) / 1000000.0;

        return greatestNumber;
    }

    @Override
    public double getTime() throws RemoteException {
        return duration;
    }

    private void drawWindow() {
        JFrame frame = new JFrame("Server");
        frame.setSize(600, 500);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel matrixLabel = new JLabel("Matriz Concatenada:");
        matrixLabel.setBounds(10, 10, 200, 20);
        frame.add(matrixLabel);

        matrixTextArea = new JTextArea();
        JScrollPane matrixScrollPane = new JScrollPane(matrixTextArea);
        matrixScrollPane.setBounds(10, 40, 560, 150);
        frame.add(matrixScrollPane);

        JLabel methodLabel = new JLabel("Métodos solicitados:");
        methodLabel.setBounds(10, 200, 200, 20);
        frame.add(methodLabel);

        methodTextArea = new JTextArea();
        JScrollPane methodScrollPane = new JScrollPane(methodTextArea);
        methodScrollPane.setBounds(10, 230, 560, 100);
        frame.add(methodScrollPane);

        clearButton = new JButton("Borrar");
        clearButton.setBounds(230, 340, 120, 30);
        clearButton.addActionListener(e -> clearMatrix());
        frame.add(clearButton);

        frame.setVisible(true);
    }

    private void clearMatrix() {
        matrices.clear();
        concatenatedMatrix = null;
        matrixTextArea.setText("");
        methodTextArea.setText("");
    }
}
