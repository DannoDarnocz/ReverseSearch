package reversesearch.structure;

public class IntMatrix {
    int[][] matrix;
    int rows; // filas
    int columns; // columnas
    int usedFields; // cantidad de espacios ocupados
    IntMatrix(int rows, int columns){
        // inicializar todos los campos con 0
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
        matrix[row][col] = value;
        usedFields++;
    }

    public int get(int row, int col){
        return matrix[row][col];
    }

    public int size() {return rows*columns;}
}
