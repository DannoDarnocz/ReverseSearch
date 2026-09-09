package reversesearch.filehandler;

import reversesearch.imagehandler.Histogram;
import reversesearch.structure.doublylinkedlist.DoublyLinkedList;

public interface Loader {
    public abstract DoublyLinkedList<Histogram> loadHistograms(String path, int binQuantity) throws Exception;

}
