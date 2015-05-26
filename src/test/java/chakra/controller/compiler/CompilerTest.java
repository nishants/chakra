package chakra.controller.compiler;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CompilerTest {

  private Compiler compiler;
  private static final String HelloClass =
      "       public class HelloClass{                                                " +
          "           public String instanceMethod(Integer i, java.util.List aList){      " +
          "               return \"instance-method\";                                     " +
          "           }                                                                   " +
          "           public static String staticMethod(String[] h){                      " +
          "               return \"static-method\";                                       " +
          "           }                                                                   " +
          "        }                                                                      ";


  @Before
  public void setUp() throws Exception {
    compiler = new InMemoryJavaCompiler();
  }

  @Test
  public void testCompileAClass() throws Exception {
    Class helloClass = compiler
        .compile("HelloClass", HelloClass)
        .getCompiledClass();

    Method staticMethod = readStaticMethodFrom(helloClass);
    Object instance = helloClass.newInstance();

    String returnedStatic     = staticMethod.invoke(null, (Object) null).toString();
    String returnedInstance   = readInstanceMethodFrom(helloClass, instance).invoke(instance, (Object) null, (Object) null).toString();

    assertThat(returnedInstance, is("instance-method"));
    assertThat(returnedStatic, is("static-method"));

  }

  private Method readInstanceMethodFrom(Class helloClass, Object o) throws NoSuchMethodException {
    Method method = helloClass
        .getMethod("instanceMethod", Integer.class, List.class);
    method.setAccessible(true);
    return method;
  }

  private Method readStaticMethodFrom(Class helloClass) throws NoSuchMethodException {
    Method method = helloClass
        .getMethod("staticMethod", String[].class);
    method.setAccessible(true);
    return method;
  }
}