package fr.ufrst.m1info.pvm.group5.ast;

import fr.ufrst.m1info.pvm.group5.ast.nodes.ASTNode;

public record InterpretationStoppedData(int line, boolean isProgramTerminated, ASTNode node) { }