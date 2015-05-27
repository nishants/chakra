package chakra.controller.compiler;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.util.List;

public class ExtendedStandardJavaFileManager extends ForwardingJavaFileManager<JavaFileManager> {

    private List<CompiledCode> compiledCode;
    private DynamicClassLoader cl;

    /**
     * Creates a new instance of ForwardingJavaFileManager.
     *  @param fileManager delegate to this file manager
     * @param compiledCode
     * @param cl
     */
    protected ExtendedStandardJavaFileManager(JavaFileManager fileManager, List<CompiledCode> compiledCode, DynamicClassLoader cl) {
        super(fileManager);
        this.compiledCode = compiledCode;
        this.cl = cl;
        this.cl.setComilationTarget(compiledCode);
    }

    @Override
    public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling) throws IOException {
        return compiledCode.get(0);
    }

    @Override
    public ClassLoader getClassLoader(Location location) {
        return cl;
    }
}
