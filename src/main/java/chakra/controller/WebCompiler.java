package chakra.controller;

import chakra.controller.compiler.Compiler;
import chakra.controller.compiler.SourceCode;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.springframework.web.bind.annotation.RequestMethod.POST;


@RestController
public class WebCompiler {
  @RequestMapping(value = "/compile/hello-world", method = POST)
  public String getProblem(@RequestBody String classCode) throws Exception {
    Class<?> compiledCode = Compiler.create().compile(new SourceCode("HelloWorld", classCode)).getCompiledClass();

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
