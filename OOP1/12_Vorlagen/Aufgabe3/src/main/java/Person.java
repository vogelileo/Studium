public class Person {
	private final String firstName;
	private final String lastName;
	private final Gender gender;
	private final String city;
	private final int age;
	private final int salary;
	
	public Person(String firstName, String lastName, Gender gender,
			String city, int age, int salary) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.city = city;
		this.age = age;
		this.salary = salary;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Gender getGender() {
		return gender;
	}

	public String getCity() {
		return city;
	}

	public int getAge() {
		return age;
	}

	public int getSalary() {
		return salary;
	}
}
