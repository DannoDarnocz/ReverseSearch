package reversesearch.structure.doublylinkedlist;

public class DoublyLinkedNode<T> {
    T content; // lo que contiene el nodo
    private DoublyLinkedNode next; // nodo siguiente
    private DoublyLinkedNode prev; // nodo anterior

    DoublyLinkedNode(T num){
        this.content = num;
    }

    DoublyLinkedNode(T num, DoublyLinkedNode prev, DoublyLinkedNode next){
        this.content = num;
        this.prev = prev;
        this.next = next;
    }

    public DoublyLinkedNode getNext(){
        return next;
    }

    public void setNext(DoublyLinkedNode node){
        next = node;
    }

    public DoublyLinkedNode getPrev() {
        return prev;
    }

    public void setPrev(DoublyLinkedNode node){
        prev=node;
    }

    public T getContent(){
        return content;
    }

    public void setContent(T num){
        content = num;
    }
}
