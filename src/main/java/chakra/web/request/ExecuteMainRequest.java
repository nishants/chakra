package chakra.web.request;

import lombok.Data;

@Data
public class ExecuteMainRequest {
  private String mainClass;
  private JavaFile[] javaFiles;
}
