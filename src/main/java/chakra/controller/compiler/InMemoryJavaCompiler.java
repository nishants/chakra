package chakra.controller.compiler;

import javax.tools.*;
import java.util.Arrays;

class InMemoryJavaCompiler extends Compiler {
  private JavaCompiler javac = ToolProvider.getSystemJavaCompiler();
  public DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();

  public CompilationResult compile(String className, String sourceCodeInText) throws Exception {

    DynamicClassLoader cl = new DynamicClassLoader(ClassLoader.getSystemClassLoader());

    ExtendedStandardJavaFileManager fileManager = new ExtendedStandardJavaFileManager(
        defaultFileManager(),
        new CompiledCode(className),
        cl
    );

    JavaCompiler.CompilationTask task = javac.getTask(null, fileManager, null, null, null, Arrays.asList(new SourceCode(className, sourceCodeInText)));

    boolean result = task.call();
    if (!result) {
      System.err.println(task.toString());
    }
    return new CompilationResult(cl.loadClass(className), null);
  }

  private StandardJavaFileManager defaultFileManager() {
    return javac.getStandardFileManager(diagnostics, null, null);
  }
}
