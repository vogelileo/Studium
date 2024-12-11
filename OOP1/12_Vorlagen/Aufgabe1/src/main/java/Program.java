import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class Program {

	public static int compareByAge(Person p1, Person p2){
		return Integer.compare(p1.getAge(), p2.getAge());
	}

	public static void main(String[] args) throws IOException {
		List<Person> people = PeopleData.read("people.csv");
		// AGE ASC
		people.sort(Program::compareByAge);
		people.sort((Person p1, Person p2)-> Integer.compare(p1.getAge(), p2.getAge()));


		// AGE DESC
		people.sort(Comparator.comparingInt(Person::getAge).reversed());

		//Lastname
		people.sort((Person p1, Person p2)-> p1.getLastName().compareTo(p2.getLastName()));

		//Length of Name
		people.sort(Comparator.comparingInt((Person p) -> p.getFirstName().length() + p.getLastName().length()));

		//City -> Lastname -> Firstname
		people.sort(Comparator.comparing(Person::getCity).thenComparing(Person::getLastName).thenComparing(Person::getFirstName));

		people.sort(Comparator.comparing(Person::getLastName).reversed().thenComparing(Person::getAge).reversed());
		PeopleData.write("sorted.csv", people);
	}
}
