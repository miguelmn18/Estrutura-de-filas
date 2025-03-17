import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ResizingArrayQueue <Item> implements Iterator <Item> {
    private static final int INIT_CAPACITY = 8;
    private Item[] q;
    private int n;
    private int first;
    private int last;
    @SuppressWarnings("unchecked")
    public ResizingArrayQueue() {
        q = (Item[]) new Object[INIT_CAPACITY];
        n = 0;
        first = 0;
        last = 0;
    }

    public boolean isEmpty() { // só retorna true ou false;
        return n == 0;
    }

    public int size() {
        return n;

    }

    @SuppressWarnings("unchecked")//suprime alerta do java por conta do cast(do objeto para item)
    /*
     @SuppressWarnings("unchecked") esse marcador suprime os alertar do java
    usado para "dizer" ao compilador que você está ciente do risco(codigo pode contér risco ou insegurança)
    */
    private void resize(int capacity) {
        assert capacity >= n;
        Item[] copy = (Item[]) new Object[capacity];//por fazer esse cast o java informa que a operação pode ser insegura
        for (int i = 0; i < n; i++) {
            copy[i] = q[(first + i) % q.length];
        }
        q = copy;
        first = 0;
        last = n;
    }

    public void enqueue(Item item) {
        if (n == q.length) resize(2 * q.length);
        q[last++] = item;
        if (last == q.length) last = 0;
        n++;

    }

    public Item dequeue() { //aqui tem um erro no qual eu não identificar
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        Item item = q[first];
        q[first] = null;
        n--;
        first++;
        if(first == q.length) first = 0;
        if(n > 0  && n == q.length/4) resize(q.length/2);
        return item;
    }

    public Item peek (){
        if(isEmpty()) throw new NoSuchElementException("Queue underflow");
        return q[first];

    }

    public Iterator<Item> iterator (){
        return new ArrayIterator();
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public Item next() {
        return null;
    }

    private class ArrayIterator implements Iterator <Item> {
        private int i = 0;

        public boolean hasNext() {
            return i < n;
        }
        public Item next(){
            if(!hasNext ()) throw new NoSuchElementException();
            Item item = q[(i + first) % q.length];
            i++;
            return item;
        }

    }

    public static void main (String [] args) {
        ResizingArrayQueue<String> queue = new ResizingArrayQueue<>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) queue.enqueue(item);
            else if (!queue.isEmpty()) StdOut.print(queue.dequeue() + " ");

        }
        StdOut.println ("(" + queue.size() + " left on queue )");
    }
}
