package reversesearch.structures.doublyLinkedList;

public class doublyLinkedNode{
    int content; // lo que contiene el nodo
    private doublyLinkedNode next; // nodo siguiente
    private doublyLinkedNode prev; // nodo anterior

    doublyLinkedNode(int num){
        this.content = num;
    }

    doublyLinkedNode(int num, doublyLinkedNode prev,doublyLinkedNode next){
        this.content = num;
        this.prev = prev;
        this.next = next;
    }

    public doublyLinkedNode getNext(){
        return next;
    }

    public doublyLinkedNode setNext(doublyLinkedNode node){
        next = node;
    }

    public doublyLinkedNode getPrev() {
        return prev;
    }

    public doublyLinkedNode setPrev(doublyLinkedNode node){
        prev=node;
    }

    public int getContent(){
        return content;
    }

    public void setContent(int num){
        content = num;
    }
}
