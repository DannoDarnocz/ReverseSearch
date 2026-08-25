package reversesearch.structure.doublylinkedlist;

import reversesearch.likenessmethod.SimilarityResult;

public class MergeSort implements SortMethod {
    @Override
    public void sort(DoublyLinkedList<SimilarityResult> list) {
        if (list == null || list.size() <= 1) {
            return;
        }
        DoublyLinkedList sortedList = list;
        DoublyLinkedList rightList = new DoublyLinkedList();
        DoublyLinkedList leftList = new DoublyLinkedList();
        DoublyLinkedNode current = sortedList.getFirst();
        int mid = sortedList.size() / 2;

    }
}
