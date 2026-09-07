package reversesearch.filehandler;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import net.coobird.thumbnailator.Thumbnails;
import reversesearch.imagehandler.HistogramCalculator;
import reversesearch.imagehandler.ImageSeeker;
import reversesearch.structure.doublylinkedlist.DoublyLinkedList;
import reversesearch.imagehandler.Histogram;
import reversesearch.imagehandler.ImageReference;

public class BinaryLoader extends Loader {
    @Override
    public DoublyLinkedList<Histogram> loadHistograms(String path, int binQuantity) throws Exception {
        try (DataInputStream in = new DataInputStream(new FileInputStream(path))) {
            DoublyLinkedList<Histogram>  histograms = new DoublyLinkedList<Histogram> ();
            int total = in.readInt();
            for (int j = 0; j < total; j++) {

                String imagePath = in.readUTF();
                int binsPerColor = in.readInt();

                // sin miniatura: no se guarda en el archivo binario, solo ruta y vector
                // hay que crear la miniatura buscando la imagen original
                ImageReference ref = new ImageReference(imagePath, null);

                BufferedImage originalImg = ImageSeeker.bufferedFromReference(ref);
                BufferedImage thumb = Thumbnails.of(originalImg).size(80, 80).asBufferedImage();

                // asignamos thumb al ref
                ref.setThumbnail(thumb);

                Histogram histogram = new Histogram(ref, binsPerColor);
                for (int i = 0; i < histogram.getTotalBins(); i++) {
                    float value = in.readFloat();
                    histogram.setBin(i, value);
                }
                histograms.addEnd(histogram);

            }

            return histograms;

        } catch (IOException e) {
            e.printStackTrace();
            throw e; // enviar de nuevo hacia arriba
        }
    }
}