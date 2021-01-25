package org.example.mv;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import static org.objectweb.asm.Opcodes.*;

/**
 * @author: wutianbiao
 * @date: 2021/1/24
 */
public class AddTimerMethodAdapter extends MethodVisitor {
    private String owner;
    public AddTimerMethodAdapter(MethodVisitor mv, String owner) {
        super(ASM5, mv);
        this.owner = owner;
    }

    @Override
    public void visitCode() {
        mv.visitCode();
        mv.visitFieldInsn(GETSTATIC, owner, "timer", Type.LONG_TYPE.getDescriptor());
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis","()J", false);
        mv.visitInsn(LSUB);
        mv.visitFieldInsn(PUTSTATIC, owner, "timer", "J");
    }

    @Override
    public void visitInsn(int opcode) {
        if ((opcode >= IRETURN && opcode <= RETURN) || opcode == ATHROW) {
            mv.visitFieldInsn(GETSTATIC, owner, "timer", "J");
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/System",
                    "currentTimeMillis", "()J", false);
            mv.visitInsn(LADD);
            mv.visitInsn(DUP);
            mv.visitFieldInsn(PUTSTATIC, owner, "timer", "J");
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "println", "(J)V", false);

        }
        mv.visitInsn(opcode);
    }

    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
        super.visitMaxs(maxStack+4, maxLocals);
    }
}
