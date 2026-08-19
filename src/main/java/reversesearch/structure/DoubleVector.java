package reversesearch.structure;

public class DoubleVector {
    private int size;
    private double[] vector;

    public DoubleVector(int size){
        vector = new double[size];
        for(int i=0;i<size;i++) vector[i] = 0;
        this.size = size;
    }

   public void sumIndex(int i){
        if(i>=size) throw new ArrayIndexOutOfBoundsException("El indice para el DoubleVector está fuera de rango: " + i + ">="+  size);
        vector[i]++;
    }

   public double getAt(int i){
        return vector[i];
    }

    public void normalizeAll(int num){
        // double para hacer la division con resultado correcto
        for(int i=0;i<size;i++) vector[i] /= (double)num;
    }
}
