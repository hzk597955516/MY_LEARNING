public class ArrayDeque<T> implements Deque<T>
{
    private T[] items;
    private int size;
    private int nextfirst;
    private int nextlast;

    public ArrayDeque()
    {
        items = (T[])new Object[8];
        size = 0;
        nextfirst = 3;
        nextlast = 4;
    }

    private void resize(int capacity)
    {
        T[] a = (T[])new Object[capacity];

        if (capacity > items.length)
        {
            if (nextfirst < nextlast)
            {
                System.arraycopy(items, nextlast, a, 0, items.length - nextlast);
                System.arraycopy(items, 0, a, items.length - nextlast, nextfirst + 1);
            }
            else
            {
                System.arraycopy(items, nextlast, a, 0,size);
            }
        }
        else
        {
            if (nextlast < nextfirst)
            {
                if (nextfirst != items.length - 1)
                {
                    System.arraycopy(items, nextfirst + 1, a, 0, items.length - nextfirst - 1);
                    System.arraycopy(items, 0, a, items.length - nextfirst - 1, size + nextfirst + 1 - items.length);
                }
                else
                {
                    System.arraycopy(items, 0, a, 0, size);
                }
            }
            else
            {
                System.arraycopy(items, nextfirst + 1, a, 0, size);
            }
        }
        nextfirst = a.length - 1;
        nextlast = size;
        items = a;
    }

    @Override
    public void addFirst(T item)
    {
        if (size == items.length)
        {this.resize(2 * items.length);}

        items[nextfirst] = item;
        size += 1;

        if (nextfirst == 0)
        {nextfirst = items.length - 1;}
        else
        {nextfirst -= 1;}
    }

    @Override
    public void addLast(T item)
    {
        if (size == items.length)
        {this.resize(2 * items.length);}

        items[nextlast] = item;
        size += 1;

        if (nextlast == items.length - 1)
        {nextlast = 0;}
        else
        {nextlast += 1;}
    }

    @Override
    public T removeFirst()
    {
        T x;
        if (nextfirst == items.length - 1)
        {
            x = items[0];
            nextfirst = 0;
        }
        else
        {
            x = items[nextfirst + 1];
            nextfirst += 1;
        }
        size -= 1;
        if (size <= items.length/4)
        {this.resize(items.length/2);}
        return x;
    }

    @Override
    public T removeLast()
    {
        T x;
        if (nextlast == 0)
        {
            x = items[items.length - 1];
            nextlast = items.length - 1;
        }
        else
        {
            x = items[nextlast - 1];
            nextlast -= 1;
        }
        size -= 1;
        if (size <= items.length/4)
        {this.resize(items.length/2);}
        return x;
    }

    @Override
    public T get(int index)
    {
        if (size == 0)
        {return null;}

        if (nextfirst <= nextlast)
        {
            if (nextfirst + 1 + index > nextfirst + size)
            {
                return null;
            }
            return items[nextfirst + 1];
        }
        else if (nextfirst + 1 + index > items.length - 1)
        {
            return items[nextfirst + 1 + index - items.length];
        }
        return items[nextfirst + 1];
    }

    @Override
    public int size()
    {return size;}

    @Override
    public boolean isEmpty()
    {
        if (size == 0)
        {return true;}
        return false;
    }

    @Override
    public void printDeque()
    {
        for (int i = 1; i <= size; i++)
        {
            if (nextfirst + i > items.length - 1)
            {
                System.out.println(items[nextfirst + i - items.length]);
            }
            else
            {System.out.println(items[nextfirst + i]);}
        }
    }
}
