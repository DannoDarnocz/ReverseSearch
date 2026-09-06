package reversesearch.structure;

public class FloatVector {
    private int size;
    private float[] vector;

    public FloatVector(int size) {
        vector = new float[size];
        for (int i = 0; i < size; i++) vector[i] = 0;
        this.size = size;
    }

    public void sumIndex(int i) {
        if (i >= size)
            throw new ArrayIndexOutOfBoundsException("El indice para el FloatVector está fuera de rango: " + i + ">=" + size);
        vector[i]++;
    }

    public float getAt(int i) {
        return vector[i];
    }

    public void normalizeAll(int num) {
        // float para hacer la division con resultado correcto
        for (int i = 0; i < size; i++) vector[i] /= (float) num;
    }

    //metodo para binaryLoader
    public void setAt(int i, float value) {
        vector[i] = value;
    }
}