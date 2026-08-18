package reversesearch.structure.doublylinkedlist;

public class DoublyLinkedList<T> {// cualquier numero
    private DoublyLinkedNode first; // raiz
    private DoublyLinkedNode last; // ultimo nodo
    private int size; //para metodo tamano, elementos de la lista

    // metodos vistos en clase
    public void addStart(T element) {
      if(first==null){
         last = first = new DoublyLinkedNode(element,null,first);

      } else {
          DoublyLinkedNode oldFirst = first;
          first = new DoublyLinkedNode(element, null, oldFirst);
          oldFirst.setPrev(first);
          }
      size++;
    }

    public void addEnd(T element){
        if(first==null){
            last=first=new DoublyLinkedNode(element,null,first);
        }else{
            last.setNext(new DoublyLinkedNode(element,last,null));
            last=last.getNext();
        }
        size++;
    }
    public void removePosition(int pos){
        int cont = 0;
        DoublyLinkedNode<T> current=first;
        while(current != null && cont != pos){
            current=current.getNext();
            cont++;
        }
        if(current == null){
            return; // cortar termino en null
        }
        if(current == first){
            first = current.getNext();
            if(first == null){
                last = null;
            }
        }
        else if(current == last){
            last = current.getPrev();
            last.setNext(null);
        }else{
                DoublyLinkedNode anterior = current.getPrev();
                DoublyLinkedNode siguiente = current.getNext();
                anterior.setNext(siguiente);
                siguiente.setPrev(anterior);
            }
        size--;
    }
    public void removeElement(T element){
        DoublyLinkedNode<T> current = first;
        while(current != null && !current.getContent().equals(element)){
            //*Importante implementar el metodo equals en Image/Histogram(comparacion segura)

            current=current.getNext();
        }
        if(current == null){
            return; //cortar si current termino en null
        }
        if(current == first){
            first = current.getNext();
            if(first == null){
                last = null;
            }
        }
        else if(current == last){
            last = current.getPrev();
            last.setNext(null);
        }else{
            DoublyLinkedNode anterior = current.getPrev();
            DoublyLinkedNode siguiente = current.getNext();
            anterior.setNext(siguiente);
            siguiente.setPrev(anterior);
        }
        size--;
    }
    public int size(){
        return size;
    }
    public ListIterator<T> getIterador() {
        return new ListIterator<T>(first);
    }


// no lo implemente
    //el algoritmo de ordenamiento es mejor en otra clase
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
