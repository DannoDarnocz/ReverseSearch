package reversesearch.filehandler;

import javafx.scene.control.Label;
import net.coobird.thumbnailator.Thumbnails;
import reversesearch.imagehandler.Histogram;
import reversesearch.imagehandler.HistogramCalculator;
import reversesearch.imagehandler.ImageReference;
import reversesearch.structure.doublylinkedlist.DoublyLinkedList;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class FolderLoader extends Loader {
    /*private class MultithreadedReader implements Runnable  {
        private File file;

        public MultithreadedReader(File file) {
            this.file = file;
        }

        @Override
        public void run() {
            BufferedImage thumb = Thumbnails.of(file).size(160, 160).asBufferedImage();
            ImageReference ref = new ImageReference(file.getAbsolutePath(), thumb);
            Histogram histogram = new Histogram(ref, binQuantity);
            histogram = HistogramCalculator.calculateNormalized(histogram);
        }*/

    // singleton
    private static FolderLoader instance = new FolderLoader();

    private FolderLoader() {}

    public static FolderLoader getInstance() {
        return instance;
    }


    @Override
    public DoublyLinkedList<Histogram> loadHistograms(String path, int binQuantity) throws Exception {
        DoublyLinkedList<Histogram>  loadedList = new DoublyLinkedList<Histogram> ();
        File directory = new File(path);

        if (!directory.isDirectory()) return null;

        // ver todas las imagenes que sean en formato png
        File[] files = directory.listFiles((dir, name) -> name.endsWith(".png"));
        // si no hay imagenes o es null entonces no hay nada y no se puede hacer nada
        if (files == null || files.length == 0) return null;

        // ver cuantos hilos hay disponibles
        int threads = Runtime.getRuntime().availableProcessors();
        // ejecutar en paralelo
        try(ExecutorService executor = Executors.newFixedThreadPool(threads)){
            // future es para el resultado de una funcion asincrona
            List<Future<Histogram>> futures = new ArrayList<>();

            for (File f : files) {
                futures.add(executor.submit(() -> {
                    // crear referencia con miniatura
                        BufferedImage thumb = Thumbnails.of(f).size(80, 80).asBufferedImage();
                        ImageReference ref = new ImageReference(f.getAbsolutePath(), thumb);
                        Histogram histogram = new Histogram(ref, binQuantity);
                        histogram = HistogramCalculator.calculateNormalized(histogram);
                        return histogram;
                }));
            }

            executor.shutdown();

            // recorrer la lista que se construyo de forma asincrona
            for (Future<Histogram> future : futures) {
                try {
                    loadedList.addStart(future.get()); // uno a la vez
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if(loadedList.isEmpty()) return null;
        return loadedList;
    }
}
