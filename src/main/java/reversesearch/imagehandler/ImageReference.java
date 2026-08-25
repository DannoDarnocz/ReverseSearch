package reversesearch.imagehandler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ImageReference {
    private String path;
    private BufferedImage thumbnail;

    public ImageReference(String path, BufferedImage thumbnail){
        this.path = path;
        this.thumbnail = thumbnail;
    }

    public String getPath(){return path;}
    public BufferedImage getThumbnail() {return thumbnail;}
}
