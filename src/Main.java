import java.io.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        //creating test matrix
        int[][] matrix = new int[3][4];
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

        System.out.print(scaleFactors(matrix));
        switchRows(matrix, 0, 1);
        }
    public static void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++)
                System.out.print(matrix[i][j] + " ");
            System.out.println();
        }
    }
    public static void switchRows(int[][] mat, int row1, int row2){
            int[] temp = mat[row1];
            mat[row1] = mat[row2];
            mat[row2] = temp;
            printMatrix(mat);
        }

    public static ArrayList<Integer> scaleFactors(int [][] mat) {
        ArrayList<Integer> scalefactors = new ArrayList<Integer>();
        for (int[] row : mat) {
            int max = row[0];
            for (int num : row) {
                if (max < num) {
                    //find the largest number in each row and set it to max
                    max = num;
                }
            }
            scalefactors.add(max);
        }
        return scalefactors;
    }
}
