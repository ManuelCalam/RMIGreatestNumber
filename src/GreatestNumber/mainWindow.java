package GreatestNumber;

import RMI.MatrixService;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;
import javax.swing.*;

public class mainWindow extends JFrame {
    JLabel greatestNumLabel, rowsLabel, columnsLabel;
    JTextField matrixRowsInput, matrixColumnsInput, sequentialTimer, forkJoinTimer, executorTimer, greatestNumberOutput;
    JTextArea arrayTxt;
    JButton generateArrayBtn, sequentialBtn, forkJoinBtn, executorBtn;

    double startTime, endTime, duration;
    int[][] randomArray;
    public double times[] = {0, 0, 0};
    public static int Rows, Columns, greatestNumber;

    MatrixService matrixService;

    public mainWindow() {
        connectToServer();
        drawWindow();
    }

    private void connectToServer() {
        try {
            String host = "25.39.100.34";
            Registry registry = LocateRegistry.getRegistry(host, 1099);
            matrixService = (MatrixService) registry.lookup("MatrixService");
            System.out.println("Connected to server at " + host);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void drawWindow() {
        setTitle("Client Window");
        setSize(1200, 650);
        setLayout(null);
        setLocationRelativeTo(this);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        greatestNumLabel = new JLabel("Numero Mayor");
        rowsLabel = new JLabel("Filas");
        columnsLabel = new JLabel("Columnas");
        matrixRowsInput = new JTextField(9);
        matrixColumnsInput = new JTextField(9);
        sequentialTimer = new JTextField(8);
        forkJoinTimer = new JTextField(8);
        executorTimer = new JTextField(8);
        greatestNumberOutput = new JTextField(8);
        arrayTxt = new JTextArea();
        generateArrayBtn = new JButton("Generar Array");
        sequentialBtn = new JButton("Secuencial");
        forkJoinBtn = new JButton("Fork/Join");
        executorBtn = new JButton("Executor");

        JScrollPane arrayScrollPane = new JScrollPane(arrayTxt);

        greatestNumLabel.setBounds(450, 20, 100, 20);
        greatestNumberOutput.setBounds(550, 20, 100, 20);

        arrayScrollPane.setBounds(20, 50, 900, 520);
        rowsLabel.setBounds(950, 40, 50, 20);
        matrixRowsInput.setBounds(950, 60, 102, 20);

        columnsLabel.setBounds(1055, 40, 70, 20);
        matrixColumnsInput.setBounds(1055, 60, 102, 20);

        generateArrayBtn.setBounds(950, 85, 205, 20);

        sequentialTimer.setBounds(950, 200, 205, 35);
        sequentialBtn.setBounds(950, 240, 205, 20);

        forkJoinTimer.setBounds(950, 310, 205, 35);
        forkJoinBtn.setBounds(950, 350, 205, 20);

        executorTimer.setBounds(950, 410, 205, 35);
        executorBtn.setBounds(950, 450, 205, 20);

        add(greatestNumLabel);
        add(greatestNumberOutput);
        add(arrayScrollPane);

        add(rowsLabel);
        add(matrixRowsInput);
        add(columnsLabel);
        add(matrixColumnsInput);
        add(generateArrayBtn);

        add(sequentialTimer);
        add(sequentialBtn);
        add(forkJoinTimer);
        add(forkJoinBtn);
        add(executorTimer);
        add(executorBtn);

        setVisible(true);

        generateArrayBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                arrayTxt.setText("");
                greatestNumLabel.setText("");

                Rows = Integer.parseInt(matrixRowsInput.getText());
                Columns = Integer.parseInt(matrixColumnsInput.getText());

                if (Rows > Columns) {
                    randomArray = createMatrix(Rows, Columns, Rows);
                } else {
                    randomArray = createMatrix(Rows, Columns, Columns);
                }

                setMatrixOnTxt(randomArray, arrayTxt);

                try {
                    matrixService.sendMatrix(randomArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        sequentialBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    matrixService.setMethod("Secuencial");
                    
                    for(int i = 0; i<3; i++){
                        startTime = System.nanoTime();
                        greatestNumber = matrixService.getGreatestNumber();
                        endTime = System.nanoTime();
                        duration = matrixService.getTime();
                        greatestNumberOutput.setText(String.valueOf(greatestNumber));
                        //System.out.println(duration);
                        Thread.sleep(10);

                    }
                    
                    sequentialTimer.setText(String.valueOf(duration) + "ms");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        forkJoinBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    matrixService.setMethod("ForkJoin");
                    
                    for(int i = 0; i<3; i++){
                        startTime = System.nanoTime();
                        greatestNumber = matrixService.getGreatestNumber();
                        endTime = System.nanoTime();
                        duration = matrixService.getTime();
                        greatestNumberOutput.setText(String.valueOf(greatestNumber));
                        //System.out.println(duration);
                        Thread.sleep(10);
                    }
                    forkJoinTimer.setText(String.valueOf(duration) + "ms");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        executorBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    matrixService.setMethod("Executor");
                    
                    for(int i = 0; i<3; i++){
                        startTime = System.nanoTime();
                        greatestNumber = matrixService.getGreatestNumber();
                        endTime = System.nanoTime();
                        duration = matrixService.getTime();
                        greatestNumberOutput.setText(String.valueOf(greatestNumber));
                        //System.out.println(duration);
                        Thread.sleep(10);

                    }

                    
                    executorTimer.setText(String.valueOf(duration) + "ms");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public int[][] createMatrix(int rows, int columns, int higherNumber) {
        int[][] matrix = new int[rows][columns];
        Random rand = new Random();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix[i][j] = rand.nextInt(higherNumber);
            }
        }
        return matrix;
    }

    public static void setMatrixOnTxt(int[][] matrix, JTextArea showArray) {
        showArray.setText("");
        for (int[] row : matrix) {
            for (int j = 0; j < row.length; j++) {
                showArray.append(String.valueOf(row[j]));
                if (j != row.length - 1) {
                    showArray.append(", ");
                }
            }
            showArray.append("\n");
        }
    }

    public static void main(String[] args) {
        new mainWindow();
    }
}
