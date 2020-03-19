package collections;

import data_structures.EndlessArray;
import data_structures.EndlessArrayIterator;
import data_structures.EndlessArrayReverseIterator;

import java.util.*;

/**
 * My navigable set
 * @author Anatoly Antonov
 * @version 1.0
 * @param <T> Type of contains element
 */
public class MyNavigableSet<T> extends AbstractSet<T> implements NavigableSet<T> {

    private EndlessArray<T> array = new EndlessArray();

    private int size;

    private Comparator<? super T> comparator;

    private int direct;

    /**
     * <p>Constructor with comparator and num, that define direction</p>
     * @param comparator Comparator, that compare elements
     * @param direct 1, with direct order, -1 with reverse order
     */
    public MyNavigableSet(Comparator<? super T> comparator, int direct){
        this.array = new EndlessArray<T>();
        size = 0;
        direct = 1;
        this.comparator = comparator;
    }

    /**
     * @return iterator
     */
    @Override
    public Iterator<T> iterator() {
        return new EndlessArrayIterator<T>(array);
    }

    /**
     * @return Amount of elements
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * <p>Returns the greatest element less then given element or null if there no such file</p>
     * @param t Element of set
     * @return Element standing behind t in order
     */
    @Override
    public T lower(T t) {
        int index = array.indexOf(t);
        if(index <= 0) return null;
        return array.get(index - 1);
    }

    /**
     * <p>Returns the greatest element less then or equal to given element</p>
     * @param t given element
     * @return result
     */
    @Override
    public T floor(T t) {
        T r = null;
        boolean flag = true;
        for(int i = 0; i < size && flag; i++){
            if(direct * comparator.compare(t, array.get(i)) > 0){
                flag = false;
            }
            else {
                r = array.get(i);
            }
        }
        return r;
    }

    /**
     * <p>Returns the least element greater then or equal to given element</p>
     * @param t given element
     * @return result
     */
    @Override
    public T ceiling(T t) {
        T r = null;
        boolean flag = true;
        for(int i = size - 1; i >= 0 && flag; i--){
            if(direct * comparator.compare(t, array.get(i)) < 0){
                flag = false;
            }
            else {
                r = array.get(i);
            }
        }
        return r;
    }

    /**
     * <p>Returns the least element greater then given element</p>
     * @param t given element
     * @return result
     */
    @Override
    public T higher(T t) {
        int index = array.indexOf(t);
        if(index == size - 1 || index == -1) return null;
        return array.get(index + 1);
    }

    /**
     * <p>Removes and returns least element</p>
     * @return least element
     */
    @Override
    public T pollFirst() {
        if(size == 0) return null;
        T r = array.get(0);
        array.remove(0);
        size--;
        return r;
    }

    /**
     * <p>Removes and returns greatest element</p>
     * @return greatest element
     */
    @Override
    public T pollLast() {
        if(size == 0) return null;
        T r = array.get(size - 1);
        array.remove(size - 1);
        size--;
        return r;
    }

    /**
     * <p>Return set with reverse direction</p>
     * @return new set
     */
    @Override
    public NavigableSet<T> descendingSet() {
        EndlessArray<T> n = new EndlessArray<>();
        for(T e : array){
            n.add(e, 0);
        }
        MyNavigableSet<T> reverseSet = new MyNavigableSet<>(comparator, direct * -1);
        reverseSet.array = array;
        reverseSet.size = size;
        return reverseSet;
    }

    /**
     * <p>Return iterator with reverse direction</p>
     * @return descending iterator
     */
    @Override
    public Iterator<T> descendingIterator() {
        return new EndlessArrayReverseIterator<>(array);
    }

    /**
     * <p>returns part of set</p>
     * @param fromElement Start element
     * @param fromInclusive Include start element
     * @param toElement Finish element
     * @param toInclusive Include finish element
     * @return new set
     */
    @Override
    public NavigableSet<T> subSet(T fromElement, boolean fromInclusive, T toElement, boolean toInclusive) {
        EndlessArray<T> n = new EndlessArray<>();
        for(int i = array.indexOf(fromElement) + (fromInclusive ? 0 : 1); i <= array.indexOf(toElement) - (toInclusive ? 0 : 1); i++){
            n.add(array.get(i));
        }
        MyNavigableSet<T> set = new MyNavigableSet<>(comparator, direct);
        set.array = array;
        set.size = size;
        return set;
    }

    /**
     * <p>Returns subset with start in first element</p>
     * @param toElement Finish element
     * @param inclusive Include finish element
     * @return new set
     */
    @Override
    public NavigableSet<T> headSet(T toElement, boolean inclusive) {
        return subSet(first(), true, toElement, inclusive);
    }

    /**
     * <p>Returns subset with finish in last element</p>
     * @param fromElement Start element
     * @param inclusive Include start element
     * @return new set
     */
    @Override
    public NavigableSet<T> tailSet(T fromElement, boolean inclusive) {
        return subSet(fromElement, inclusive, last(), true);
    }

    /**
     * <p>Subset with included start element and excluded finish element</p>
     * @param fromElement Start element
     * @param toElement Finish element
     * @return new set
     */
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        return subSet(fromElement, true, toElement, false);
    }

    /**
     * <p>Head set with excluded finish element</p>
     * @param toElement Finish element
     * @return new set
     */
    @Override
    public SortedSet<T> headSet(T toElement) {
        return headSet(toElement, false);
    }

    /**
     * <p>Tail set with included start element</p>
     * @param fromElement Start element
     * @return new set
     */
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        return tailSet(fromElement, true);
    }

    /**
     * <p>Returns comparator</p>
     * @return comparator
     */
    @Override
    public Comparator<? super T> comparator() {
        return comparator;
    }

    /**
     * <p>Returns least element</p>
     * @return element
     */
    @Override
    public T first() {
        return array.get(0);
    }

    /**
     * <p>Returns greatest element</p>
     * @return element
     */
    @Override
    public T last() {
        return array.get(size - 1);
    }

    /**
     * <p>Add element to set</p>
     * @param t Element to add
     * @return Is element added
     */
    @Override
    public boolean add(T t) {
        for(int i = 0; i < size; i++){
            if(direct * comparator.compare(array.get(i),t) > 0){
                array.add(t, i);
                return true;
            }
            if(comparator.compare(array.get(i),t) == 0){
                return false;
            }
        }
        array.add(t);
        return true;
    }

    /**
     * <p>Removes element</p>
     * @param o element to remove
     * @return has element removed
     */
    @Override
    public boolean remove(Object o) {
        T r = null;
        try{
            r = (T)o;
        }
        catch (ClassCastException e){
            return false;
        }
        int index = array.indexOf(r);
        if(index == -1){
            return false;
        }
        array.remove(index);
        size--;
        return true;
    }

    /**
     * <p>Is element contains in this set</p>
     * @param o Element
     * @return Boolean
     */
    @Override
    public boolean contains(Object o) {
        T r = null;
        try{
            r = (T)o;
        }
        catch (ClassCastException e){
            return false;
        }
        int index = array.indexOf(r);
        return index != -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MyNavigableSet<?> that = (MyNavigableSet<?>) o;
        if(that.size != size) return false;
        for(int i = 0; i < size; i++){
            if(!array.get(i).equals(that.array.get(i))){
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), array, size, comparator, direct);
    }
}
