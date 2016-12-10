package com.williamngo.business;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.NoSuchElementException;

/**
 *
 * @author 1435707
 */
public class MyQueue<T> {
    
    private ArrayList<T> list;
    private int startIndex;
    private int endIndex;
    
    public MyQueue(){
       this.list = new ArrayList<>();
       this.startIndex = 0;
       this.endIndex = startIndex + 1;
    }
    
    
    /**
     * Adds the element at the end of the queue.
     * 
     * @param t
     */
    public void push(T t){
        if(!t.equals("")){
            list.add(t);
            endIndex++;
        }
    }
    
    /**
     * Returns the first element of queue without removing it, or returns
     * null if the queue is empty.
     * @return 
     */
    public T peek(){
        if(!isEmpty()){
            return list.get(startIndex);
        }
        else{
            throw new EmptyStackException();
        }
    }
    
    /**
     * Retrieves item at specified index
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
     * Retrieves the first element from the queue and removes it.
     * @return The first element of the queue.
     */
    public T pop(){
        if(!isEmpty()){
            T t = list.get(startIndex);
            startIndex++;
            return t;
        }
        else{
            throw new NoSuchElementException("Error grabbing first element - Queue is empty.");
        }
    }
    
    /**
     * Determines if the list is empty by verifying if the start index and the
     * end index are in the same position. Returns true if both indexes are
     * in the same place.
     * @return boolean if start and end index are the same.
     */
    public boolean isEmpty(){
        return (startIndex == endIndex);
    }
    
    /**
     * Returns number of elements in the list
     * @return 
     */
    public int getSize(){
        return list.size();
    }    
    
    /**
     * Overridden toString method. It will display the content of the the queue.
     * @return 
     */
    @Override
    public String toString(){
        String values = "";
        for(T str : list){
            values = values + str + " ";
        }
        
        return values;
    }
}
