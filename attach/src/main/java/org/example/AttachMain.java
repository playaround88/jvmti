package org.example;

import com.sun.tools.attach.*;

import java.io.IOException;
import java.util.List;

/**
 * Hello world!
 *
 */
public class AttachMain
{
    private static String agentJar = "D:\\workspace\\java\\jvmti\\agent\\target\\agent-1.0-SNAPSHOT-jar-with-dependencies.jar";

    public static void main( String[] args ) throws IOException, AgentLoadException, AgentInitializationException, AttachNotSupportedException {
        List<VirtualMachineDescriptor> vms = VirtualMachine.list();
        for(VirtualMachineDescriptor vmd : vms){
            // 找到测试的JVM
            if (vmd.displayName().endsWith("WorkerMain")) {
                // attach到目标ID的JVM上
                VirtualMachine virtualMachine = VirtualMachine.attach(vmd.id());
                // agent指定jar包到已经attach的JVM上
                virtualMachine.loadAgent(agentJar);
                virtualMachine.detach();
            }
        }
    }
}
