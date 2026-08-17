package reversesearch.filehandler;

import reversesearch.structure.doublylinkedlist.ImageList;

public abstract class Loader {
    public abstract ImageList loadHistograms(String path);
}
