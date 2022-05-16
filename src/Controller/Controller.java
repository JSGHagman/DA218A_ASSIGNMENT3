/**
 * @author Jakob Hagman
 * Program SYS21
 */
package Controller;
import Model.Buffer;
import Model.Consumer;
import Model.FoodItem;
import Model.Producer;
import View.GUISemaphore;
import java.util.ArrayList;
public class Controller {
    private GUISemaphore gui;
    private FoodItem items;
    private Producer scan, arla, axfood;
    private Consumer ica, coop, cg;
    private ArrayList<FoodItem> foodlist =  new ArrayList<>();
    private Buffer buffer;
    /**
     * Constructor for this class
     * starts the gui
     * creates the fooditems.
     * creates three producers and three consumers.
     */
    public Controller(){
        gui = new GUISemaphore(this);
        buffer = new Buffer();
        initializeFoodList();
        scan = new Producer(this, foodlist, "Scan");
        arla = new Producer(this, foodlist, "Arla");
        axfood = new Producer(this, foodlist, "Axfood");
        ica = new Consumer(this, "ICA");
        coop = new Consumer(this,"Coop");
        cg = new Consumer(this, "City Gross");
    }


    /**
     * Creates the arrayList of food items
     */
    public void initializeFoodList(){
        foodlist.add(new FoodItem(1.1,0.5,"Milk"));
        foodlist.add(new FoodItem(0.5,0.1,"Cream"));
        foodlist.add(new FoodItem(1.1,0.5,"Yoghurt"));
        foodlist.add(new FoodItem(2.34,0.66,"Butter"));
        foodlist.add(new FoodItem(3.4,1.2,"Flower"));
        foodlist.add(new FoodItem(3.7,1.8,"Sugar"));
        foodlist.add(new FoodItem(1.55,0.27,"Salt"));
        foodlist.add(new FoodItem(0.6,0.19,"Almonds"));
        foodlist.add(new FoodItem(1.98,0.75,"Bread"));
        foodlist.add(new FoodItem(1.4,0.5,"Donuts"));
        foodlist.add(new FoodItem(1.3,1.5,"Jam"));
        foodlist.add(new FoodItem(4.1,2.5,"Ham"));
        foodlist.add(new FoodItem(6.8,3.9,"Chicken"));
        foodlist.add(new FoodItem(0.87,0.55,"Salad"));
        foodlist.add(new FoodItem(2.46,0.29,"Orange"));
        foodlist.add(new FoodItem(2.44,0.4,"Apple"));
        foodlist.add(new FoodItem(1.3,0.77,"Pear"));
        foodlist.add(new FoodItem(2.98,2.0,"Soda"));
        foodlist.add(new FoodItem(3.74,1.5,"Beer"));
        foodlist.add(new FoodItem(2.0,1.38,"Hotdogs"));
    }

    public ArrayList<FoodItem> getFoodlist() {
        return foodlist;
    }

    /**
     * will take a foodItem from the buffer
     * @return FoodItem - foodItem from the buffer
     */
    public FoodItem getItemFromBuffer() throws InterruptedException {
        gui.setProgressbar(buffer.size());
        return (FoodItem) buffer.getItem();
    }

    /**
     * Puts food item in the buffer and updates gui on how many items are stored in the buffer.
     * @param item
     */
    public void putFoodItemInBuffer(FoodItem item){
        buffer.put(item);
        gui.setProgressbar(buffer.size());
    }


    public void updateLabels(String company, int items, double weight, double volume, String itemList){
        gui.updateLabels(company, items, weight, volume, itemList);
    }


    /**
     * Starts threads, this gets called from the actionlistener
     * @param company - the name of the company which is to start working.
     */
    public void startThread(String company){
        if(company.equals("Scan")){
            scan.startWorking();
        }
        if(company.equals("Arla")){
            arla.startWorking();
        }
        if(company.equals("Axfood")){
            axfood.startWorking();
        }
        if(company.equals("ICA")){
            ica.startWork();
        }
        if(company.equals("Coop")){
            coop.startWork();
        }
        if(company.equals("City Gross")){
            cg.startWork();
        }
    }

    /**
     * Called from actionlistener, stops threads based on wich company the user wants to stop.
     * @param company - the name of the company to stop working
     */
    public void stopThread(String company){
        if(company.equals("Scan")){
            scan.stopWorking();
        }
        if(company.equals("Arla")){
            arla.stopWorking();
        }
        if(company.equals("Axfood")){
            axfood.stopWorking();
        }
        if(company.equals("ICA")){
            ica.stopWork();
        }
        if(company.equals("Coop")){
            coop.stopWork();
        }
        if(company.equals("City Gross")){
            cg.stopWork();
        }
    }

    /**
     * Will check if the checkbox for continue loading is checked or not.
     * @param company - the name of the company
     * @return true- if box is selected
     */
    public boolean checkIfKeepLoading(String company){
       return gui.selectedBox(company);
    }


}

