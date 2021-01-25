package org.example;

import org.example.cv.AddTimerAdapter;
import org.example.cv.ClassPrinter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

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
        agent2(agentArgs, inst);
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

    private static void agent1(String agentArgs, Instrumentation inst) {
        System.out.print("agent1 is running!");
        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
                if (className.endsWith("WorkerMain")) {

                    ClassPrinter classPrinter = new ClassPrinter();

                    ClassReader classReader = new ClassReader(classfileBuffer);

                    classReader.accept(classPrinter, 0);
                }
                // 直接返回，不做二进制打桩
                return classfileBuffer;
            }
        });
    }

    private static void agent2(String agentArgs, Instrumentation inst) {
        System.out.print("agent2 is running!");
        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
                if (className.endsWith("WorkerMain")) {
                    ClassReader classReader = new ClassReader(classfileBuffer);
                    ClassWriter cw = new ClassWriter(classReader, 0);

                    AddTimerAdapter addTimerAdapter = new AddTimerAdapter(cw);

                    classReader.accept(addTimerAdapter, 0);
                    return cw.toByteArray();
                }
                return classfileBuffer;
            }
        });
    }
}