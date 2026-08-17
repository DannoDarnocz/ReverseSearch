package reversesearch.imagehandler;

import reversesearch.structure.Histogram;
import reversesearch.structure.IntMatrix;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Image {
    static int binQuantity = 4;
    private BufferedImage bufferedImage;
    private IntMatrix pixelMatrix;
    private Histogram histogram;

    public Image(String path){

        File inputFile = new File(path); // abrir imagen
        try(Scanner myReader = new Scanner(inputFile)) {
            // intentar guardar imagen en bufer
            bufferedImage = ImageIO.read(inputFile);

            System.out.println(bufferedImage.getWidth());

            // inicializar
            pixelMatrix = new IntMatrix(bufferedImage.getWidth(),bufferedImage.getHeight());

            // insertar cada pixel en la matriz con su color
            for (int row = 0; row < bufferedImage.getHeight(); row++) {
                for (int col = 0; col < bufferedImage.getWidth(); col++) {
                    try{
                        pixelMatrix.insert(row,col,bufferedImage.getRGB(col, row)); // .getRGB obtiene valor para el color
                    } catch (Exception e){
                        System.out.println(e.getStackTrace());
                    }

                }
            }

            // calcular histograma
            this.histogram = ImageHistogramCalculator.calculateNormalized(this,binQuantity);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    // constructor cuando ya se tiene el objeto
    public Image(BufferedImage img){
        try{
            bufferedImage = img;
            // inicializar
            pixelMatrix = new IntMatrix(bufferedImage.getWidth(),bufferedImage.getHeight());

            // insertar cada pixel en la matriz con su color
            for (int row = 0; row < bufferedImage.getHeight(); row++) {
                for (int col = 0; col < bufferedImage.getWidth(); col++) {
                    try{
                        pixelMatrix.insert(row,col,bufferedImage.getRGB(col, row)); // .getRGB obtiene valor para el color
                    } catch (Exception e){
                        System.out.println(e.getMessage());
                    }

                }
            }

            // calcular histograma
            this.histogram = ImageHistogramCalculator.calculateNormalized(this,4);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    int getWidth(){return bufferedImage.getWidth();};
    int getHeight(){return  bufferedImage.getHeight();};
    int getSize() {return getWidth()*getHeight();};

    public IntMatrix getPixelMatrix(){
        return pixelMatrix;
    }
}
