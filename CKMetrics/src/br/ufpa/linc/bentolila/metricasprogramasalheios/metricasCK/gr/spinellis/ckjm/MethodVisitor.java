/*
 * $Id: MethodVisitor.java 1.8 2005/10/09 15:36:08 dds Exp $
 *
 * (C) Copyright 2005 Diomidis Spinellis
 *
 * Permission to use, copy, and distribute this software and its
 * documentation for any purpose and without fee is hereby granted,
 * provided that the above copyright notice appear in all copies and that
 * both that copyright notice and this permission notice appear in
 * supporting documentation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND WITHOUT ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED WARRANTIES OF
 * MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */

package br.ufpa.linc.bentolila.metricasprogramasalheios.metricasCK.gr.spinellis.ckjm;

import org.apache.bcel.Constants;
import org.apache.bcel.generic.ArrayInstruction;
import org.apache.bcel.generic.CHECKCAST;
import org.apache.bcel.generic.CodeExceptionGen;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.ConstantPushInstruction;
import org.apache.bcel.generic.EmptyVisitor;
import org.apache.bcel.generic.FieldInstruction;
import org.apache.bcel.generic.INSTANCEOF;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.InstructionConstants;
import org.apache.bcel.generic.InstructionHandle;
import org.apache.bcel.generic.InvokeInstruction;
import org.apache.bcel.generic.LocalVariableInstruction;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.ReturnInstruction;
import org.apache.bcel.generic.Type;



/**
 * Visit a method calculating the class's Chidamber-Kemerer metrics.
 * A helper class for ClassVisitor.
 *
 * @see ClassVisitor
 * @version $Revision: 1.8 $
 * @author <a href="http://www.spinellis.gr">Diomidis Spinellis</a>
 */
@SuppressWarnings("deprecation")
class MethodVisitor extends EmptyVisitor {
    /** Method generation template. */
    private MethodGen mg;
    /* The class's constant pool. */
    private ConstantPoolGen cp;
    /** The visitor of the class the method visitor is in. */
    private ClassVisitor cv;
//    /** The metrics of the class the method visitor is in. */
//////////////////////////	private ClassMetrics cm;

    /** Constructor. */
    MethodVisitor(MethodGen m, ClassVisitor c) {
	mg  = m;
	cv = c;
	cp  = mg.getConstantPool();
//////////////////////////////////	cm = cv.getMetrics();
    }

    /** Start the method's visit. */
    public void start() {
	if (!mg.isAbstract() && !mg.isNative()) {
	    for (InstructionHandle ih = mg.getInstructionList().getStart();
		 ih != null; ih = ih.getNext()) {
		Instruction i = ih.getInstruction();

		if(!visitInstruction(i))
		    i.accept(this);
	    }
	    updateExceptionHandlers();
	}
    }

    /** Visit a single instruction. */
    private boolean visitInstruction(Instruction i) {
	short opcode = i.getOpcode();

	return ((InstructionConstants.INSTRUCTIONS[opcode] != null) &&
	   !(i instanceof ConstantPushInstruction) &&
	   !(i instanceof ReturnInstruction));
    }

    /** Local variable use. */
    public void visitLocalVariableInstruction(LocalVariableInstruction i) {
	if(i.getOpcode() != Constants.IINC)
	    cv.registerCoupling(i.getType(cp));
    }

    /** Array use. */
    public void visitArrayInstruction(ArrayInstruction i) {
	cv.registerCoupling(i.getType(cp));
    }

    /** Field access. */
	public void visitFieldInstruction(FieldInstruction i) {
	cv.registerFieldAccess(i.getClassName(cp), i.getFieldName(cp));
	cv.registerCoupling(i.getFieldType(cp));
    }

    /** Method invocation. */
    public void visitInvokeInstruction(InvokeInstruction i) {
	Type[] argTypes   = i.getArgumentTypes(cp);
	for (int j = 0; j < argTypes.length; j++)
	    cv.registerCoupling(argTypes[j]);
	cv.registerCoupling(i.getReturnType(cp));
	/* Measuring decision: measure overloaded methods separately */
	cv.registerMethodInvocation(i.getClassName(cp), i.getMethodName(cp), argTypes);
    }

    /** Visit an instanceof instruction. */
    public void visitINSTANCEOF(INSTANCEOF i) {
	cv.registerCoupling(i.getType(cp));
    }

    /** Visit checklast instruction. */
    public void visitCHECKCAST(CHECKCAST i) {
	cv.registerCoupling(i.getType(cp));
    }

    /** Visit return instruction. */
    public void visitReturnInstruction(ReturnInstruction i) {
	cv.registerCoupling(i.getType(cp));
    }

    /** Visit the method's exception handlers. */
    private void updateExceptionHandlers() {
	CodeExceptionGen[] handlers = mg.getExceptionHandlers();

	/* Measuring decision: couple exceptions */
	for(int i=0; i < handlers.length; i++) {
	    Type t = handlers[i].getCatchType();
	    if (t != null)
		cv.registerCoupling(t);
	}
    }
}
