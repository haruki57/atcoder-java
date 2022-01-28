package lib;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class MethodsTest {
  
  @Test
  void lowerUpperBound() {
    Integer[] arr = { 1, 2, 2, 2, 3 };
    assertEquals(0, Methods.lowerBound(arr, 0));
    assertEquals(0, Methods.lowerBound(arr, 1));
    assertEquals(1, Methods.lowerBound(arr, 2));
    assertEquals(4, Methods.lowerBound(arr, 3));
    assertEquals(5, Methods.lowerBound(arr, 100));

    assertEquals(0, Methods.upperBound(arr, 0));
    assertEquals(1, Methods.upperBound(arr, 1));
    assertEquals(4, Methods.upperBound(arr, 2));
    assertEquals(5, Methods.upperBound(arr, 3));
    assertEquals(5, Methods.upperBound(arr, 100));
  }

  @Test
  void hoge() {
    assertTrue(true);
  }
}
