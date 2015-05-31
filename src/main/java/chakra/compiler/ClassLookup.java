package chakra.compiler;

public class ClassLookup {
  public static Class<?> getClass(String name) throws ClassNotFoundException {
    return Class.forName(name);
  }
}
