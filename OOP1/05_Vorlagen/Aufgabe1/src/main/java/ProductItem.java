public class ProductItem extends Item {
    private String description;
    public final int amount;
    public final int piecePrice;

    @Override
    public int getPrice(){
        return this.amount * this.piecePrice;
    }

    @Override
    public void print(){
        System.out.print("Product: "+this.description + "Count of pieces: "+this.amount+ "Piece price"+ this.piecePrice);
    }

    public ProductItem(int amount, int piecePrice, String description){
        this.amount = amount;
        this.piecePrice  = piecePrice;
        this.description = description;
    }
}
