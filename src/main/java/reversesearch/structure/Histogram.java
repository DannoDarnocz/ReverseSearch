package reversesearch.structure;

public class Histogram {
    double[] vector;
    int bins; // espacios
    int usedFields; // cantidad de espacios ocupados

    public Histogram(int bins){
        // inicializar todos los campos con 0
        vector = new double[bins];
        for(int i=0;i<bins;i++){
            vector[i]= 0;
        }
        this.bins = bins;
        usedFields = 0;
    }

    public void sumBin(int i){
        vector[i]++;
    }

    public double get(int i){
        return vector[i];
    }

    public void normalize(int totalPixels){
        for(int i=0;i<bins;i++){
            vector[i] /= (double)totalPixels;
        }
    }

    public int binQuantity() {return bins;}
}
