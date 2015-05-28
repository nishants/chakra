package chakra.runner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class MainRunner {
  // Synchronized for reporting the console output.
  public static synchronized ExecuteMainResult run(String className, List<Class> classes) {
    ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
    ReadablePrintStream streamOut = new ReadablePrintStream(byteOut);
    PrintStream defaultOut = System.out;
    System.setOut(streamOut);

    try {
      executeMain(findClass(className, classes));
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }finally {
      System.setOut(defaultOut);
    }

    String[] consoleOutput = streamOut.read();

    return success(consoleOutput, new String[0]);
  }

  private static void executeMain(Class mainClass) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    Method main = mainClass.getMethod("main", String[].class);
    main.setAccessible(true);
    main.invoke(null,(Object) null);
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
