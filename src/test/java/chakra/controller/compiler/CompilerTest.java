package chakra.controller.compiler;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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


  private static final String aDynmaicClass =
      "       package chakra.compiler;  " +
          "       public class AClass {                " +
          "         public static String get(){               " +
          "           return \"dynamically-loaded-static\";   " +
          "         }                                  " +
          "         public String getI(){\n"+
          "           return \"dynamically-loaded-instance\";"+
          "         }                                         "+

  "       }                                    ";


  @Before
  public void setUp() throws Exception {
    compiler = new InMemoryJavaCompiler();
  }

  @Test
  public void shouldNotUpdateAlreadyLoadedClass() throws Exception {

    Class dynamicClass = compiler
        .compile("chakra.compiler.AClass", aDynmaicClass)
        .getCompiledClass();

    String gotDynamicallyStatic   = invokeStaticGetterOn(dynamicClass);
    String gotDynamicallyInstance = invokeInstanceGetterOn(dynamicClass);

    assertThat(gotDynamicallyStatic, is("statically-loaded-static"));
    assertThat(gotDynamicallyInstance, is("statically-loaded-instance"));
  }

  @Test
  public void testCompileAClass() throws Exception {
    Class helloClass = compiler
        .compile("HelloClass", HelloClass)
        .getCompiledClass();

    Method staticMethod = readStaticMethodFrom(helloClass);
    Object instance = helloClass.newInstance();

    String returnedStatic = staticMethod.invoke(null, (Object) null).toString();
    String returnedInstance = readInstanceMethodFrom(helloClass, instance).invoke(instance, (Object) null, (Object) null).toString();

    assertThat(returnedInstance, is("instance-method"));
    assertThat(returnedStatic, is("static-method"));

  }

  private Method instanceMethodFrom(Class claz, Object instance, String methodName) throws NoSuchMethodException {
    Method method = claz
        .getMethod(methodName);
    method.setAccessible(true);
    return method;
  }

  private Method readInstanceMethodFrom(Class helloClass, Object o) throws NoSuchMethodException {
    return instanceMethodFrom(helloClass, o, "instanceMethod");
  }

  private String invokeStaticGetterOn(Class claz) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    Method method = claz.getMethod("get");
    method.setAccessible(true);
    return method.invoke(null).toString();

  }
  private String invokeInstanceGetterOn(Class claz) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
    Object instance = claz.newInstance();
    return instanceMethodFrom(claz, instance, "getI").invoke(instance).toString();

  }

  private Method readStaticMethodFrom(Class helloClass) throws NoSuchMethodException {
    Method method = helloClass
        .getMethod("staticMethod", String[].class);
    method.setAccessible(true);
    return method;
  }
}