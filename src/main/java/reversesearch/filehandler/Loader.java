package reversesearch.filehandler;

import reversesearch.structure.doublylinkedlist.BufferedImageList;

public abstract class Loader {
    public abstract BufferedImageList loadHistograms(String path);
}
