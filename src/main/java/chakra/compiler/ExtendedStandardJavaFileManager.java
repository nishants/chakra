package chakra.compiler;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.util.List;

public class ExtendedStandardJavaFileManager extends ForwardingJavaFileManager<JavaFileManager> {

  private List<InMemoryClassFile> compiledCode;
  private DynamicClassLoader cl;

  protected ExtendedStandardJavaFileManager(
      JavaFileManager fileManager,
      List<InMemoryClassFile> compiledCode,
      DynamicClassLoader cl) {

    super(fileManager);
    this.compiledCode = compiledCode;
    this.cl = cl;
    this.cl.setComilationTarget(compiledCode);
  }

  @Override
  public JavaFileObject getJavaFileForOutput(
      Location location,
      String className,
      JavaFileObject.Kind kind,
      FileObject sibling) throws IOException {

    for (InMemoryClassFile compiledClass : compiledCode) {
      if (compiledClass.getName().equals(className)) return compiledClass;
    }

    throw new UnexpectedJavaFileRequestedByCompiler(className, compiledCode);
  }

  @Override
  public ClassLoader getClassLoader(Location location) {
    return cl;
  }
}
