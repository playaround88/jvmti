package org.example;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

/**
 * Java Virtual Machine Tool Interface Demo
 *
 * @author wutb
 * @date 2021-01-17
 */
public class AgentMain {
    // JVM启动时agent
    public static void premain(String agentArgs, Instrumentation inst) {
        agent0(agentArgs, inst);
    }

    // JVM运行时agent
    public static void agentmain(String args, Instrumentation inst) {
        agent0(args, inst);
    }

    /**
     * 验证开发
     *
     * @param agentArgs
     * @param inst
     */
    private static void agent0(String agentArgs, Instrumentation inst) {
        System.out.println("agent is running");
        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
                // 判断那些类要做打桩
                if (className.endsWith("WorkerMain")) {
                    System.out.println("transform class WorkerMain");
                }
                // 直接返回，不做二进制打桩
                return classfileBuffer;
            }
        });
    }
}
