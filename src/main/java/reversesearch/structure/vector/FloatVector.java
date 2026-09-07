package reversesearch.structure.vector;

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
    public void insertAt(int i, float value) {
        vector[i] = value;
    }

    public VectorIterator getIterator(){ return new VectorIterator();}

    public int getSize() { return size;}

    // clase interna para facil manejo del iterator
    public class VectorIterator {
        private int currentIndex = 0;

        public boolean hasNext() {
            return currentIndex < size;
        }

        public float getNext() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException("No hay más elementos en el vector.");
            }
            return vector[currentIndex++];
        }
    }
}