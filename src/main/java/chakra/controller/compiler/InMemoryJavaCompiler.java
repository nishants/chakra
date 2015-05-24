package chakra.controller.compiler;

import javax.tools.*;
import java.util.Arrays;

public class InMemoryJavaCompiler {
  static JavaCompiler javac = ToolProvider.getSystemJavaCompiler();
  public static DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();

  public static Class<?> compile(String className, String sourceCodeInText) throws Exception {
    SourceCode sourceCode = new SourceCode(className, sourceCodeInText);
    Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(sourceCode);

    CompiledCode codeToCompile = new CompiledCode(className);

    DynamicClassLoader cl = new DynamicClassLoader(ClassLoader.getSystemClassLoader());

    ExtendedStandardJavaFileManager fileManager = new ExtendedStandardJavaFileManager(
        defaultFileManager(), 
        codeToCompile,
        cl
    );

    JavaCompiler.CompilationTask task = javac.getTask(null, fileManager, null, null, null, compilationUnits);
    boolean result = task.call();
    if(!result){
      System.err.println(task.toString());
    }
    return cl.loadClass(className);
  }

  private static StandardJavaFileManager defaultFileManager() {
    return javac.getStandardFileManager(diagnostics, null, null);
  }
}
