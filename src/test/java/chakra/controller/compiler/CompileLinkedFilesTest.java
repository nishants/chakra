package chakra.controller.compiler;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CompileLinkedFilesTest {

  private Compiler compiler;

  private static final String AClassBody =
      "    package a; public class AClass{}";


  private static final String BClassBody =
      "    package a.b; public class BClass{}";

  @Before
  public void setUp() throws Exception {
    compiler = Compiler.create();
  }

  @Test
  public void shouldCompileLinkedClasses() throws Exception {

    List<Class> aClass = compiler
        .compile(
            new SourceCode("a.AClass", AClassBody),
            new SourceCode("a.b.BClass", BClassBody)
        ).getCompiledClasses();

    assertThat(aClass.get(0).getName(), is("a.AClass"));
    assertThat(aClass.get(1).getName(), is("a.b.BClass"));
  }

}