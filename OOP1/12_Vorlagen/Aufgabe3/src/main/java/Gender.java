public enum Gender {
	MALE("m"), FEMALE("f");

	private String code;

	Gender(String code) {
		this.code = code;
	}

	public static Gender fromCode(String code) {
		switch (code) {
		case "m":
			return MALE;
		case "f":
			return FEMALE;
		default:
			throw new IllegalArgumentException("code");
		}
	}

	public String toCode() {
		return code;
	}
}
