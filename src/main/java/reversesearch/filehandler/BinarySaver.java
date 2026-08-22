package reversesearch.filehandler;
import reversesearch.structure.doublylinkedlist.HistogramList;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class BinarySaver {
    public boolean saver(HistogramList histograms, String path) {
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(path))) {

        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}