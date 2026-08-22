package reversesearch.structure.doublylinkedlist;

import reversesearch.imagehandler.Histogram;
import reversesearch.imagehandler.ImageReference;

public class HistogramList extends DoublyLinkedList<Histogram > {
    public HistogramList orderByLikeness(ImageReference targetImage){
        // todo: implementar strategy para el metodo de ordenamiento
        return new HistogramList();
    }
}
