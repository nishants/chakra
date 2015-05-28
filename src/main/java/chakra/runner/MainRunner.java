package chakra.runner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.List;

public class MainRunner {

  // Synchronized for reporting the console output.
  public static synchronized ExecuteMainResult run(String className, List<Class> classes) {
    ReadablePrintStream sysOut = new ReadablePrintStream(new ByteArrayOutputStream());
    ReadablePrintStream sysErr = new ReadablePrintStream(new ByteArrayOutputStream());

    executeMain(
        sysOut,
        sysErr,
        findClass(className, classes)
    );

    return success(sysOut.read(), sysErr.read());
  }

  private static void executeMain(ReadablePrintStream sysOut, ReadablePrintStream sysErr, Class mainClass) {
    PrintStream defaultOut = System.out;
    PrintStream defaultErr = System.err;

    System.setOut(sysOut);
    System.setErr(sysErr);

    try {
      Method main = mainClass.getMethod("main", String[].class);
      main.setAccessible(true);
      main.invoke(null, (Object) null);

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      System.setOut(defaultOut);
      System.setErr(defaultErr);
    }

  }

  private static ExecuteMainResult success(String[] read, String[] error) {
    return new ExecuteMainResult(
        read,
        error,
        "okay"
    );
  }

  private static Class findClass(String className, List<Class> classes) {
    for (Class claz : classes) {
      if (claz.getName().equals(className)) return claz;
    }
    return null;
  }
}
