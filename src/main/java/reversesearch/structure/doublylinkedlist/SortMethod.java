package reversesearch.structure.doublylinkedlist;

import reversesearch.similarity.SimilarityResult;

import java.util.Comparator;

public interface SortMethod {
    void sort(DoublyLinkedList<SimilarityResult> list, Comparator<SimilarityResult> comparator);
}
