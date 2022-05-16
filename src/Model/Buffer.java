/**
 * @author Jakob Hagman
 * Program SYS21
 */

package Model;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;

public class Buffer <T> {
    private LinkedList <T> buffer;
    private int maxItems = 100;
    private Semaphore mutex = new Semaphore(1);
    private Semaphore currentAmount = new Semaphore(0);
    private Semaphore maxAvailable = new Semaphore(maxItems);


    public Buffer(){
        buffer = new LinkedList <T>();
    }

    /**
     * Takes fooditems and places them in the last spot in the buffer
     * @param obj - The foodItems
     */
    public void put(T obj) {
        try{
            currentAmount.release();
            maxAvailable.acquire();
            mutex.acquire();
            buffer.addLast(obj);
            mutex.release();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * Gets the fooditem first in the buffer
     * will just wait in case the buffer is empty
     * @return FoodItem
     * @throws InterruptedException
     */
    public T getItem() throws InterruptedException {
        T item = null;
        try{
            currentAmount.acquire();
            maxAvailable.release();
            mutex.acquire();
            while(buffer.isEmpty()) {
                wait();
            }
            item = buffer.pollFirst();
            mutex.release();
        }catch(Exception e){
            e.printStackTrace();
        }
        return item;
    }

    public int size() {
        return buffer.size();
    }
}

