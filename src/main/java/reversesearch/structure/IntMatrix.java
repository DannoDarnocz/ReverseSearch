package reversesearch.structure;

import org.w3c.dom.ranges.Range;
import org.w3c.dom.ranges.RangeException;

public class IntMatrix {
    private int[][] matrix;
    private int rows; // filas
    private int columns; // columnas
    private int usedFields; // cantidad de espacios ocupados
    public IntMatrix(int rows, int columns){
        // inicializar todos los campos con 0
        matrix = new int[rows][columns];
        for(int row=0;row<rows;row++){
            for(int column=0;column<columns;column++){
                matrix[row][column] = 0;
            }
        }
        this.rows=rows;
        this.columns=columns;
        usedFields = 0;
    }

    public void insert(int row, int col, int value){
        if(row>rows||col>columns){
            throw new IndexOutOfBoundsException("La celda especificada esta fuera del rango");
        }
        matrix[row][col] = value;
        System.out.println("Prieba");
        usedFields++;
    }

    public int get(int row, int col){
        return matrix[row][col];
    }

    public int size() {return rows*columns;}
}
