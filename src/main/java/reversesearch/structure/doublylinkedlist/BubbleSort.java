package reversesearch.structure.doublylinkedlist;

import reversesearch.similarity.SimilarityResult;

import java.util.Comparator;

public class BubbleSort implements SortMethod {
    @Override
    public void sort(DoublyLinkedList<SimilarityResult> list, Comparator<SimilarityResult> comparator) {
        if (list == null || list.size() <= 1) {
            return; // no ocupa ordenar
        }

        boolean swapped;

        ListIterator<SimilarityResult> current= list.getIterador();
        while(current.hasNext() ){
            ListIterator<SimilarityResult> nextNode = current.getNext();
            if (comparator.compare(current.getContent(), nextNode.getContent()) > 0) {
                // intercambiar valores
                SimilarityResult temp = current.getContent();
                current.setContent(nextNode.getContent());
                nextNode.setContent(temp);
                swapped = true;
            }
            // obtener siguiente
            current = nextNode;
        }

        while (current != null && current.getNext() != null) {
            ListIterator<SimilarityResult>nextNode = current.getNext();
            if (comparator.compare(current.getContent(), nextNode.getContent()) > 0) {
                // intercambiar valores
                SimilarityResult temp = current.getContent();
                current.setContent(nextNode.getContent());
                nextNode.setContent(temp);
                swapped = true;
            }
            current = nextNode;
        }
    }
}
