package reversesearch.imagehandler;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;

public class HistogramCalculator {
    public static Histogram calculateNormalized(Histogram histogram) {
        // obtener ruta de la imagen
        ImageReference imageReference = histogram.getReferencedImage();
        try {
            // buscar la imagen real para poder recorrer todos los pixeles
            BufferedImage bufferedImage = ImageSeeker.bufferedFromReference(imageReference);
            if(bufferedImage == null) throw new FileNotFoundException("No se ha encontrado la imagen referenciada.");

            int binQuantity = histogram.getBinsPerColor();
            int interval = 256 / binQuantity; // cuantos valores que van en cada bin

            for (int row = 0; row < bufferedImage.getHeight(); row++) {
                for (int column = 0; column < bufferedImage.getWidth(); column++) {
                    // desempaquetar colores con el RGB entero que representa el color
                    int currentRGB = bufferedImage.getRGB(column, row);

                    // poner los bits que corresponden a cada color haciendo bitshifting poniendo lso
                    // bits que nos interesan en los últimos 8 bits
                    // 8 bits de alfa, 8 de red, 8 de green, 8 de blue
                    // hacer "and" de 0xFF hace que los ultimos 8 bits sean 1, poniendo el resto en 0
                    int red = (currentRGB >> 16) & 0xFF;
                    int green = (currentRGB >> 8) & 0xFF;
                    int blue = currentRGB & 0xFF;

                    // obtener el bin al que pertenece cada color por individual
                    int redBin = Math.min(red / interval, binQuantity - 1);
                    int greenBin = Math.min(green / interval, binQuantity - 1);
                    int blueBin = Math.min(blue / interval, binQuantity - 1);

                    // usar formula para obtener el valor caracteristico del pixel (bin general)
                    int overallBin = (int) (redBin * Math.pow(binQuantity, 2) + greenBin * binQuantity + blueBin);

                    // normalizar histograma con la dimension de la misma
                    int imageDimensions = bufferedImage.getWidth() * bufferedImage.getHeight();
                    histogram.normalize(imageDimensions);

                    histogram.sumBin(overallBin); // sumarle uno a ese bin "general" para ese pixel


                }
            }
            // retornar por si acaso aunque modifica directamente el histograma
            return histogram;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
