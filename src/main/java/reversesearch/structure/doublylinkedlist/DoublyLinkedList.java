package reversesearch.structure.doublylinkedlist;

public class DoublyLinkedList {// cualquier numero
    private DoublyLinkedNode first; // raiz
    private DoublyLinkedNode last; // ultimo nodo

    // metodos vistos en clase
    public void addStart(int num) {
        first = new DoublyLinkedNode(num);
        if(first.getPrev() == null){
            last=first;
        }
    }

    public void addEnd(int num){
        if(first==null){
            last=first=new DoublyLinkedNode(num,null,first);
        }else{
            last.setNext(new DoublyLinkedNode(num));
            last=last.getNext();
        }
    }

    /*public void addOrdered(int num){
        if(first==null||num <  first.getContent()){
            addStart(num);
            return;
        }else {
            DoublyLinkedNode tmp = first;

            while (num>tmp.getContent() && tmp.getNext() != null){
                tmp = tmp.getNext();
            }

            DoublyLinkedNode newNode = new DoublyLinkedNode(num, tmp.getPrev(), tmp.getNext());
            tmp.setNext(newNode);

            // si es el ultimo, ponerlo como ultimo tambien
            if(tmp.getNext()==null) {
                last = newNode;
            }
        }

    }*/
}
