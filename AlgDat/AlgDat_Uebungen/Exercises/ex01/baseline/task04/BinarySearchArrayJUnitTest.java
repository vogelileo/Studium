/*
 * OST - Uebungen 'Algorithmen & Datenstrukturen (AlgDat)'
 * Version: Sat Sep 13 15:53:00 CEST 2025
 */

package ex01.baseline.task04;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BinarySearchArrayJUnitTest {
  
  // Stress-Test:
  private static final int NUMBER_OF_TESTS = 10_000;
  private static final int MIN_SIZE =  1;
  private static final int MAX_SIZE = 32;
  private static final int LOWER_BOUND =  0; // inclusive
  private static final int UPPER_BOUND = 10; // inclusive
  
  BinarySearchArrayTest binarySearchArray = new BinarySearchArrayTest();

  @Before
  public void setUp() {
    binarySearchArray.clear();
  }

  @Test
  public void test_1() {
    fillBinarySearchArray(List.of(1, 2));
    assertTrue(binarySearchArray.verify(2, false));
  }

  @Test
  public void test_2() {
    fillBinarySearchArray(List.of(2, 1));
    assertTrue(binarySearchArray.verify(2, false));
  }

  @Test
  public void test_3() {
    fillBinarySearchArray(List.of(1, 1));
    assertTrue(binarySearchArray.verify(2, false));
  }

  @Test
  public void test_4() {
    fillBinarySearchArray(List.of(1, 2, 3));
    assertTrue(binarySearchArray.verify(3, false));
  }

  @Test
  public void test_5() {
    fillBinarySearchArray(List.of(3, 2, 1));
    assertTrue(binarySearchArray.verify(3, false));
  }

  @Test
  public void test_6() {
    fillBinarySearchArray(List.of(3, 1, 2));
    assertTrue(binarySearchArray.verify(3, false));
  }

  @Test
  public void test_7() {
    fillBinarySearchArray(List.of(1, 1, 1));
    assertTrue(binarySearchArray.verify(3, false));
  }
  
  @Test
  public void test_StressTest() {
    new Random().ints(NUMBER_OF_TESTS, MIN_SIZE, MAX_SIZE + 1).forEach(size -> {
      List<Integer> list = new Random()
          .ints(size, LOWER_BOUND, UPPER_BOUND + 1).boxed()
          .collect(Collectors.toList());
      System.out.println(list);
      binarySearchArray.clear();
      fillBinarySearchArray(list);
      System.out.println(binarySearchArray.arrayList);
      assertTrue(binarySearchArray.verify(list.size(), false));
    });
  }
  
  private void fillBinarySearchArray(List<Integer> list) {
    for (int i: list) {
      if (binarySearchArray.arrayList.size() == 0) {
        binarySearchArray.arrayList.add(i);
      } else {
        binarySearchArray.add(0, binarySearchArray.arrayList.size() - 1, i);
      }
    }
  }

}
