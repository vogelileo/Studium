import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Program {
	public static void main(String[] args) throws IOException {
		var people = PeopleData.read("people.csv");

		people.stream()
				.filter(p->p.getGender() == Gender.FEMALE)
				.map(Person::getFirstName)
				.filter(p->p.length() <= 3)
				.distinct()
				.forEach(System.out::println);
		OptionalDouble averageMaleAge = people.stream()
				.filter(p->p.getGender() == Gender.MALE)
				.mapToInt(Person::getAge)
				.average();
		System.out.println("Average Male Age: "+averageMaleAge);
		OptionalInt min = people.stream()
				.filter(p->p.getCity().equals( "Zürich"))
				.mapToInt(Person::getAge)
				.min();
		System.out.println("Min Age in Zürich: " + min);
		OptionalInt max = people.stream()
				.filter(p->p.getCity().equals( "Zürich"))
				.mapToInt(Person::getAge)
				.max();
		System.out.println("max Age in Zürich: " + max);
		people.stream()
				.map(Person::getSalary)
				.sorted(Comparator.reverseOrder())
				.limit(10)
				.forEach(System.out::println);
	    Map<String, Double> cities = people.stream()
				.collect(Collectors.groupingBy(Person::getCity, Collectors.averagingInt(Person::getAge)));
		System.out.print(cities);
	}
}
