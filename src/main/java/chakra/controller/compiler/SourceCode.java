package chakra.controller.compiler;

import lombok.Getter;

import javax.tools.SimpleJavaFileObject;
import java.io.IOException;
import java.net.URI;

public class SourceCode extends SimpleJavaFileObject {
    @Getter
    private final String classFullName;
    private final String classSourceCode;

    public SourceCode(String classFullName, String classSourceCode) throws Exception {
        super(URI.create("string:///" + classFullName.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
        this.classFullName = classFullName;
        this.classSourceCode = classSourceCode;
    }

    public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
        return classSourceCode;
    }
}
