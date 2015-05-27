package chakra.controller.compiler;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;

import static java.util.Arrays.asList;

class InMemoryJavaCompiler extends Compiler {
  private JavaCompiler javac = ToolProvider.getSystemJavaCompiler();
  public DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();

  public CompilationResult compile(SourceCode... classes) throws Exception {
    DynamicClassLoader classLoader = new DynamicClassLoader(ClassLoader.getSystemClassLoader());

    ExtendedStandardJavaFileManager fileManager =

        new ExtendedStandardJavaFileManager(
            javac.getStandardFileManager(diagnostics, null, null),
            compilationTargetFor(classes),
            classLoader
        );

    JavaCompiler.CompilationTask task = javac.getTask(null, fileManager, null, null, null, asList(classes));

    boolean result = task.call();
    if (!result) {
      System.err.println(task.toString());
    }
    return new CompilationResult(classLoader.loadClass(classes[0].getClassFullName()), null);
  }

  private CompiledCode compilationTargetFor(SourceCode[] aClass) throws Exception {
    return new CompiledCode(aClass[0].getClassFullName());
  }

}
