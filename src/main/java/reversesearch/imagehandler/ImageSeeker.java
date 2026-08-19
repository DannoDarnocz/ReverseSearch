package reversesearch.imagehandler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageSeeker {
    // obtener imagen desde una referencia (puede tirar excepcion si no se encuentra o algo ocurre)
    public static BufferedImage bufferedFromReference(ImageReference ref) throws IOException {
        String path = ref.getPath();
        File f = new File(path);
        return ImageIO.read(f);
    }
}
