package chakra.compiler;

public class ClassLookup {
  public static Class<?> getClass(String name) throws ClassNotFoundException {
    System.out.println("lookup " + name + ", result : ");
    return org.junit.Test.class;
  }
}

