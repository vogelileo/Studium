public class OrderSystemTest {
    public static void main(String[] args) {

        var order = new Order();
        var pitem1 = new ProductItem(5,25,"Dishwashers");
        var pitem2 = new ProductItem(1,250, "oven");
        var sitem1 = new ServiceItem("Installment", 340);
        var sitem2 = new ServiceItem("Check",80);

        order.addItem(pitem1);
        order.addItem(pitem2);
        order.addItem(sitem1);
        order.addItem(sitem2);

        System.out.println(order.getItems().toString());
        System.out.println(order.getTotalPrice());

    }
}
