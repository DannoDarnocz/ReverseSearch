package reversesearch.structures.doublyLinkedList;

public class doublyLinkedList {// cualquier numero
    private doublyLinkedNode first; // raiz
    private doublyLinkedNode last; // ultimo nodo

    // metodos vistos en clase
    public void addStart(int num) {
        first = new doublyLinkedNode(num);
        if(first.getPrev() == null){
            last=first;
        }
    }

    public void addEnd(int num){
        if(first==null){
            last=first=new doublyLinkedNode(num,null,first);
        }else{
            last.setNext(new doublyLinkedNode(num));
            last=last.getNext();
        }
    }

    public void addOrdered(int num){
        if(first==null||num <  first.getContent()){
            addStart(num);
            return;
        }else {
            doublyLinkedNode tmp = first;

            while (num>tmp.getContent() && tmp.getNext() != null){
                tmp = tmp.getNext();
            }

            doublyLinkedNode newNode = new doublyLinkedNode(num, tmp.getPrev(), tmp.getNext());
            tmp.setNext(newNode);

            // si es el ultimo, ponerlo como ultimo tambien
            if(tmp.getNext()==null) {
                last = newNode;
            }
        }

    }
}
