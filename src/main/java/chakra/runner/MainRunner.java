package chakra.runner;

import chakra.web.request.JavaFile;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class MainRunner {
  public static JavaFile[] preprocess(JavaFile... javaFiles) {
    for (JavaFile sourceCode : javaFiles) {
      replaceSystemConsole(sourceCode);
    }
    return javaFiles;
  }

  private static void replaceSystemConsole(JavaFile sourceCode) {
    int console = SystemConsoles.getAConsole();
    String newConsole = "chakra.runner.SystemConsoles.get(<console-id>)".replace("<console-id>", "" + console);
    String filteredCode = sourceCode.getJavaCode().replaceAll("System.out", newConsole);
    sourceCode.setJavaCode(filteredCode);
  }

  public static ExecuteMainResult run(String className, List<Class> classes) {
    try {
      executeMain(findClass(className, classes));
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }
    return success(SystemConsoles.get(0).read(), new String[0]);
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
