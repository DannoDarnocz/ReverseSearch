package reversesearch.structure.doublylinkedlist;

import reversesearch.likenessmethod.SimilarityResult;

public class BubbleSort implements SortMethod {
    @Override
    public void sort(DoublyLinkedList<SimilarityResult> list) {
        if (list == null || list.size() <= 1) {
            return; // No need to sort
        }

        boolean swapped;
        do {
            swapped = false;
            DoublyLinkedNode<SimilarityResult> current = list.getFirst();
            while (current != null && current.getNext() != null) {
                DoublyLinkedNode<SimilarityResult> nextNode = current.getNext();
                if (current.getContent().compareTo(nextNode.getContent()) > 0) {
                    // Swap the contents of the nodes
                    SimilarityResult temp = current.getContent();
                    current.setContent(nextNode.getContent());
                    nextNode.setContent(temp);
                    swapped = true;
                }
                current = nextNode;
            }
        } while (swapped);
    }
}
