package chakra.compiler;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CompileSingleFileTest {

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
          "           return \"static-a-class\";   " +
          "         }                                  " +
          "         public String getI(){\n"+
          "           return \"dynamic-a-class\";"+
          "         }                                         "+

  "       }                                    ";

  private static final String aHelloClass =
      "       package a.b;  " +
          "       public class HelloClass  {                " +
          "         public static String get(){               " +
          "           return \"static-a-class\";   " +
          "         }                                  " +
          "         public String getI(){\n"+
          "           return \"instance-a-class\";"+
          "         }                                         "+

  "       }                                    ";

  private static final String anotherHelloClass =
      "       package a.b;  " +
          "       public class HelloClass  {                " +
          "         public static String get(){               " +
          "           return \"static-other-class\";   " +
          "         }                                  " +
          "         public String getI(){\n"+
          "           return \"instance-other-class\";"+
          "         }                                         "+

  "       }                                    "
      ;

  private static final String anInterface = "package a.b;  public interface AnInterface {void call();}";
  private static final String anAbstractClass = "package a.b;  public abstract class AnAbstractClass {public abstract void call();}";


  @Before
  public void setUp() throws Exception {
    compiler = Compiler.create();
  }

  @Test
  public void shouldNotUpdateAlreadyLoadedClass() throws Exception {
    Class dynamicClass = compiler
        .compile(new InMemoryJavaFile("chakra.compiler.AClass", aDynmaicClass))
        .getCompiledClasses().get(0);

    String gotDynamicallyStatic   = staticGetOn(dynamicClass);
    String gotDynamicallyInstance = instanceGetOn(dynamicClass);

    assertThat(gotDynamicallyStatic, is("statically-loaded-static"));
    assertThat(gotDynamicallyInstance, is("statically-loaded-instance"));
  }

  @Test
  public void testCompileAClass() throws Exception {
    Class helloClass = compiler
        .compile(new InMemoryJavaFile("HelloClass", HelloClass))
        .getCompiledClasses().get(0);

    Method staticMethod = readStaticMethodFrom(helloClass);
    Object instance = helloClass.newInstance();

    String returnedStatic = staticMethod.invoke(null, (Object) null).toString();
    String returnedInstance = readInstanceMethodFrom(helloClass, instance).invoke(instance, (Object) null, (Object) null).toString();

    assertThat(returnedInstance, is("instance-method"));
    assertThat(returnedStatic, is("static-method"));

  }

  @Test
  public void compilersMustHaveIsolatedClassLoaders() throws Exception {
    Class aClass = compiler
        .compile(new InMemoryJavaFile("a.b.HelloClass", aHelloClass))
        .getCompiledClasses().get(0);

    Class anotherClass = compiler
        .compile(new InMemoryJavaFile("a.b.HelloClass", anotherHelloClass))
        .getCompiledClasses().get(0);

    assertThat(staticGetOn(aClass), is("static-a-class"));
    assertThat(staticGetOn(anotherClass), is("static-other-class"));

    assertThat(instanceGetOn(aClass), is("instance-a-class"));
    assertThat(instanceGetOn(anotherClass), is("instance-other-class"));
  }

  @Test
  public void shouldCompileAnInterface() throws Exception {
    Class interfase = compiler
        .compile(new InMemoryJavaFile("a.b.AnInterface", anInterface))
        .getCompiledClasses().get(0);
    assertThat(interfase.getTypeName(), is("a.b.AnInterface"));

  }

  @Test
  public void shouldCompileAnAbstractClass() throws Exception {
    Class abstractClass = compiler
        .compile(new InMemoryJavaFile("a.b.AnAbstractClass", anAbstractClass))
        .getCompiledClasses().get(0);
    assertThat(abstractClass.getTypeName(), is("a.b.AnAbstractClass"));
  }

  private Method instanceMethodFrom(Class claz, Object instance, String methodName) throws NoSuchMethodException {
    Method method = claz
        .getMethod(methodName);
    method.setAccessible(true);
    return method;
  }

  private Method readInstanceMethodFrom(Class helloClass, Object o) throws NoSuchMethodException {
    Method method = helloClass
        .getMethod("instanceMethod", Integer.class, List.class);
    method.setAccessible(true);
    return method;
  }

  private String staticGetOn(Class claz) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    Method method = claz.getMethod("get");
    method.setAccessible(true);
    return method.invoke(null).toString();

  }
  private String instanceGetOn(Class claz) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
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