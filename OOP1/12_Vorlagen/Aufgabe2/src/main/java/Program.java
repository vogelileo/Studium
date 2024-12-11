import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class Program {


	public static void main(String[] args) throws IOException {
		List<Person> people = PeopleData.read("people.csv");

		Functions func = new Functions();

		func.search(people, p-> p.getGender() == Gender.MALE && p.getAge() > 30 && p.getAge() < 65);

		PeopleData.write("sortedFromFilter.csv", people);
	}
}
