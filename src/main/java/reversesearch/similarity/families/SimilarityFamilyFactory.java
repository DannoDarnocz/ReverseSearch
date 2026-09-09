package reversesearch.similarity.families;

import reversesearch.similarity.SimilarityResult;
import reversesearch.similarity.likenessmethods.LikenessMethod;

import java.util.Comparator;

public abstract class SimilarityFamilyFactory {
    public abstract LikenessMethod createLikenessMethod();

    // Comparator permite comparar SimilarityResult y dependiendo de cual LikenessMethod sea se necesita
    // que sea de forma sscedente o descendente
    public abstract Comparator<SimilarityResult> createComparator();

    public static SimilarityFamilyFactory getFactory(String methodName) {
        switch (methodName) {
            case "Similitud coseno":
                return new CosineSimilarityFamily();
            case "Distancia euclidiana":
                return new EuclideanDistanceFamily();
            case "Intersección de histogramas":
                return new HistogramIntersectionFamily();
            default:
                throw new IllegalArgumentException("Unknown method: " + methodName);
        }
    }
}
