package chakra.compiler;

// This class is loaded statically, and used for testing class-loader isolations
public class AClass {
  public static String get(){
    return "statically-loaded-static";
  }

  public String getI(){
    return "statically-loaded-instance";
  }
}
