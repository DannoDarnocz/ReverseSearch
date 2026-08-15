package reversesearch.imagehandler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class imageMatrix {
    private int[][] pixelMatrix;  //todo: meter esto en una clase aparte
    imageMatrix(String path){
        try {
            File inputFile = new File(path); // abrir imagen

            // intentar guardar imagen en bufer
            BufferedImage img = ImageIO.read(inputFile);

            // obtener dimensiones
            int width = img.getWidth();
            int height = img.getHeight();

            // crear matriz con dimensiones
            this.pixelMatrix = new int[height][width];

            // insertar cada pixel en la matriz con su color
            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    pixelMatrix[row][col] = img.getRGB(col, row); // .getRGB obtiene valor para el color
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
