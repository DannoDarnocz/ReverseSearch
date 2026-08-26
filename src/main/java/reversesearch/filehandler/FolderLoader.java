package reversesearch.filehandler;

import net.coobird.thumbnailator.Thumbnails;
import reversesearch.imagehandler.Histogram;
import reversesearch.imagehandler.HistogramCalculator;
import reversesearch.imagehandler.ImageReference;
import reversesearch.structure.doublylinkedlist.DoublyLinkedList;
import reversesearch.structure.doublylinkedlist.ImageReferenceList;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


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
    public DoublyLinkedList<Histogram> loadHistograms(String path, int binQuantity) {
        DoublyLinkedList<Histogram>  loadedList = new DoublyLinkedList<Histogram> ();
        File directory = new File(path);

        if (!directory.isDirectory()) return null;

        // ver todas las imagenes que sean en formato png
        File[] files = directory.listFiles((dir, name) -> name.endsWith(".png"));
        // si no hay imagenes o es null entonces no hay nada y no se puede hacer nada
        if (files == null || files.length == 0) return null;


        for (File f : files) {
            try {
                BufferedImage thumb = Thumbnails.of(f).size(160, 160).asBufferedImage();
                ImageReference ref = new ImageReference(f.getAbsolutePath(), thumb);
                Histogram histogram = new Histogram(ref, binQuantity);

                System.out.println(histogram.getBinsPerColor());
                histogram = HistogramCalculator.calculateNormalized(histogram);
                loadedList.addStart(histogram);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (loadedList.isEmpty()) return null; // no se cargó nada

        return loadedList;
        // todo: arreglar multithread
        /* ver cuantos procesadores hay disponibles
        int threads = Runtime.getRuntime().availableProcessors();
        // ejecutar en paralelo
        try(ExecutorService executor = Executors.newFixedThreadPool(threads)){
            List<Future<ImageReference>> futures = new ArrayList<>();

            for (File f : files) {
                futures.add(executor.submit(() -> {
                    System.out.println("Cargando imagen....");
                    //
                    BufferedImage thumb = Thumbnails.of(f).size(160, 160).asBufferedImage();
                    return new ImageReference(f.getAbsolutePath(), thumb);
                }));
            }

            executor.shutdown();

            for (Future<ImageReference> future : futures) {
                try {
                    newList.addStart(future.get()); // added back on this thread, one at a time = safe
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newList;
    }*/
    }
}
