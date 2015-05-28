package chakra.runner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ReadablePrintStream extends PrintStream {
  private ByteArrayOutputStream out;

  public ReadablePrintStream(ByteArrayOutputStream out) {
    super(out);
    this.out = out;
  }

  public String[] read(){
    return new String(out.toByteArray()).split("\\n");
  }
}
