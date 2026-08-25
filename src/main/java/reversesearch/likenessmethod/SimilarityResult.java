package reversesearch.likenessmethod;
import reversesearch.imagehandler.ImageReference;

public class SimilarityResult implements Comparable<SimilarityResult> {
    private ImageReference imageReference;
    private double likenessValue;

    public SimilarityResult(ImageReference imageReference, double likenessValue){
        this.imageReference = imageReference;
        this.likenessValue = likenessValue;

    }
    public ImageReference getImageReference() {
        return imageReference;
    }

    public double getLikenessValue() {
        return likenessValue;
    }


    @Override
    public int compareTo(SimilarityResult o) {
        return Double.compare( o.likenessValue,this.likenessValue);
    }
}
