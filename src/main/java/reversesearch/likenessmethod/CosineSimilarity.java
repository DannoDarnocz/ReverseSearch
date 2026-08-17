package reversesearch.likenessmethod;

import reversesearch.imagehandler.Image;
import reversesearch.structure.IntMatrix;

public class CosineSimilarity extends LikenessMethod {
    @Override
    public double compare(Image img1, Image img2) {
        // obtener vectores de cada imagen
        IntMatrix img1Matrix = img1.getPixelMatrix();
        IntMatrix img2Matrix = img2.getPixelMatrix();

        double result = 0;
        return result;
    }
}
