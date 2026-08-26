package reversesearch.filehandler;
import reversesearch.structure.doublylinkedlist.DoublyLinkedList;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import reversesearch.structure.doublylinkedlist.ListIterator;
import reversesearch.imagehandler.Histogram;

public class BinarySaver {
    static public boolean saver(DoublyLinkedList<Histogram> histograms, String path) {
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(path))) {
            out.writeInt(histograms.size());
            ListIterator<Histogram> iterator = histograms.getIterador();
            while(iterator.hasNext()) {
                iterator = iterator.getNext();
                Histogram current = iterator.getContent();
                out.writeUTF(current.getImagePath());
                out.writeInt(current.getBinsPerColor());

                for (int i = 0; i < current.getTotalBins(); i++) {
                    out.writeDouble(current.getBin(i));
                }
            }
            return true;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}