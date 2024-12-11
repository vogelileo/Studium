import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

interface PersonFactory {
	Person createPerson(String firstName, String lastName, Gender gender, String city, int age, int salary);
}

public class Lambdas {
	public static void main(String[] args) {
		taskA();
		taskB();
		taskC();
	}

	private static void taskA() {
		var people = new ArrayList<Person>();
		people.add(new Person("Hans", "Meier", Gender.MALE, "Zürich", 34, 40000));
		people.add(new Person("Petra", "Müller", Gender.FEMALE, "Rapperswil", 25, 60000));
		people.add(new Person("Klaus", "Schmid", Gender.MALE, "Wetzikon", 30, 21000));
		people.add(new Person("Claudia", "Schneider", Gender.FEMALE, "Zürich", 40, 92000));
		sort(people);
	}

	private static void sort(List<Person> people) {
		int count = 0;
		Collections.sort(people, (p1, p2) -> {
			count++; // Compile error
			return p1.getLastName().compareTo(p2.getLastName());
		});
		System.out.println("Number of comparisons: " + count);
	}

	private static void taskB() {
		String[] names = { "UPPER", "up", "DOWN", "do", "reset", "REPEAT" };
		Arrays.sort(names, String::compareToIgnoreCase);
		for (String item : names) {
			System.out.print(item + " ");
		}
		System.out.println();
	}

	private static void taskC() {
		System.out.println(createPersonList(Person::new, 10));
	}

	private static List<Person> createPersonList(PersonFactory factory, int amount) {
		var list = new ArrayList<Person>();
		for (int i = 0; i < amount; i++) {
			list.add(factory.createPerson("FirstName" + i, "LastName" + i, Gender.MALE, "Zürich", 30, 0));
		}
		return list;
	}
}
