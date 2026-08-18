package reversesearch.imagehandler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Image {
    static int binQuantity = 4;
    private BufferedImage bufferedImage;

    public Image(String path){

        File inputFile = new File(path); // abrir imagen
        try(Scanner myReader = new Scanner(inputFile)) {
            // intentar guardar imagen en bufer
            bufferedImage = ImageIO.read(inputFile);

            System.out.println(bufferedImage.getWidth());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    // constructor cuando ya se tiene el objeto
    public Image(BufferedImage img){
        bufferedImage = img;
    }
    BufferedImage getBufferedImage(){ return bufferedImage; }
    int getWidth(){return bufferedImage.getWidth();};
    int getHeight(){return  bufferedImage.getHeight();};
    int getSize() {return getWidth()*getHeight();};
}
