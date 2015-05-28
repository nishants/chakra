package chakra.compiler;

import javax.tools.SimpleJavaFileObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

public class InMemoryClassFile extends SimpleJavaFileObject {
    private ByteArrayOutputStream baos = new ByteArrayOutputStream();

    public InMemoryClassFile(String className) throws URISyntaxException {
        super(new URI(className), Kind.CLASS);
    }

    @Override
    public OutputStream openOutputStream() throws IOException {
        return baos;
    }

    public byte[] getByteCode() {
        return baos.toByteArray();
    }
}
