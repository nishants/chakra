package chakra.controller.compiler;

import lombok.Getter;

import javax.tools.SimpleJavaFileObject;
import java.io.IOException;
import java.net.URI;

public class SourceCode extends SimpleJavaFileObject {
    @Getter
    private final String className;
    private String contents = null;

    public SourceCode(String className, String contents) throws Exception {
        super(URI.create("string:///" + className.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
        this.className = className;
        this.contents = contents;
    }

    public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
        return contents;
    }
}
