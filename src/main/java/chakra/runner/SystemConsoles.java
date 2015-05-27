package chakra.runner;

import chakra.compiler.ReadablePrintStream;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;

public class SystemConsoles {
  private static List<ReadablePrintStream> streams = Arrays.asList(new ReadablePrintStream(new ByteArrayOutputStream()));

  public static int getAConsole() {
    return 0;
  }

  public static ReadablePrintStream get(int i){
    return streams.get(i);
  }
}
