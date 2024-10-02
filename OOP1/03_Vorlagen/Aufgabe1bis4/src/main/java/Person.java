class Person {
 String name;
 Person father, mother;
 Person[] colleagues;

    public static void main(String[] args){
        Person a = new Person();
        a.name = "Claudia";
        Person b = new Person();
        b.name = "Peter";
        a.father = b;
        b.name = "Pesche";
        b = new Person();
        b.name = "Susanne";
        a.mother = b;
        b.father = new Person();
        b.father.name = "Albert";
        b = null;
        System.out.println(a.father.name); //Pesche
        System.out.println(a.mother.name); //Susanne
        System.out.println(a.mother.father.name); //Albert
        System.out.println(a.father.mother.name); //never set -> null

    }
 }

