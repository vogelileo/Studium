public class ServiceItem extends Item{
    private String description;
    public double flatPrice;

    public String getDescription(){
        return this.description;
    }

    @Override
    public double getPrice(){
        return this.flatPrice;
    }

    @Override
    public void print(){
        System.out.print("Service: "+this.description +  "Piece price"+ this.getPrice());
    }

    public ServiceItem(String description, int flatPrice){
        this.description = description;
        this.flatPrice = flatPrice;
    }
}
