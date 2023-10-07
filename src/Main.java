import java.io.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {


        //creating test matrix
        float[][] matrix = new float[3][4];
        matrix[0][0] = 2;
        matrix[0][1] = 3;
        matrix[0][2] = 0;
        matrix[0][3] = 8;
        matrix[1][0] = -1;
        matrix[1][1] = 2;
        matrix[1][2] = -1;
        matrix[1][3] = 0;
        matrix[2][0] = 3;
        matrix[2][1] = 0;
        matrix[2][2] = 2;
        matrix[2][3] = 9;
        ArrayList<Float> scale_factors;
        ArrayList<Float> scale_ratios = new ArrayList<>();

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
        ArrayList<Float> scalefactors = new ArrayList<Float>();
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

