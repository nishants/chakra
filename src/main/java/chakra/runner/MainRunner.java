package chakra.runner;

import chakra.compiler.SourceCode;

import java.util.List;

public class MainRunner {
  public static SourceCode[] preprocess(SourceCode... classes) {
    return classes;
  }

  public static ExecuteMainResult run(String className, List<Class> classes) {
    return new ExecuteMainResult(
        new String[0],
        new String[0],
        "okay"
    );
  }
}
