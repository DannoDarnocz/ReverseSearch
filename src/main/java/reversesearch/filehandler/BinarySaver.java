package reversesearch.filehandler;
import reversesearch.structure.doublylinkedlist.DoublyLinkedList;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import reversesearch.structure.doublylinkedlist.ListIterator;
import reversesearch.imagehandler.Histogram;

public class BinarySaver {
    public static boolean saver(DoublyLinkedList<Histogram> histograms, String path) {
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(path))) {
            out.writeInt(histograms.size());
            int total = histograms.size();

            ListIterator<Histogram> iterator = histograms.getIterador();

            // para asegurarse de que se escriba exactamente la cantidad se usa un iterador con contador
            for (int i = 0; i < total; i++) {
                Histogram current = iterator.next();
                out.writeUTF(current.getImagePath());
                out.writeInt(current.getBinsPerColor());
                for (int j = 0; j < current.getTotalBins(); j++) {
                    out.writeFloat(current.getBin(j));
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