package dk.dtu.compute.course02324.assignment4.functions.implementations;

import dk.dtu.compute.course02324.assignment4.functions.types.List;

import jakarta.validation.constraints.NotNull;
import java.util.Comparator;

// TODO Assignment 4a: delete this file! We use Java standard Collections now

/**
 * An implementation of the interface {@link List} based on basic Java
 * arrays, which dynamically are adapted in size when needed.
 *
 * @param <E> the type of the list's elements.
 */
public class ArrayList<E> implements List<E> {

    /**
     * Constant defining the default size of the array when the
     * list is created. The value can be any (strictly) positive
     * number. Here, we have chosen <code>10</code>, which is also
     * Java's default for some array-based collection implementations.
     */
    final private int DEFAULT_SIZE = 10;

    /**
     * Current size of the list.
     */
    private int size = 0;

    /**
     *  The array for storing the elements of the
     */
    private E[] list = createEmptyArray(DEFAULT_SIZE);

    @Override
    public void clear() {
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public @NotNull E get(int pos) throws IndexOutOfBoundsException {
        if (pos < 0 || pos >= size) {
            throw new IndexOutOfBoundsException();
        }
        return list[pos];
    }

    @Override
    public E set(int pos, @NotNull E e) throws IndexOutOfBoundsException {
        if (pos < 0 || pos >= size) {throw new IndexOutOfBoundsException();}
        if (e == null) {throw new IllegalArgumentException();}
        E oldValue = list[pos];
        list[pos] = e;
        return oldValue;
    }

    @Override
    public boolean add(@NotNull E e) throws IllegalArgumentException{
        if (e == null) {throw new IllegalArgumentException();}
        if (size == list.length) {
            E[] newArray = createEmptyArray(size*2);
            System.arraycopy(list, 0, newArray, 0, size);
            list = newArray;
        }
        list[size] = e;
        size++;
        return true;
    }

    @Override
    public boolean add(int pos, @NotNull E e) throws IndexOutOfBoundsException {
        if (e == null) {throw new IllegalArgumentException();}
        if (pos < 0 || pos > size) {throw new IndexOutOfBoundsException();}
        if (size == list.length) {
            E[] newArray = createEmptyArray(size*2);
            System.arraycopy(list, 0, newArray, 0, size);
            list = newArray;
        }
        shiftElementsUpFrom(pos);
        list[pos] = e;
        size++;
        return true;
    }

    @Override
    public E remove(int pos) throws IndexOutOfBoundsException {
        if (pos < 0 || pos >= size) {throw new IndexOutOfBoundsException();}
        E oldvalue = list[pos];
        shiftElementsDownTo(pos);
        size--;
        list[size] = null;
        return oldvalue;
    }

    @Override
    public boolean remove(E e) throws IllegalArgumentException {
        if (e == null) {throw new IllegalArgumentException();}
        for (int i = 0; i < size; i++) {
            if (list[i].equals(e)) {
                shiftElementsDownTo(i);
                size--;
                list[size] = null;
                return true;
            }
        }
        return false;
    }

    @Override
    public int indexOf(E e) throws IllegalArgumentException {
        if (e == null) {throw new IllegalArgumentException();}
        for (int i = 0; i < size; i++) {
            if (list[i].equals(e)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void sort(@NotNull Comparator<? super E> c) throws IllegalArgumentException {
        if (c == null) {throw new IllegalArgumentException();}
        for (int i = 0; i < size-1; i++){
            for(int j = 0; j < size-1-i; j++){
                if(c.compare(list[j], list[j+1]) > 0){
                    E temp = list[j];
                    list[j] = list[j + 1];
                    list[j+1] = temp;
                }
            }
        }
    }

    /**
     * Creates a new array of type <code>E</code> with the given size.
     *
     * @param length the size of the array
     * @return a new array of type <code>E</code> and the given length
     */
    private E[] createEmptyArray(int length) {
        // there is unfortunately no really easy and elegant way to initialize
        // an array with a type coming in as a generic type parameter, but
        // the following is simple enough. And it is OK, since the array
        // is never passed out of this class.
        return (E[]) new Object[length];
    }

    private void shiftElementsUpFrom(int pos){
        for (var i = size-1; i >= pos; i--) {
            list[i+1] = list[i];
        }
    }
    private void shiftElementsDownTo(int pos) {
        for (var i = pos; i < size - 1 ; i++) {
            list[i] = list[i+1];
        }
    }

}
