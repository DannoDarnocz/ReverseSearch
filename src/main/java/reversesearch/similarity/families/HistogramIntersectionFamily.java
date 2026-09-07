package reversesearch.similarity.families;

import reversesearch.similarity.SimilarityResult;
import reversesearch.similarity.likenessmethods.HistogramIntersection;
import reversesearch.similarity.likenessmethods.LikenessMethod;

import java.util.Comparator;

public class HistogramIntersectionFamily implements SimilarityFamilyFactory {
    @Override
    public LikenessMethod createLikenessMethod() {
        return new HistogramIntersection();
    }

    @Override
    public Comparator<SimilarityResult> createComparator() {
        // DESCENDENTE, mayor valor, mas parecido
        return (a, b) -> Double.compare(b.getLikenessValue(), a.getLikenessValue());
    }
}