import org.w3c.dom.Node;
import java.util.Iterator;
import java.util.NoSuchElementException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or .
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Queue <Item> implements  Iterable <Item> {
    private Node<Item> first;
    private Node<Item> last;
    private int n;

    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
    }

    public Queue() {
        first = null;
        last = null;
        n = 0;
    }

    public boolean isEmpty() {
        // o metodo boolean só retorna true ou false
        return first == null;

    }

    public int size() {
        return n;
    }

    public Item peek() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        return first.item;

    }

    public void enqueue(Item item) {
        Node<Item> oldLast = last;
        last = new Node<Item>();
        last.item = item;
        last.next = null;
        if (isEmpty()) first = last;
        else oldLast.next = last;
        n++;
    }

    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        Item item = first.item;
        first = first.next;
        n--;
        if (isEmpty()) last = null;
        return item;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this) {
            s.append(item);
            s.append(' ');

        }
        return s.toString();
    }

    public Iterator<Item> iterator() {
        return new LinkedIterator(first);
    }

    private Node<Item> current;

    private class LinkedIterator implements Iterator<Item> {
        private Queue.Node<Item> current;

        public LinkedIterator(Queue.Node<Item> first) {
            current = first;
        }

        public boolean hasNext() {
            return current != null;

        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main (String [] args) {
        Queue<String> queue = new Queue<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-"))
                queue.enqueue(item);
            else if (queue.isEmpty()) StdOut.print(queue.dequeue() + " ");

        }
        StdOut.println ("("+ queue.size() + " left on queue) ");

    }
}