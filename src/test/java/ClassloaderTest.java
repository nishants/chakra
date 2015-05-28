import chakra.compiler.*;
import chakra.compiler.Compiler;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ClassloaderTest {

  private static String message = "no-messages";

  public static String set(String args){
    message = args;
    return message;
  }

  public static String get(){
    return "Hello     - ClassloaderTest";
  }

  @Test
  public void classesFromChildClassLoaderMustBeAbleToPassSignalsToParentCllassloaderContext() throws Exception {
    String rutimeClassSrc = "public class SimpleMain{public static void print(){ClassloaderTest.set(\"Hello     - SimpleMain\"); System.out.println(ClassloaderTest.get());}" +
        "public static String get(){return ClassloaderTest.get();}"+
        "}";

    CompilationResult result = Compiler.create().compile(new InMemoryJavaFile("SimpleMain", rutimeClassSrc));
    Class runtimeClass = result.getCompiledClasses().get(0);

    invokeStaicMethod(runtimeClass, "print");
    assertThat(message, is("Hello     - SimpleMain"));
    assertThat(getOn(runtimeClass.newInstance()), is("Hello     - ClassloaderTest"));
  }

  private String getOn(Object instance) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    return instance.getClass().getMethod("get").invoke(instance).toString();
  }

  private void invokeStaicMethod(Class runtimeClass, String methodName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    Method method = runtimeClass.getMethod(methodName);
    method.setAccessible(true);
    method.invoke(null);
  }
}
