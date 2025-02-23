public class Employee implements Comparable<Employee> {
    private String name;
    private double salary;

    public Employee (String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    @Override
    public int compareTo(Employee other) {
        return Double.compare(this.salary, other.salary);
    }

    @Override
    public String toString() {
        return name + ": " + salary;
    }
}


