package chakra.compiler;

public abstract class Compiler {
  public abstract CompilationResult compile(SourceCode...classes) throws Exception;

  public static Compiler create(){
    return new InMemoryJavaCompiler();
  };
}
