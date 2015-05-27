package chakra.controller.compiler;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

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
        .compile(new SourceCode("a.b.AClass", AClassBody)).getCompiledClasses().get(0);

    assertThat(aClass.getName(), is("a.b.AClass"));
  }

}