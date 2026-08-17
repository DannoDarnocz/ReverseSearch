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
            for(int column=0;column<img.getWidth();column++){
                // desempaquetar colores con el entero almacenado
                int currentRGB = imageMatrix.get(row,column);

                // poner los bits que corresponden a cada color haciendo bitshifting poniendo lso
                // bits que nos interesan en los últimos 8 bits
                // 8 bits de alfa, 8 de red, 8 de green, 8 de blue
                // hacer "and" de 0xFF hace que los ultimos 8 bits sean 1, poniendo el resto en 0
                int r = (currentRGB >> 16) & 0xFF;
                int g = (currentRGB >> 8)  & 0xFF;
                int b =  currentRGB       & 0xFF;

                //todo: poner formula bien
                //int currentBin = Math.min( / interval, binQuantity-1); // no hay parte decimal, solo se queda lo entero
                //histogram.sumBin(currentBin); // sumarle uno a ese bin
            }
        }

        // normalizar histograma
        int imageDimensions = img.getSize(); // usar double para poder dividirlo correctamente
        histogram.normalize(imageDimensions);
        return histogram;
    }
}
