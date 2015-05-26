package chakra.controller.compiler;

public abstract class Compiler {
  public abstract CompilationResult compile(String className, String sourceCodeInText) throws Exception;

  public static Compiler create(){
    return new InMemoryJavaCompiler();
  };
}
