public class LinkedListDeque<T> implements Deque<T>
{
    private class IntNode
    {
        public T item;
        public IntNode next;
        public IntNode prev;

        private IntNode(T i)
        {
            prev = this;
            item = i;
            next = this;
        }

        private IntNode(T i, IntNode p, IntNode n)
        {
            prev = p;
            item = i;
            next = n;
        }
    }

    private IntNode sentinel    ;
    private int size;

    public LinkedListDeque()
    {
        sentinel = new IntNode(null);
        size = 0;
    }

    @Override
    public void addFirst(T item)
    {
        sentinel.next = new IntNode(item, sentinel, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
        size += 1;
    }
    @Override
    public void addLast(T item)
    {
        sentinel.prev = new IntNode(item, sentinel.prev, sentinel);
        sentinel.prev.prev.next = sentinel.prev;
        size += 1;
    }

    @Override
    public boolean isEmpty()
    {
        if (size == 0)
        {return true;}
        else
        {return false;}
    }

    @Override
    public int size()
    {return size;}

    @Override
    public void printDeque()
    {
        IntNode p = sentinel.next;
        for (int i = 0; i < size; i++)
        {
            System.out.print(p.item + " ");
            p = p.next;
        }
    }

    @Override
    public T removeFirst()
    {
        if (size == 0)
        {return null;}
        T item = sentinel.next.item;
        IntNode p = sentinel.next.next;
        sentinel.next = p;
        p.prev = sentinel;
        size -= 1;
        return item;
    }

    @Override
    public T removeLast()
    {
        if (size == 0)
        {return null;}
        T item = sentinel.prev.item;
        IntNode p = sentinel.prev.prev;
        sentinel.prev = p;
        p.next = sentinel;
        size -= 1;
        return item;
    }

    @Override
    public T get(int index)
    {
        IntNode p = sentinel.next;
        if (index > size - 1)
        {return null;}
        for (int i = 0; i < index; i++)
        {
            p = p.next;
        }
        return p.item;
    }

    public T getRecursivehelper(IntNode p, int index)
    {
        if (index == 0)
        {
            return p.item;
        }
        return getRecursivehelper(p.next, index - 1);
    }

    public T getRecursive(int index)

    {
        if (index > size - 1)
        {return null;}
        IntNode p = sentinel.next;
        return getRecursivehelper(p, index);
    }
}
