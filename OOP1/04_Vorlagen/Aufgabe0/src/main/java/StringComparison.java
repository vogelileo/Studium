public class StringComparison {
	public static void main(String[] args) {
		String oo = "OO";
		String prog = "Prog";
		String ooProg = "OOProg";

		System.out.println(oo == oo);
		System.out.println(oo == "OO");
		System.out.println(oo == new String(oo));
		System.out.println(oo == new String("OO"));
		System.out.println(oo + prog == ooProg);
		System.out.println(oo + prog == oo + prog);
		System.out.println(ooProg == "OO" + "Prog");

		System.out.println("---");

		System.out.println(oo.equals("OO"));
		System.out.println(oo.equals(new String("OO")));
		System.out.println((oo + prog).equals(ooProg));
		System.out.println((oo + prog).equals(oo + prog));
		System.out.println(ooProg.equals("OO" + "Prog"));
	}
}
