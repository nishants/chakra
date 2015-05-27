package chakra.controller.compiler;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;

@Ignore
public class CompileLinkedFilesTest {

  private Compiler compiler;

  private static final String AClassBody =
      "       package a.b;  " +
          "       public class AClass {                " +
          "         public static String get(){               " +
          "           return \"static-a-class\";   " +
          "         }                                  " +
          "         public String getI(){\n"+
          "           return \"dynamic-a-class\";"+
          "         }                                         "+

          "       }                                    ";

  private static final String BClassBody =
      "    package a.b; public class BClass{}";

  @Before
  public void setUp() throws Exception {
    compiler = Compiler.create();
  }

  @Test
  public void shouldCompileLinkedClasses() throws Exception {
    List<SourceCode> classes = asList();

    Class aClass = compiler
        .compile(new SourceCode("a.b.AClass", AClassBody),
            new SourceCode("a.b.BClass", BClassBody))
        .getCompiledClass();
  }

}