package top.deeke.javet;//package top.deeke.script;

import com.caoccao.javet.exceptions.JavetCompilationException;
import com.caoccao.javet.exceptions.JavetException;
import com.caoccao.javet.exceptions.JavetExecutionException;
import com.caoccao.javet.interop.V8Host;
import com.caoccao.javet.interop.V8Runtime;

import org.junit.Test;

import java.io.IOException;

public class JavetUnitTest {
    private void loadModules(V8Runtime v8Runtime) {
        v8Runtime.setV8ModuleResolver((runtime, resourceName, v8ModuleReferrer) -> {
            try {
                return runtime.getExecutor(getFileContent(resourceName)).setResourceName(resourceName).compileV8Module();
            } catch (JavetCompilationException | JavetExecutionException | IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public String getFileContent(String filename) throws IOException {
        return "export const Dy = {name: 'my name'}";//这里的逻辑写死
    }

    @Test
    public void recycleBug() {
        V8Runtime v8Runtime = null;
        try {
            v8Runtime = V8Host.getV8Instance().createV8Runtime();
            loadModules(v8Runtime);
            v8Runtime.getExecutor("import {Dy} from 'dy.js';Dy.name;").setModule(true).setResourceName("main.js").executeVoid();
        } catch (JavetException ignored) {

        } finally {
            assert v8Runtime != null;
            try {
                v8Runtime.removeV8Modules();//移除自定义的所有js模块
                System.gc();
                System.runFinalization();
                v8Runtime.lowMemoryNotification();
                v8Runtime.close();
            } catch (JavetException ignored) {

            }
        }
    }
}
