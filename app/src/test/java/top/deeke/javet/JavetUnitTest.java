package top.deeke.javet;//package top.deeke.script;

import android.util.Log;

import com.caoccao.javet.exceptions.JavetCompilationException;
import com.caoccao.javet.exceptions.JavetException;
import com.caoccao.javet.exceptions.JavetExecutionException;
import com.caoccao.javet.interop.V8Host;
import com.caoccao.javet.interop.V8Runtime;
import com.caoccao.javet.interop.callback.IV8ModuleResolver;
import com.caoccao.javet.interop.converters.JavetProxyConverter;
import com.caoccao.javet.interop.proxy.JavetReflectionObjectFactory;
import com.caoccao.javet.javenode.JNEventLoop;
import com.caoccao.javet.javenode.enums.JNModuleType;
import com.caoccao.javet.javenode.modules.console.ConsoleModule;
import com.caoccao.javet.javenode.modules.javet.JavetModule;
import com.caoccao.javet.javenode.modules.timers.TimersModule;
import com.caoccao.javet.javenode.modules.timers.TimersPromisesModule;
import com.caoccao.javet.values.reference.IV8Module;
import com.caoccao.javet.values.reference.V8Module;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JavetUnitTest {
    List<IV8Module> modules = new ArrayList<>();

    private void loadModules(V8Runtime v8Runtime) {
        IV8ModuleResolver iv8ModuleResolver = v8Runtime.getV8ModuleResolver();
        v8Runtime.setV8ModuleResolver((runtime, resourceName, v8ModuleReferrer) -> {
            System.out.println(":::" + resourceName);
            try {
                if (Objects.equals(resourceName, ConsoleModule.NAME) || Objects.equals(resourceName, JavetModule.NAME) || Objects.equals(resourceName, TimersModule.NAME) || Objects.equals(resourceName, TimersPromisesModule.NAME)) {
                    System.out.println("加载之前的逻辑");

                    IV8Module v8Module = iv8ModuleResolver.resolve(runtime, resourceName, v8ModuleReferrer);
                    modules.add(v8Module);
                    return v8Module;
                }

                V8Module v8Module = runtime.getExecutor(getFileContent(resourceName)).setResourceName(resourceName).compileV8Module();
                modules.add(v8Module);
                return v8Module;
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
        JNEventLoop eventLoop = null;
        try {
            v8Runtime = V8Host.getV8Instance().createV8Runtime();

            JavetProxyConverter javetProxyConverter = new JavetProxyConverter();
            javetProxyConverter.getConfig().setProxyArrayEnabled(true).setProxyListEnabled(true).setProxyMapEnabled(true).setProxySetEnabled(true);
            javetProxyConverter.getConfig().setReflectionObjectFactory(JavetReflectionObjectFactory.getInstance());
            v8Runtime.setConverter(javetProxyConverter);

            eventLoop = new JNEventLoop(v8Runtime);
            eventLoop.loadStaticModules(JNModuleType.Console, JNModuleType.Timers);

            loadModules(v8Runtime);

            v8Runtime.getExecutor("import {Dy} from 'dy.js';console.log(324);").setModule(true).setResourceName("main.js").executeVoid();
        } catch (JavetException e) {
            System.out.println("错误了：" + e.getMessage());
        } finally {
            assert v8Runtime != null;
            assert eventLoop != null;
            try {
                for (IV8Module v8Module : this.modules) {
                    v8Module.close();
                    System.out.println("模块--");
                }

                eventLoop.unloadStaticModules(JNModuleType.Timers, JNModuleType.Console);
                System.gc();
                System.runFinalization();
                v8Runtime.lowMemoryNotification();
                v8Runtime.close();
            } catch (JavetException ignored) {

            }
        }
    }
}
