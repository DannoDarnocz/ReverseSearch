package reversesearch.structure.doublylinkedlist;

import reversesearch.likenessmethod.SimilarityResult;

import java.util.List;

public class MergeSort implements SortMethod {
    @Override
    public void sort(DoublyLinkedList<SimilarityResult> list) {
        if (list==null || list.size() <= 1) {
            return;
        }
        DoublyLinkedList<SimilarityResult> sorted = mergeSortHelper(list);
        list.clear();
        DoublyLinkedNode<SimilarityResult> current = sorted.getFirst();
        while (current != null) {
            list.addEnd(current.getContent());
            current = current.getNext();
        }
    }
    private DoublyLinkedList<SimilarityResult> mergeSortHelper(DoublyLinkedList<SimilarityResult> list) {
        if (list.size() <= 1) {
            return list;
        }
        DoublyLinkedList<SimilarityResult>[] halves = split(list);
        DoublyLinkedList<SimilarityResult> left = halves[0];
        DoublyLinkedList<SimilarityResult> right = halves[1];
        left = mergeSortHelper(left);
        right = mergeSortHelper(right);
        return merge(left, right);
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
    private DoublyLinkedList<SimilarityResult> merge(DoublyLinkedList<SimilarityResult> left, DoublyLinkedList<SimilarityResult> right) {
        DoublyLinkedList<SimilarityResult> result = new DoublyLinkedList<>();
        DoublyLinkedNode<SimilarityResult> leftCurrent = left.getFirst();
        DoublyLinkedNode<SimilarityResult> rightCurrent = right.getFirst();
        while (leftCurrent != null && rightCurrent != null) {
            if (leftCurrent.getContent().compareTo(rightCurrent.getContent()) <= 0) {
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
