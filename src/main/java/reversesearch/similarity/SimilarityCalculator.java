package reversesearch.similarity;

import reversesearch.imagehandler.Histogram;
import reversesearch.imagehandler.HistogramCalculator;
import reversesearch.imagehandler.ImageReference;
import reversesearch.similarity.likenessmethods.LikenessMethod;
import reversesearch.structure.doublylinkedlist.*;

public class SimilarityCalculator {
    public static DoublyLinkedList<SimilarityResult> calculate(ImageReference target, DoublyLinkedList<Histogram>  databaseHistograms, LikenessMethod likenessMethod, int binQuantity){
        // calcular histograma para el target
        Histogram targetHistogram = new Histogram(target, binQuantity);
        HistogramCalculator.calculateNormalized(targetHistogram);


        // construir lista de resultados para comparacion
        DoublyLinkedList<SimilarityResult> results = new DoublyLinkedList<SimilarityResult>();

        // iterar lista de histogramas
        ListIterator<Histogram> it = databaseHistograms.getIterador();
        while(it!=null){
            // comparar actual de la lista de histogramas con el target
            Histogram currentHistogram = it.getContent();
            double currentValue = likenessMethod.compare(currentHistogram,targetHistogram);
            ImageReference currentImage = currentHistogram.getReferencedImage();

            SimilarityResult currentResult = new SimilarityResult(currentImage,currentValue);
            results.addStart(currentResult); // insertar al inicio coste 1
            it=it.getNext();
        }

        return results;
    }
}
