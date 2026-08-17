package reversesearch.imagehandler;

import reversesearch.structure.Histogram;
import reversesearch.structure.IntMatrix;

public class ImageHistogramCalculator {
    // única responsabilidad de calcular histograma normalizado
    static public Histogram calculateNormalized(Image img, int binQuantity){
        // primero calcular histograma normal con la cantidad de bins
        Histogram histogram = new Histogram(binQuantity);

        int interval = 256/binQuantity; // cuantos valores que van en cada bin

        // recorrer todos los pixeles y clasificarlo por bin
        IntMatrix imageMatrix = img.getPixelMatrix();

        for(int row=0;row<img.getHeight();row++){
            for(int column=0;row<img.getWidth();row++){
                int currentBin = imageMatrix.get(row,column) / interval; // no hay parte decimal, solo se queda lo entero
                histogram.sumBin(currentBin); // sumarle uno a ese bin
            }
        }

        // normalizar histograma
        int imageDimensions = img.getSize(); // usar double para poder dividirlo correctamente
        histogram.normalize(imageDimensions);
        return histogram;
    }
}
