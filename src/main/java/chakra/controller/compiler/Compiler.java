package chakra.controller.compiler;

public interface Compiler {
  CompilationResult compile(String className, String sourceCodeInText) throws Exception;
}
