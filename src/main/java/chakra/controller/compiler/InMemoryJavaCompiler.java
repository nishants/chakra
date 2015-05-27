package chakra.controller.compiler;

import javax.tools.*;
import java.util.Arrays;

class InMemoryJavaCompiler extends Compiler {
  private JavaCompiler javac = ToolProvider.getSystemJavaCompiler();
  public DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();

  public CompilationResult compile(SourceCode...classes) throws Exception {

    DynamicClassLoader cl = new DynamicClassLoader(ClassLoader.getSystemClassLoader());

    ExtendedStandardJavaFileManager fileManager = new ExtendedStandardJavaFileManager(
        defaultFileManager(),
        new CompiledCode(classes[0].getClassName()),
        cl
    );

    JavaCompiler.CompilationTask task = javac.getTask(null, fileManager, null, null, null, Arrays.asList(classes));

    boolean result = task.call();
    if (!result) {
      System.err.println(task.toString());
    }
    return new CompilationResult(cl.loadClass(classes[0].getClassName()), null);
  }

  private StandardJavaFileManager defaultFileManager() {
    return javac.getStandardFileManager(diagnostics, null, null);
  }
}
