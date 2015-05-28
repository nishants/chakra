package chakra.compiler;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CompileLinkedFilesTest {

  private Compiler compiler;

  private static final String AClassBody =
          "    package a; public class AClass{                           " +
          "        public String get(){return \"i am an AClass\";}       " +
          "     }                                                        ";


  private static final String BClassBody =
      "    package a.b; import a.AClass; public class BClass extends AClass{         " +
      "        @Override public String get(){return new a.AClass().get();}        " +
      "    }                                                           ";

  @Before
  public void setUp() throws Exception {
    compiler = Compiler.create();
  }

  @Test
  public void shouldCompileLinkedClasses() throws Exception {

    List<Class> compiledClasses = compiler
        .compile(
            new InMemoryJavaFile("a.AClass", AClassBody),
            new InMemoryJavaFile("a.b.BClass", BClassBody)
        ).getCompiledClasses();

    Class aClass = compiledClasses.get(0);
    Class bClass = compiledClasses.get(1);

    assertThat(aClass.getName(), is("a.AClass"));
    assertThat(bClass.getName(), is("a.b.BClass"));

    assertThat(getFrom(anInstanceOf(bClass)), is("i am an AClass"));
  }

  private String getFrom(Object gettable) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    return gettable.getClass().getMethod("get").invoke(gettable).toString();
  }

  private Object anInstanceOf(Class claz) throws IllegalAccessException, InstantiationException {
    return claz.newInstance();
  }

}