public class OrderSystemTest {
    public static void main(String[] args) {

        var order = new Order();
        var pitem1 = new ProductItem(5,25,"Dishwashers");
        var pitem2 = new ProductItem(1,250, "oven");
        var sitem1 = new ServiceItem("Installment", 340);
        var sitem2 = new ServiceItem("Check",80);

        var bundle1 = new BundleItem("New Year bundle", 10);
        bundle1.addItem(pitem1);
        bundle1.addItem(sitem1);
        bundle1.addItem(sitem2);
        bundle1.removeItem(2);


        order.addItem(pitem1);
        order.addItem(pitem2);
        order.addItem(sitem1);
        order.addItem(sitem2);
        order.addItem(bundle1);

        for(Item item: order.getItems()){
            item.print();
        }
        System.out.println(order.getTotalPrice());

    }
}
