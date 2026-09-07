package reversesearch.similarity.likenessmethods;
import reversesearch.imagehandler.Histogram;

public interface LikenessMethod {
    // retorna un double con los calculos, luego afuera se hace lo que se necesite con ese resultado
    abstract public double compare(Histogram A, Histogram B);
}
