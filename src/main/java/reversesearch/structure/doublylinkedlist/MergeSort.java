package reversesearch.structure.doublylinkedlist;

import reversesearch.similarity.SimilarityResult;

import java.util.Comparator;

public class MergeSort implements SortMethod {
    @Override
    public void sort(DoublyLinkedList<SimilarityResult> list, Comparator<SimilarityResult> comparator) {
        if (list==null || list.size() <= 1) {
            return;
        }
        DoublyLinkedList<SimilarityResult> sorted = mergeSortHelper(list, comparator);
        list.clear();
        DoublyLinkedNode<SimilarityResult> current = sorted.getFirst();
        while (current != null) {
            list.addEnd(current.getContent());
            current = current.getNext();
        }
    }
    private DoublyLinkedList<SimilarityResult> mergeSortHelper(DoublyLinkedList<SimilarityResult> list, Comparator<SimilarityResult> comparator) {
        if (list.size() <= 1) {
            return list;
        }
        DoublyLinkedList<SimilarityResult>[] halves = split(list);
        DoublyLinkedList<SimilarityResult> left = halves[0];
        DoublyLinkedList<SimilarityResult> right = halves[1];
        left = mergeSortHelper(left,comparator);
        right = mergeSortHelper(right, comparator);
        return merge(left, right, comparator);
    }
    private DoublyLinkedList<SimilarityResult>[] split(DoublyLinkedList<SimilarityResult> list) {
        DoublyLinkedList<SimilarityResult> leftList = new DoublyLinkedList<>();
        DoublyLinkedList<SimilarityResult> rightList = new DoublyLinkedList<>();
        DoublyLinkedNode<SimilarityResult> current = list.getFirst();
        int mid = list.size() / 2;
        for (int i = 0; i < mid; i++) {
            leftList.addEnd(current.getContent());
            current = current.getNext();
        }
        while (current != null) {
            rightList.addEnd(current.getContent());
            current = current.getNext();
        }
        return new DoublyLinkedList[]{ leftList, rightList };
    }
    private DoublyLinkedList<SimilarityResult> merge(DoublyLinkedList<SimilarityResult> left, DoublyLinkedList<SimilarityResult> right, Comparator<SimilarityResult> comparator) {
        DoublyLinkedList<SimilarityResult> result = new DoublyLinkedList<>();
        DoublyLinkedNode<SimilarityResult> leftCurrent = left.getFirst();
        DoublyLinkedNode<SimilarityResult> rightCurrent = right.getFirst();
        while (leftCurrent != null && rightCurrent != null) {
            // comparar para que sea de forma ascendente o descendente
            if (comparator.compare(leftCurrent.getContent(),rightCurrent.getContent()) <= 0) {
                result.addEnd(leftCurrent.getContent());
                leftCurrent = leftCurrent.getNext();
            } else {
                result.addEnd(rightCurrent.getContent());
                rightCurrent = rightCurrent.getNext();
            }
        }
        while (leftCurrent != null) {
            result.addEnd(leftCurrent.getContent());
            leftCurrent = leftCurrent.getNext();
        }
        while (rightCurrent != null) {
            result.addEnd(rightCurrent.getContent());
            rightCurrent = rightCurrent.getNext();
        }
        return result;
    }
}
