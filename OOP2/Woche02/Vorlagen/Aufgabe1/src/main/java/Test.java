import java.util.*;

public class Test {
	public static void main(String[] args) {
		testMergeToList();
		testMergeToSet();
		testTargetMerge();
		testDuplicates();
		testMostFrequent();
		testVariance();
	}

	private static final Set<Integer> MOST_FREQUENT_INT = 
			Set.of(201, 398, 814); // 5 occurrences each
	private static final Set<String> MOST_FREQUENT_STRING = 
			Set.of("MBA"); // 4 occurrences
	
	private static void testMostFrequent() {
		var intResult = CollectionFunctions.mostFrequent(integerTestList());
		checkContains(MOST_FREQUENT_INT, intResult);
		var stringResult = CollectionFunctions.mostFrequent(stringTestList());
		checkContains(MOST_FREQUENT_STRING, stringResult);
	}

	private static final Set<Integer> DUPLICATE_INTEGERS = Set.of(515, 4, 519, 520, 521, 10, 13, 528, 530, 534, 23, 536,
			537, 538, 539, 541, 543, 33, 546, 34, 35, 36, 40, 553, 44, 47, 50, 51, 54, 567, 57, 570, 63, 579, 67, 580,
			72, 589, 591, 80, 593, 597, 86, 87, 601, 609, 99, 611, 616, 105, 107, 109, 112, 624, 627, 116, 118, 121,
			636, 125, 638, 639, 127, 128, 129, 642, 132, 646, 134, 647, 136, 652, 142, 146, 660, 661, 150, 663, 155,
			157, 158, 160, 163, 164, 166, 679, 168, 687, 180, 698, 186, 187, 192, 704, 705, 193, 198, 711, 713, 201,
			715, 719, 209, 721, 210, 211, 725, 215, 727, 219, 220, 736, 229, 742, 744, 746, 234, 751, 239, 752, 241,
			242, 244, 757, 245, 248, 760, 249, 253, 766, 768, 264, 265, 266, 267, 779, 268, 782, 783, 272, 275, 793,
			282, 284, 288, 291, 803, 804, 806, 298, 811, 812, 814, 302, 304, 305, 820, 821, 311, 824, 314, 826, 316,
			831, 321, 323, 324, 837, 325, 329, 842, 843, 333, 850, 851, 853, 856, 345, 346, 349, 353, 865, 354, 355,
			358, 872, 873, 362, 363, 875, 364, 365, 877, 366, 881, 371, 884, 372, 373, 889, 890, 379, 892, 380, 381,
			382, 383, 388, 389, 901, 904, 393, 905, 907, 908, 398, 401, 918, 407, 921, 925, 926, 415, 928, 416, 929,
			418, 938, 939, 428, 430, 943, 949, 437, 439, 953, 955, 446, 958, 962, 453, 454, 966, 458, 974, 462, 975,
			976, 979, 981, 470, 983, 473, 985, 475, 478, 480, 994, 995, 485, 998, 491, 493, 495, 496, 497, 500, 503,
			507, 508);
	private static final Set<String> DUPLICATE_STRINGS = Set.of("IHB", "MTJ", "FRS", "RNB", "VLA", "OLH", "HDF", "JVX",
			"MBA", "SLH", "BDS", "BBP", "ISC", "TLP", "JKD", "WHT", "MMB", "LKN", "WUM", "VQI", "HCN", "WQO", "OTE",
			"EGV", "APH", "QMX");

	private static void testDuplicates() {
		var intResult = CollectionFunctions.findDuplicates(integerTestList());
		checkEquals(DUPLICATE_INTEGERS, intResult);
		var stringResult = CollectionFunctions.findDuplicates(stringTestList());
		checkEquals(DUPLICATE_STRINGS, stringResult);
	}

	private static void testMergeToList() {
		testMergeToList(integerTestList());
		testMergeToList(stringTestList());
		testMergeToList(new ArrayList<Double>());
	}

	private static <T> void testMergeToList(List<T> list) {
		var left = list.subList(0, list.size() / 2);
		var right = list.subList(list.size() / 2, list.size());
		var actual = CollectionFunctions.mergeToList(left, right);
		checkEquals(list, actual);
	}

	private static void testMergeToSet() {
		testMergeToSet(integerTestList());
		testMergeToSet(stringTestList());
		testMergeToList(new ArrayList<Double>());
	}

	private static <T> void testMergeToSet(List<T> list) {
		var left = list.subList(0, list.size() / 2);
		var right = list.subList(list.size() / 2, list.size());
		var actual = CollectionFunctions.mergeToSet(left, right);
		checkEquals(new HashSet<T>(list), actual);
	}

	private static void testTargetMerge() {
		testTargetMerge(integerTestList());
		testTargetMerge(stringTestList());
		testTargetMerge(new ArrayList<Double>());
	}

	private static <T> void testTargetMerge(List<T> list) {
		var left = list.subList(0, list.size() / 3);
		var middle = list.subList(list.size() / 3, 2 * list.size() / 3);
		var right = list.subList(2 * list.size() / 3, list.size());
		var target = new ArrayList<>(left);
		CollectionFunctions.merge(middle, right, target);
		checkEquals(list, target);
	}

	private static <T> void checkEquals(T expected, T actual) {
		if (Objects.equals(expected, actual)) {
			System.out.println("Passed");
		} else {
			throw new AssertionError("Test failed!");
		}
	}
	
	private static <T> void checkContains(Set<T> expectedSet, T actual) {
		if (expectedSet.contains(actual)) {
			System.out.println("Passed");
		} else {
			throw new AssertionError("Test failed!");
		}
	}

	private static List<Integer> integerTestList() {
		var random = new Random(4711);
		var list = new ArrayList<Integer>();
		for (int i = 0; i < 1000; i++) {
			list.add(random.nextInt(1000));
		}
		return list;
	}

	private static List<String> stringTestList() {
		var random = new Random(4711);
		var list = new ArrayList<String>();
		for (int i = 0; i < 1000; i++) {
			String text = "";
			for (int k = 0; k < 3; k++) {
				text += (char) ('A' + random.nextInt('Z' - 'A'));
			}
			list.add(text);
		}
		return list;
	}
	
	private static void testVariance() {
		List<Number> result1 = CollectionFunctions.mergeToList(new HashSet<Double>(), new ArrayList<Integer>());
		checkEquals(new ArrayList<>(), result1);
		Set<Number> result2 = CollectionFunctions.mergeToSet(new HashSet<Double>(), new ArrayList<Integer>());
		checkEquals(new HashSet<>(), result2);
		CollectionFunctions.<Number>merge(new HashSet<Short>(), new ArrayList<Float>(), new HashSet<Object>());
	}
}
