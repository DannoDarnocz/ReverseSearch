package reversesearch.filehandler;

import reversesearch.structure.doublylinkedlist.HistogramList;

public abstract class Loader {
    public abstract HistogramList loadHistograms(String path, int binQuantity);
}
