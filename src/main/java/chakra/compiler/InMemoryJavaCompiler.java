package chakra.compiler;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

class InMemoryJavaCompiler extends Compiler {
  private JavaCompiler javac = ToolProvider.getSystemJavaCompiler();
  public DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();

  public CompilationResult compile(InMemoryJavaFile... javaFiles) {
    DynamicClassLoader classLoader = new DynamicClassLoader(ClassLoader.getSystemClassLoader());

    ExtendedStandardJavaFileManager fileManager = null;

    try {
      fileManager = new ExtendedStandardJavaFileManager(
          javac.getStandardFileManager(diagnostics, null, null),
          compilationTargetFor(javaFiles),
          classLoader
      );
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }

    JavaCompiler.CompilationTask task = javac.getTask(null, fileManager, null, null, null, asList(javaFiles));

    boolean result = task.call();
    if (!result) {
      System.err.println(task.toString());
    }

    List<Class> load = null;

    try {
      load = load(javaFiles, classLoader);
    }
    catch (Throwable e) {
      e.printStackTrace();
      return new CompilationResult(new ArrayList<Class>(), e);
    }

    return new CompilationResult(load, null);
  }

  private List<Class> load(InMemoryJavaFile[] javaFiles, DynamicClassLoader classLoader) throws ClassNotFoundException {
    List<Class> classes = new ArrayList<Class>();
    for (InMemoryJavaFile javaFile : javaFiles) {
      classes.add(classLoader.loadClass(javaFile.getClassFullName()));
    }

    return classes;
  }

  private List<InMemoryClassFile> compilationTargetFor(InMemoryJavaFile[] aClass) throws URISyntaxException {
    List<InMemoryClassFile> compiledCodeTarget = new ArrayList<InMemoryClassFile>();
    for (InMemoryJavaFile c : aClass) {
      compiledCodeTarget.add(new InMemoryClassFile(c.getClassFullName()));
    }
    return compiledCodeTarget;
  }

}
