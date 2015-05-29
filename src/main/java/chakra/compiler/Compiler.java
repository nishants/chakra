package chakra.compiler;

public abstract class Compiler {
  public abstract CompilationResult compile(InMemoryJavaFile...classes) throws Exception;
  public static Compiler create(){
    return new InMemoryJavaCompiler();
  };
}
