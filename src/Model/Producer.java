/**
 * @author Jakob Hagman
 * Program SYS21
 */
package Model;
import Controller.Controller;
import java.util.ArrayList;
import java.util.Random;
public class Producer implements Runnable{
    private Buffer<FoodItem> foodBuffer;
    private ArrayList<FoodItem> foodItems;
    private Controller controller;
    private boolean isWorking;
    private String company;
    private int threadDelay = 1000;
    private Random rand;
    private Thread thread;
    public Producer(Controller controller, ArrayList<FoodItem> foodItems, String company) {
        this.foodItems = foodItems;
        this.controller = controller;
        this.company = company;
    }

    /**
     * This method kills the thread
     */
    public void stopWorking() {
        isWorking = false;
    }

    /**
     * This method starts the thread and updates booelan is working to true
     */
    public void startWorking() {
        isWorking = true;
        if(thread == null){
            thread = new Thread(this);
            thread.start();
        }
    }


    /**
     * this method gets called by the thread
     * it randomizes a number and produces the item corresponding to whatever item that is in the arraylist of items.
     */
    @Override
    public void run() {
        //System.out.println("work has started");
        while (isWorking) {
            try {
                Thread.sleep(threadDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            rand = new Random();
            int item = rand.nextInt(foodItems.size());
            controller.putFoodItemInBuffer(foodItems.get(item));
        }
        thread = null;
    }
}
