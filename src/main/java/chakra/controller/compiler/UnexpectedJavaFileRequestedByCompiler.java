package chakra.controller.compiler;

import java.util.List;

import static java.lang.String.format;

public class UnexpectedJavaFileRequestedByCompiler extends IllegalStateException {
  public UnexpectedJavaFileRequestedByCompiler(String className, List<CompiledCode> compiledCode) {
    super(format("Unexpected class source requested%n Requested by compiler : %s%n available : %s", className, compiledCode.toString()));
  }
}
