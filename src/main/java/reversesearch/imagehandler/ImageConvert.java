package reversesearch.imagehandler;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javafx.scene.image.Image;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImageConvert {
    public static Image fromBuffered(BufferedImage bImg) throws IOException {
        // convertir por bytes
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bImg, "png", baos);
        return new Image(new ByteArrayInputStream(baos.toByteArray()));
    }
}
