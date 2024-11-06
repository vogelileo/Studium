public class Main {

    public static void main(String[] args) {
        Sub s = new Sub();
        Base b = new Sub();
        Object o = new Sub();
        var v = o;

        b.copyTo(o);
        b.copyTo(b);
        b.copyTo(s);
        s.copyTo(o);
        s.copyTo(b);
        s.copyTo(s);
        v.copyTo(v);
    }

}
