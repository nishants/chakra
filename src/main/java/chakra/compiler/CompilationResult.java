package chakra.compiler;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CompilationResult {
  private List<Class> compiledClasses;
  private Object error;
}
