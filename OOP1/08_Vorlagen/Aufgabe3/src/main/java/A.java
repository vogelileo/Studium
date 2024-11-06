public interface A {
     default void f() { System.out.println("A::f()"); }
     default void g() { System.out.println("A::g()"); }
}
