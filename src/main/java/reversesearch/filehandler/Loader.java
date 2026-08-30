package reversesearch.filehandler;

import reversesearch.imagehandler.Histogram;
import reversesearch.structure.doublylinkedlist.DoublyLinkedList;

public abstract class Loader {
    public abstract DoublyLinkedList<Histogram> loadHistograms(String path, int binQuantity) throws Exception;

}
