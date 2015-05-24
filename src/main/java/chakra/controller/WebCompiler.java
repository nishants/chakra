package chakra.controller;

import chakra.controller.compiler.InMemoryJavaCompiler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


@RestController
public class WebCompiler {
  @RequestMapping("/compile/hello-world")
  public String getProblem(@RequestBody String src) throws Exception {
    Class<?> compiledCode = InMemoryJavaCompiler.compile("HelloWorld", src);
    return invokeStatic(mainMethodFrom(compiledCode)) ;
  }

  private String invokeStatic(Method method) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    return "Hello World! - "+ method.getName();

//    return method.invoke(new String[0]).toString();
  }

  private Method mainMethodFrom(Class<?> helloWorld) throws NoSuchMethodException {
    return helloWorld.getDeclaredMethod("main", String[].class);
  }
}
