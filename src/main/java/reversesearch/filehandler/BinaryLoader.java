package reversesearch.filehandler;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import reversesearch.structure.doublylinkedlist.DoublyLinkedList;
import reversesearch.imagehandler.Histogram;
import reversesearch.imagehandler.ImageReference;

public class BinaryLoader extends Loader {
    @Override
    public DoublyLinkedList<Histogram> loadHistograms(String path, int binQuantity) {
        try (DataInputStream in = new DataInputStream(new FileInputStream(path))) {
            DoublyLinkedList<Histogram>  histograms = new DoublyLinkedList<Histogram> ();
            int total = in.readInt();
            for (int j = 0; j < total; j++) {
                String imagePath = in.readUTF();
                int binsPerColor = in.readInt();
                ImageReference ref = new ImageReference(imagePath, null); // sin miniatura: no se guarda en el archivo binario, solo ruta y vector
                //validar con if(thumbnail != null) antes de usar getThumbnail()

                Histogram histogram = new Histogram(ref, binsPerColor);
                for (int i = 0; i < histogram.getTotalBins(); i++) {
                    double value = in.readDouble();
                    histogram.setBin(i, value);
                }
                histograms.addEnd(histogram);
            }
            return histograms;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}