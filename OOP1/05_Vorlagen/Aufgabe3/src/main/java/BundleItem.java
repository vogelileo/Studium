import java.util.ArrayList;
public class BundleItem extends Item {
    ArrayList<Item> items = new ArrayList<Item>();
    private final String description;
    public int discount;

    public String getDescription() {
        return description;
    }

    public void addItem(Item item){
        if(item instanceof BundleItem){
            return;
        }
        this.items.add(item);
    }

    public void removeItem(int index){
        this.items.remove(index);
    }

    @Override
    public double getPrice() {
        double total = 0;
        for(Item item: this.items){
            total += item.getPrice();
        };

        return total - total * this.discount /100;
    }
    @Override
    public void print(){
        System.out.print("Product: "+this.description +  "Bundle price"+ this.getPrice() + "Positionen:" +this.items.toString());
    }
    BundleItem(String description, int discount){
        this.description = description;
        this.discount = discount;
    }
}
