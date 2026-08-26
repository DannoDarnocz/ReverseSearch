package reversesearch.structure.doublylinkedlist;

import reversesearch.likenessmethod.SimilarityResult;

public class BubbleSort implements SortMethod {
    @Override
    public void sort(DoublyLinkedList<SimilarityResult> list) {
        if (list == null || list.size() <= 1) {
            return; // no ocupa ordenar
        }

        boolean swapped;

        ListIterator<SimilarityResult> current= list.getIterador();
        while(current.hasNext() ){
            ListIterator<SimilarityResult> nextNode = current.getNext();
            if (current.getContent().compareTo(nextNode.getContent()) > 0) {
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
            if (current.getContent().compareTo(nextNode.getContent()) > 0) {
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
