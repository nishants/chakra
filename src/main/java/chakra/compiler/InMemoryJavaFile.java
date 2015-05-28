package chakra.compiler;

import lombok.Getter;

import javax.tools.SimpleJavaFileObject;
import java.io.IOException;
import java.net.URI;

public class InMemoryJavaFile extends SimpleJavaFileObject {
    @Getter
    private final String classFullName;
    private final String classSourceCode;

    public InMemoryJavaFile(String classFullName, String classSourceCode) throws Exception {
        super(URI.create(filePathFor(classFullName)), Kind.SOURCE);
        this.classFullName = classFullName;
        this.classSourceCode = classSourceCode;
    }

    // TODO To compiler the public classes, why ?
    private static String filePathFor(String classFullName) {
        return classFullName.replace('.', '/') + Kind.SOURCE.extension;
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
        return classSourceCode;
    }
}
