package reversesearch.structure.doublylinkedlist;

public class ListIterator<T> {
    private DoublyLinkedNode<T> current;

    ListIterator(DoublyLinkedNode first) {
        current = first;
    }
boolean hasNext() {
    if(current != null){
        return true;
    }else{
        return false;
    }
}
T next() {
    T result = current.getContent();
    current = current.getNext();
return result;
}
}
