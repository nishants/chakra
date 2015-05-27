package chakra.controller.compiler;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;
import java.util.ArrayList;
import java.util.List;

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
    return new CompilationResult(load(classes, classLoader), null);
  }

  private List<Class> load(SourceCode[] javaFiles, DynamicClassLoader classLoader) throws ClassNotFoundException {
    List<Class> classes = new ArrayList<Class>();
    for(SourceCode javaFile : javaFiles){
      classes.add(classLoader.loadClass(javaFile.getClassFullName()));
    }

    return classes;
  }

  private List<CompiledCode> compilationTargetFor(SourceCode[] aClass) throws Exception {
    List<CompiledCode> compiledCodeTarget = new ArrayList<CompiledCode>();
    for(SourceCode c : aClass){
      compiledCodeTarget.add(new CompiledCode(c.getClassFullName()));
    }
    return compiledCodeTarget;
  }

}
