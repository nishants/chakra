package chakra.controller.compiler;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CompilationResult {
  private Class compiledClass;
  private Object error;
}
