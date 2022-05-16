/**
 * @author Jakob Hagman
 * Program SYS21
 */
package Model;
public class FoodItem {
    private double weight;
    private double volume;
    private String item;
    public FoodItem (double weight, double volume, String item){
        this.weight = weight;
        this.volume = volume;
        this.item = item;
    }
    public String getItem() {
        return item;
    }
    public double getVolume() {
        return volume;
    }
    public double getWeight() {
        return weight;
    }
    @Override
    public String toString() {
        return String.format("ITEM: %s | WEIGHT: %.2f KG | VOLUME: %.2f", this.item, this.weight, this.volume);
    }
}
