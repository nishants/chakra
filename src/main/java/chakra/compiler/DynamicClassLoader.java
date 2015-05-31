package chakra.compiler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DynamicClassLoader extends ClassLoader {
    private Map<String, InMemoryClassFile> customCompiledCode = new HashMap<String, InMemoryClassFile>();

    public DynamicClassLoader(ClassLoader parent) {
        super(parent);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        InMemoryClassFile cc = customCompiledCode.get(name);
        if (cc == null) {
            return ClassLookup.getClass(name);
        }
        byte[] byteCode = cc.getByteCode();
        return defineClass(name, byteCode, 0, byteCode.length);
    }

    public void setComilationTarget(List<InMemoryClassFile> compiledCode) {
        for(InMemoryClassFile code : compiledCode){
            customCompiledCode.put(code.getName(), code);
        }
    }
}
