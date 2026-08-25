package reversesearch.filehandler;
import reversesearch.structure.doublylinkedlist.HistogramList;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import reversesearch.structure.doublylinkedlist.ListIterator;
import reversesearch.imagehandler.Histogram;

public class BinarySaver {
    /*public boolean saver(HistogramList histograms, String path) {
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(path))) {
            out.writeInt(histograms.size());
            ListIterator<Histogram> iterator = histograms.getIterador();
            while(iterator.hasNext()) {
                Histogram current = iterator.getNext();
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
    }*/
}