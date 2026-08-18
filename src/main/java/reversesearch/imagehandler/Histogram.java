package reversesearch.imagehandler;

import reversesearch.structure.DoubleVector;

import java.awt.image.BufferedImage;

public class Histogram {
    DoubleVector vector;
    BufferedImage bufferedImage; // a cual imagen esta asociado
    int binQuantity; // espacios

    public Histogram(BufferedImage image, int bins){
        this.binQuantity = bins;
        this.bufferedImage = image;

        // primero calcular histograma normal con la cantidad de bins
        vector = new DoubleVector(binQuantity);

        int interval = 256/binQuantity; // cuantos valores que van en cada bin

        for(int row=0;row<image.getHeight();row++){
            for(int column=0;column<image.getWidth();column++){
                // desempaquetar colores con el RGB entero que representa el color
                int currentRGB = bufferedImage.getRGB(row,column);

                // poner los bits que corresponden a cada color haciendo bitshifting poniendo lso
                // bits que nos interesan en los últimos 8 bits
                // 8 bits de alfa, 8 de red, 8 de green, 8 de blue
                // hacer "and" de 0xFF hace que los ultimos 8 bits sean 1, poniendo el resto en 0
                int red = (currentRGB >> 16) & 0xFF;
                int green = (currentRGB >> 8)  & 0xFF;
                int blue =  currentRGB       & 0xFF;

                // obtener el bin al que pertenece cada color por individual
                int redBin = Math.min(red / interval, binQuantity-1);
                int greenBin = Math.min(green / interval, binQuantity-1);
                int blueBin = Math.min(blue / interval, binQuantity-1);

                // usar formula para obtener el valor caracteristico del pixel (bin general)
                int overallBin = (int) (redBin * Math.pow(binQuantity,2)+greenBin*binQuantity+blueBin);

                vector.sumIndex(overallBin); // sumarle uno a ese bin "general" para ese pixel
            }
        }

        // normalizar histograma
        int imageDimensions = image.getWidth()*image.getHeight();
        vector.normalizeAll(imageDimensions);
    }

    public double getBin(int i){
        return vector.getAt(i);
    }

    public int binQuantity() {return binQuantity;}
}
