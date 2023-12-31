import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {
    
    public static void populateMatrixFromFile(float[][] matrix) throws FileNotFoundException {
        System.out.println("Enter file path: ");
        Scanner scnr = new Scanner(System.in);
        Scanner fileScnr = new Scanner(new File(scnr.nextLine()));
        scnr.close();

        for (float[] row : matrix) {
            for (int i = 0; i < row.length; i++) {
                row[i] = fileScnr.nextFloat(); 
            }
        }
        fileScnr.close();
    }
    public static void populateMatrixFromUserInput(float[][] matrix) {
        Scanner scnr = new Scanner(System.in);
        for (int row = 0; row < matrix.length; row++) {
            System.out.println("Enter row " + row + "'s values: ");
            String[] values = scnr.nextLine().split(" ");
            for (int column = 0; column < matrix[row].length; column++) {
                matrix[row][column] = Float.parseFloat(values[column]);
            }
        }
        scnr.close();
    }
    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);
        System.out.println("How many linear equations do you want to solve for?: ");
        int numEquations = scnr.nextInt();
        float[][] matrix = new float[numEquations][numEquations+1];

        System.out.println("How do you want to enter the matrix \n1. Enter matrix manually \n2. Read from file\n");
        int userChoice = scnr.nextInt();

        if(userChoice == 1){
            populateMatrixFromUserInput(matrix);
        }
        else if(userChoice ==2 ){

            //creating matrix
            boolean notDone = true; 
            while(notDone){
                try{populateMatrixFromFile(matrix);
                    notDone = false;
                }
                catch(FileNotFoundException expec){
                    System.out.println("The file inputted can't be found.");
                }
            }
        }
        else{
            return;
        }
        
        ArrayList<Float> scale_factors;
        ArrayList<Float> scale_ratios = new ArrayList<>();
    //creates final matrix
    for(int currentPivotRow = 0; currentPivotRow< matrix.length-1; currentPivotRow++) {
        System.out.println("Count: " + currentPivotRow);
        scale_factors = scaleFactors(matrix, currentPivotRow);
        System.out.println("Scale factors" + scale_factors);
        //get scale ratios
        int scaleFactorIterator = 0;
        for (int i = currentPivotRow; i < matrix[0].length-1; i++) {
            scale_ratios.add(Math.abs(( matrix[i][currentPivotRow] / scale_factors.get(scaleFactorIterator))));
            scaleFactorIterator++;
        }
        System.out.println("Scale ratios" + scale_ratios);
        //set the row with the largest scale_ratio as the pivot row

        switchRows(matrix, scale_ratios.indexOf(maxScaleRatio(scale_ratios))+currentPivotRow, currentPivotRow);

        System.out.print("Pivot Row: ");
        for (float nums : matrix[currentPivotRow]) {
            System.out.print(nums + " ");
        }
        System.out.println();

        ArrayList<Float> multipliers = new ArrayList<>();
        calculateMultipliers(matrix, multipliers, currentPivotRow);
        System.out.println("Multipliers: "+multipliers);

        eliminateVariable(matrix, multipliers, currentPivotRow);
        printMatrix(matrix);

        scale_factors.clear();
        scale_ratios.clear();
        multipliers.clear();
    }
        ArrayList<Float> solutions = solveVariables(matrix);
        System.out.println("Solution: " + solutions);

    }
    public static ArrayList<Float> solveVariables(float[][] matrix) {
        int numRows = matrix.length;
        int numCols = matrix[0].length-1;

        ArrayList<Float> solution = new ArrayList<>(Collections.nCopies(numCols, 0.0f));

        for (int row = numRows - 1; row >= 0; row--) {
            float sum = 0;
            for (int col = row + 1; col < numCols; col++) {
                sum += matrix[row][col] * solution.get(col);
            }
            solution.set(row, (matrix[row][numCols] - sum) / matrix[row][row]);
        }

        return solution;
    }
    public static void printMatrix(float[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            System.out.print("[");
            for (int j = 0; j < matrix[0].length; j++)
                System.out.print(matrix[i][j] + " ");
            System.out.print("]");
            System.out.println();
        }
    }

    public static void switchRows(float[][] mat, int row1, int row2) {
        if (row1 == row2){
            System.out.println("I've returned");
            return;
        }
        float[] temp = mat[row1];
        mat[row1] = mat[row2];
        mat[row2] = temp;
        printMatrix(mat);
    }

    public static ArrayList<Float> scaleFactors(float[][] mat, int pivotRow) {
        ArrayList<Float> scalefactors = new ArrayList<>();
        for (int i = pivotRow; i < mat.length; i++) {
            float max = mat[i][0];
            //go through each row and find max excluding last number
            for(int j = 0; j < mat[0].length-1; j++){
                if (max < mat[i][j]) {
                    //find the largest number in each row and set it to max
                    max = mat[i][j];
                }
            }
            scalefactors.add(max);
        }
        return scalefactors;
    }

    public static float maxScaleRatio(ArrayList<Float> scale_ratios) {
        float max = scale_ratios.get(0);
        for (int i = 0; i < scale_ratios.size(); i++) {
            if (max < scale_ratios.get(i)) {
                max = scale_ratios.get(i);
            }
        }
        return max;
    }
    public static void calculateMultipliers(float[][] matrix ,ArrayList<Float> multipliers, int pivotRow){
        //iterate through rows
        for (int i =pivotRow+1; i<matrix.length; i++){
            multipliers.add(matrix[i][pivotRow]/matrix[pivotRow][pivotRow]);
        }
    }
    public static void eliminateVariable(float[][] matrix, ArrayList<Float> multiplier, int currentPivotRow){
        int multiplierIterator = 0;
        //iterates through rows starting one after current pivot row
        for (int i = currentPivotRow+1; i<matrix.length; i++){
            //iterates through columns
            for (int j = 0; j < matrix[0].length; j++){
                matrix[i][j] = matrix[i][j] - (matrix[currentPivotRow][j] * multiplier.get(multiplierIterator)) ;
            }
            multiplierIterator++;
        }
    }
}

