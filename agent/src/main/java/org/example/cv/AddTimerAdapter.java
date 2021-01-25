package org.example.cv;

import org.example.mv.AddTimerMethodAdapter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;

/**
 * @author: wutianbiao
 * @date: 2021/1/24
 */
public class AddTimerAdapter extends ClassVisitor {
    private String owner;
    private boolean isInterface;

    public AddTimerAdapter(ClassVisitor cv) {
        super(ASM5, cv);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        cv.visit(version,access,name,signature,superName,interfaces);
        owner = name;
        isInterface = (access & ACC_INTERFACE) != 0;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        if (!isInterface && mv != null && !name.equals("<init>")){
            mv = new AddTimerMethodAdapter(mv, owner);
        }
        return mv;
    }

    @Override
    public void visitEnd() {
        if (!isInterface){
            FieldVisitor fv = cv.visitField(ACC_PUBLIC + ACC_STATIC, "timer", "J", null, null);
            if(fv != null) {
                fv.visitEnd();
            }
        }
        cv.visitEnd();
    }
}
