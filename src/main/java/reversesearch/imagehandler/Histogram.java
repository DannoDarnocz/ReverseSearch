package reversesearch.imagehandler;

import reversesearch.structure.DoubleVector;

public class Histogram {
    private DoubleVector vector;
    private ImageReference referencedImage; // a cual imagen esta asociado
    private int binsPerColor; // espacios

    public Histogram(ImageReference referenced, int bins){
        this.binsPerColor = bins;
        this.referencedImage = referenced;

        // crear histograma (HistogramCalculator se encarga de llenar los espacios con los valores calculados en el momento)

        // son 3 colores, cada uno con "n" cantidad de bins, hay que almacenar todas las posibles combinaciones
        // de esos 3 colores para esa "n" cantidad de bins por lo que es "n" a la 3
        int histogramBins = (int)Math.pow(binsPerColor,3);

        vector = new DoubleVector(histogramBins);

    }

    public double getBin(int i){
        return vector.getAt(i);
    }
    public void sumBin(int i){vector.sumIndex(i);}
    public void normalize(int dimensions){vector.normalizeAll(dimensions);}

    public int binQuantity() {return binsPerColor;}
    public ImageReference getReferencedImage(){return referencedImage;}
    public int getBinsPerColor(){return binsPerColor;}
}
