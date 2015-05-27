package chakra.runner;

import chakra.compiler.SourceCode;

import java.util.List;

public class MainRunner {
  public static SourceCode[] preprocess(SourceCode... classes) {
    for(SourceCode sourceCode : classes){
      replaceSystemConsole(sourceCode);
    }
    return classes;
  }

  private static void replaceSystemConsole(SourceCode sourceCode) {
    int console = SystemConsoles.getAConsole();
    String newConsole = "chakra.runner.SystemConsoles.get(<console-id>)".replace("<console-id>", ""+ console);
    sourceCode.getClassFullName().replaceAll("System.out", newConsole);
  }

  public static ExecuteMainResult run(String className, List<Class> classes) {
    return new ExecuteMainResult(
        SystemConsoles.get(0).read(),
        new String[0],
        "okay"
    );
  }
}
