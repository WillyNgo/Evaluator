package com.williamngo.business;

import java.util.ArrayList;
import java.util.EmptyStackException;

/**
 *
 * @author William Ngo
 */
public class MyStack<T> {
    
    private ArrayList<T> list;
    private int index;
    
    public MyStack(){
        list = new ArrayList<>();
        index = -1;
    }
    
    /**
     * Returns true if the list is empty.
     * @return 
     */
    public boolean isEmpty(){
        return (list.size() == 0);
    }
    
    /**
     * Returns the object at the top of the stack without removing it from
     * the stack. Returns null if empty
     * @return 
     */
    public T peek(){
        if(!isEmpty()){
            return list.get(index);
        }
        else{
            throw new EmptyStackException();
        }
    }
    
    /**
     * Removes the item on the top of the list.
     * @return 
     */
    public T pop() {
        if (!isEmpty()) {
            T obj = list.get(index);

            list.remove(index);
            index--;

            return obj;
        }
        else{
            throw new EmptyStackException();
        }
    }
    
    /**
     * Push the item on the top of the list.
     * @param item
     * @return 
     */
    public T push(T item){
        if(!item.equals("")){
            list.add(item);
            index++;
        }
        return item;
    }
    
    /**
     * Retrieves item at specified index
     * 
     * @param index
     * @return 
     */
    public T get(int index){
        if(!isEmpty()){
            return list.get(index);
        }
        else{
            throw new EmptyStackException();
        }
    }
    
    /**
     * Returns the index position of the specified objects;
     * @param o
     * @return 
     */
    public int getIndex(Object o){
        return list.lastIndexOf(o);
    }
    
    public void display(){
        for(T t : list){
            System.out.println(t + " ");
        }
    }
}
