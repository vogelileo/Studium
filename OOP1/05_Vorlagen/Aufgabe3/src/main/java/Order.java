import java.util.ArrayList;

public class Order {
    private ArrayList<Item> items = new ArrayList<Item>();
    public ArrayList<Item> getItems() {
        return items;
    }

    public void addItem(Item item){
        this.items.add(item);
    }
    public double getTotalPrice(){
        double total = 0;
        for (Item item : this.items) {
            total += item.getPrice();
        }
        return total;
    }
}
