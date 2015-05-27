package chakra.web.request;

import lombok.Data;

@Data
public class RunnableMain {
  private String mainClass;
  private String[] javaFiles;
}
