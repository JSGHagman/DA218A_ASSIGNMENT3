
/**
 * @author Jakob Hagman
 * Program SYS21
 */
package Model;
import Controller.Controller;
import java.util.ArrayList;
public class Consumer implements Runnable{
    private int threadDelay = 1000;
    private int maxItems = 10;
    private double maxVolume = 200.0;
    private double maxWeight = 300.0;
    private int n = 0;
    private double v, w = 0.0;
    private Controller controller;
    private ArrayList <FoodItem> fList;
    private String company;
    private boolean isWorking, full, isSelected;
    private Thread thread;

    public Consumer(Controller controller, String company){
        this.fList = new ArrayList<>();
        this.controller = controller;
        this.company = company;
    }

    /**
     * This method will start the thread.
     */
    public void startWork(){
        isWorking = true;
        if(thread == null){
            thread = new Thread(this);
            thread.start();
        }
    }

    /**
     * Will update isWorking to false and thus, kill the thread.
     */
    public void stopWork(){
        isWorking = false;
    }

    /**
     *
     * @return String - The name of the company
     */
    public String getCompany(){
        return company;
    }

    /**
     * Checks if the items loaded onto the truck is within the trucks limits
     * @return boolean - if the truck is full or not
     */
    public boolean checkIfFull(){
        if(n < maxItems && w < maxWeight && v < maxVolume){
            full = false;
        } else{
            full = true;
        }
        return full;
    }

    /**
     * Will check if the checkbox is selected to keep loading
     * @return boolean - if the chackbox to keep loading is selected or not
     */

    public boolean checkIfBoxSelected(){
       isSelected = controller.checkIfKeepLoading(company);
       return isSelected;
    }

    /**
     * This method specifies what work needs to be done by the thread
     * It will take items from the buffer and place in its own ArrayList of FoodItems.
     */
    @Override
    public void run() {
        boolean full;
        while(isWorking){
            String itemList = "";
            try{
                Thread.sleep(threadDelay);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            fList.clear();
            n = 0;
            w = 0.0;
            v = 0.0;
            controller.updateLabels(company, n, w, v,itemList);
            full = checkIfFull();
            while(!full){
                try {
                FoodItem item = controller.getItemFromBuffer();
                fList.add(item);
                w = w + item.getWeight();
                v = v + item.getVolume();
                n = n + 1;
                itemList = itemList + item.getItem() + "\n";
                controller.updateLabels(company, n, w, v, itemList);
                Thread.sleep(threadDelay);
                full = checkIfFull();
                    System.out.println(full);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try{
                Thread.sleep(threadDelay);
            }catch(InterruptedException e){
                e.printStackTrace();
            }

            //kanske måste uppdateras sen
            if(checkIfBoxSelected()){
                try{
                    Thread.sleep(threadDelay);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }else{
                stopWork();
                thread = null;
            }
        }
    }
}
