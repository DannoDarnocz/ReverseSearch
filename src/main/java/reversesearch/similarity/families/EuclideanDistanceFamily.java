package reversesearch.similarity.families;

import reversesearch.similarity.SimilarityResult;
import reversesearch.similarity.likenessmethods.EuclideanDistance;
import reversesearch.similarity.likenessmethods.LikenessMethod;

import java.util.Comparator;

public class EuclideanDistanceFamily implements SimilarityFamilyFactory {
    @Override
    public LikenessMethod createLikenessMethod() {
        return new EuclideanDistance();
    }

    @Override
    public Comparator<SimilarityResult> createComparator() {
        // ASCENDENTE, menor valor mas parecido (invertir el orden de los parametros)
        return (a, b) -> Double.compare(a.getLikenessValue(), b.getLikenessValue());
    }
}
