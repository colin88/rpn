package com.zw.rpn;
 
import com.zw.rpn.exception.RPNException;
 
/**
 * This interface defines the operations for a model used for RPN calculation. The assumption is a LIFO stack will be 
 * used as the implementation. 
 */
public interface RPNModel<E> {
 
    /**
     * Used for adding an item to the model.
     * 
     * @param item
     *            The item to be added. Must be non-<code>null</code>.
     */
    public void add(E item);
     
    /**
     * @return The item
     */
    public E remove() throws RPNException;
     
    /**
     * @return <code>true</code> if the model is currently empty.
     */
    public boolean isEmpty();
     
    /**
     * @return The number of items currently in this model.
     */
    public int size();
 
}