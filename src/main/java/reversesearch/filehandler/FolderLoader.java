package reversesearch.filehandler;

import net.coobird.thumbnailator.Thumbnails;
import reversesearch.imagehandler.Histogram;
import reversesearch.imagehandler.HistogramCalculator;
import reversesearch.imagehandler.ImageReference;
import reversesearch.structure.doublylinkedlist.DoublyLinkedList;
import reversesearch.structure.doublylinkedlist.ListIterator;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class FolderLoader implements Loader {

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
            // future es para el resultado de una funcion asincrona que aún no se tiene pero se "promete" que enn el futuro
            // estará construido.
            // luego debe de iterarse de nuevo en
            // una nueva lista para evitar conflictos
            DoublyLinkedList<Future<Histogram>> futures = new DoublyLinkedList<>();

            for (File f : files) {
                futures.addEnd(executor.submit(() -> {
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
            ListIterator<Future<Histogram>> it = futures.getIterador();
            while (it!=null) {
                try {
                    // aqui si se tiene el future construido (o espera a que se termine de construir),
                    // entonces se obtiene con .get() hasta que esté listo y se asegura que la nueva lista
                    // quede construida con objetos válidos
                    loadedList.addStart(it.getContent().get());
                } catch (Exception e) {
                    e.printStackTrace(); // continuar en vez de pararlo completamente, saltandose la iactual
                }
                it=it.getNext();
            }

        }
        if(loadedList.isEmpty()) return null;
        return loadedList;
    }
}
