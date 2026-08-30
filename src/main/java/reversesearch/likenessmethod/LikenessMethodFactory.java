package reversesearch.likenessmethod;

public class LikenessMethodFactory {
    public static LikenessMethod getLikenessMethod(String methodName) {
        switch (methodName) {
            case "Distancia euclidiana":
                return new EuclideanDistance();
            case "Intersección de histogramas":
                return new HistogramIntersection();
            case "Similitud coseno":
                return new CosineSimilarity();
            default:
                throw new IllegalArgumentException("Unknown likeness method: " + methodName);
        }
    }
}

