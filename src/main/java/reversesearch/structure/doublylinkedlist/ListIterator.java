package reversesearch.structure.doublylinkedlist;

public class ListIterator<T> {
    private DoublyLinkedNode<T> current;

    ListIterator(DoublyLinkedNode first) {
        current = first;
    }
public boolean hasNext() {
    if(current.getNext() != null){
        return true;
    }else{
        return false;
    }
}
public ListIterator<T>  getNext() {
    if(current.getNext() == null) return null;
    return new ListIterator(current.getNext());
}

public T getContent(){
    return current.getContent();
}

public void setContent(T value){
    current.setContent(value);
}
}
