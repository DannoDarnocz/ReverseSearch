package reversesearch.imagehandler;

import reversesearch.structure.Histogram;
import reversesearch.structure.IntMatrix;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Image {
    private int width;
    private int height;
    private String url;
    private IntMatrix pixelMatrix;
    private Histogram histogram;


    Image(String path){

        File inputFile = new File(path); // abrir imagen
        try(Scanner myReader = new Scanner(inputFile)) {
            // intentar guardar imagen en bufer
            BufferedImage img = ImageIO.read(inputFile);

            // obtener dimensiones
            this.width =  img.getWidth();
            this.height =  img.getHeight();
            this.url = inputFile.getAbsolutePath();

            // insertar cada pixel en la matriz con su color
            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    pixelMatrix.insert(row,col,img.getRGB(col, row)); // .getRGB obtiene valor para el color
                }
            }

            // calcular histograma
            this.histogram = ImageHistogramCalculator.calculateNormalized(this,4);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    int getWidth(){return width;};
    int getHeight(){return height;};
    int getSize() {return width*height;};
    String getUrl(){return url;}

    public IntMatrix getPixelMatrix(){
        return pixelMatrix;
    }
}
