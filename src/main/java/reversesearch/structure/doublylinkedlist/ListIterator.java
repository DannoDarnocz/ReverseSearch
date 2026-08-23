package reversesearch.structure.doublylinkedlist;

public class ListIterator<T> {
    private DoublyLinkedNode<T> current;

    ListIterator(DoublyLinkedNode first) {
        current = first;
    }
public boolean hasNext() {
    if(current != null){
        return true;
    }else{
        return false;
    }
}
public T next() {
    T result = current.getContent();
    current = current.getNext();
return result;
}
}
