package CFGDetector;
import org.eclipse.jdt.core.dom.*;

import java.util.ArrayList;
import java.util.List;

public class CFGGenerator {

    public static class CFGFragment {
        public CFGNode entry;
        public CFGNode exit;  // 正常的出口
        public List<CFGNode> breakNodes;
        public List<CFGNode> continueNodes;

        public CFGFragment(CFGNode entry, CFGNode exit) {
            this.entry = entry;
            this.exit = exit;
            this.breakNodes = new ArrayList<>();
            this.continueNodes = new ArrayList<>();
        }

        // 工具方法：将两个CFG片段按顺序合并
        public static CFGFragment concat(CFGFragment first, CFGFragment second) {
            if (first == null) return second;
            if (second == null) return first;
            if (first.exit != null) {
                first.exit.addChild(second.entry);
            }
            CFGFragment result = new CFGFragment(first.entry, second.exit);
            result.breakNodes.addAll(first.breakNodes);
            result.breakNodes.addAll(second.breakNodes);
            result.continueNodes.addAll(first.continueNodes);
            result.continueNodes.addAll(second.continueNodes);
            return result;
        }
    }

    public static CFGNode generateCFG(String javaCode) {
        ASTParser parser = ASTParser.newParser(AST.JLS8);
        parser.setSource(javaCode.toCharArray());
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        CompilationUnit cu = (CompilationUnit) parser.createAST(null);

        TypeDeclaration typeDec = (TypeDeclaration) cu.types().get(0);
        MethodDeclaration[] methods = typeDec.getMethods();
        if (methods.length == 0) return null;
        MethodDeclaration method = methods[0];
        Block body = method.getBody();
        if (body == null) return null;

        CFGFragment frag = buildFragment(body, null, null);
        return frag.entry;
    }

    public static CFGFragment buildFragment(ASTNode node) {
        return buildFragment(node, null, null);
    }

    public static CFGFragment buildFragment(ASTNode node, CFGNode breakTarget, CFGNode continueTarget) {
        if (node instanceof Block) {
            Block block = (Block) node;
            List<?> stmts = block.statements();
            CFGFragment frag = null;
            for (Object obj : stmts) {
                Statement stmt = (Statement) obj;
                CFGFragment stmtFrag = buildFragment(stmt, breakTarget, continueTarget);
                if (frag == null) {
                    frag = stmtFrag;
                } else {
                    frag = CFGFragment.concat(frag, stmtFrag);
                }
            }
            if (frag == null) {
                CFGNode dummy = new CFGNode("Others", node);
                frag = new CFGFragment(dummy, dummy);
            }
            return frag;
        } else if (node instanceof IfStatement) {
            IfStatement ifStmt = (IfStatement) node;
            CFGNode condNode = new CFGNode("Condition", ifStmt.getExpression());

            CFGFragment thenFrag = buildFragment(ifStmt.getThenStatement(), breakTarget, continueTarget);
            CFGFragment elseFrag;
            if (ifStmt.getElseStatement() != null) {
                elseFrag = buildFragment(ifStmt.getElseStatement(), breakTarget, continueTarget);
            } else {
                CFGNode dummyElse = new CFGNode("Others", null);
                elseFrag = new CFGFragment(dummyElse, dummyElse);
            }
            condNode.addChild(thenFrag.entry);
            condNode.addChild(elseFrag.entry);

            CFGNode merge = new CFGNode("Others", null);
            if (thenFrag.exit != null) thenFrag.exit.addChild(merge);
            if (elseFrag.exit != null) elseFrag.exit.addChild(merge);

            CFGFragment frag = new CFGFragment(condNode, merge);
            frag.breakNodes.addAll(thenFrag.breakNodes);
            frag.breakNodes.addAll(elseFrag.breakNodes);
            frag.continueNodes.addAll(thenFrag.continueNodes);
            frag.continueNodes.addAll(elseFrag.continueNodes);
            return frag;
        } else if (node instanceof ForStatement) {
            ForStatement forStmt = (ForStatement) node;
            CFGNode condNode = new CFGNode("Condition", forStmt);
            CFGNode exitNode = new CFGNode("Others", null);
            CFGFragment bodyFrag = buildFragment(forStmt.getBody(), exitNode, condNode);
            if (bodyFrag.exit != null) {
                bodyFrag.exit.addChild(condNode);
            }
            for (CFGNode cont : bodyFrag.continueNodes) {
                cont.addChild(condNode);
            }
            for (CFGNode brk : bodyFrag.breakNodes) {
                brk.addChild(exitNode);
            }
            condNode.addChild(bodyFrag.entry);
            condNode.addChild(exitNode);
            CFGFragment frag = new CFGFragment(condNode, exitNode);
            frag.breakNodes.addAll(bodyFrag.breakNodes);
            frag.continueNodes.addAll(bodyFrag.continueNodes);
            return frag;
        } else if (node instanceof WhileStatement) {
            WhileStatement whileStmt = (WhileStatement) node;
            CFGNode condNode = new CFGNode("Condition", whileStmt.getExpression());
            CFGNode exitNode = new CFGNode("Others", null);
            CFGFragment bodyFrag = buildFragment(whileStmt.getBody(), exitNode, condNode);
            if (bodyFrag.exit != null) {
                bodyFrag.exit.addChild(condNode);
            }
            for (CFGNode cont : bodyFrag.continueNodes) {
                cont.addChild(condNode);
            }
            for (CFGNode brk : bodyFrag.breakNodes) {
                brk.addChild(exitNode);
            }
            condNode.addChild(bodyFrag.entry);
            condNode.addChild(exitNode);
            CFGFragment frag = new CFGFragment(condNode, exitNode);
            frag.breakNodes.addAll(bodyFrag.breakNodes);
            frag.continueNodes.addAll(bodyFrag.continueNodes);
            return frag;
        } else if (node instanceof SwitchStatement) {
            SwitchStatement switchStmt = (SwitchStatement) node;
            CFGNode switchNode = new CFGNode("Condition", switchStmt.getExpression());
            CFGNode merge = new CFGNode("Others", null);

            List<?> stmts = switchStmt.statements();
            List<CFGFragment> caseFragments = new ArrayList<>();
            CFGFragment currentCaseFrag = null;
            for (Object obj : stmts) {
                if (obj instanceof SwitchCase) {
                    if (currentCaseFrag != null) {
                        if (currentCaseFrag.exit != null) {
                            currentCaseFrag.exit.addChild(merge);
                        }
                        caseFragments.add(currentCaseFrag);
                    }
                    SwitchCase switchCase = (SwitchCase) obj;
                    CFGNode caseNode = new CFGNode("Others", switchCase);
                    currentCaseFrag = new CFGFragment(caseNode, caseNode);
                } else if (obj instanceof Statement) {
                    Statement stmt = (Statement) obj;
                    CFGFragment stmtFrag = buildFragment(stmt, merge, null);
                    if (currentCaseFrag == null) {
                        CFGNode dummyCase = new CFGNode("Others", null);
                        currentCaseFrag = new CFGFragment(dummyCase, dummyCase);
                    }
                    currentCaseFrag = CFGFragment.concat(currentCaseFrag, stmtFrag);
                }
            }
            if (currentCaseFrag != null) {
                if (currentCaseFrag.exit != null) {
                    currentCaseFrag.exit.addChild(merge);
                }
                caseFragments.add(currentCaseFrag);
            }
            for (CFGFragment fragCase : caseFragments) {
                switchNode.addChild(fragCase.entry);
            }
            CFGFragment frag = new CFGFragment(switchNode, merge);
            for (CFGFragment fragCase : caseFragments) {
                frag.breakNodes.addAll(fragCase.breakNodes);
            }
            return frag;
        } else if (node instanceof BreakStatement) {
            BreakStatement breakStmt = (BreakStatement) node;
            CFGNode breakNode = new CFGNode("Break", breakStmt);
            if (breakTarget != null) {
                breakNode.addChild(breakTarget);
            }
            CFGFragment frag = new CFGFragment(breakNode, breakNode);
            frag.breakNodes.add(breakNode);
            return frag;
        } else if (node instanceof ContinueStatement) {
            ContinueStatement contStmt = (ContinueStatement) node;
            CFGNode contNode = new CFGNode("Continue", contStmt);
            if (continueTarget != null) {
                contNode.addChild(continueTarget);
            }
            CFGFragment frag = new CFGFragment(contNode, contNode);
            frag.continueNodes.add(contNode);
            return frag;
        } else if (node instanceof ExpressionStatement) {
            ExpressionStatement exprStmt = (ExpressionStatement) node;
            CFGNode exprNode = new CFGNode("Expression", exprStmt.getExpression());
            return new CFGFragment(exprNode, exprNode);
        } else if (node instanceof ReturnStatement) {
            ReturnStatement retStmt = (ReturnStatement) node;
            CFGNode retNode = new CFGNode("Others", retStmt);
            return new CFGFragment(retNode, retNode);
        } else if (node instanceof ConditionalExpression) {
            ConditionalExpression condExp = (ConditionalExpression) node;
            CFGNode condNode = new CFGNode("Condition", condExp.getExpression());
            CFGFragment thenFrag = buildFragment(condExp.getThenExpression(), breakTarget, continueTarget);
            CFGFragment elseFrag = buildFragment(condExp.getElseExpression(), breakTarget, continueTarget);
            condNode.addChild(thenFrag.entry);
            condNode.addChild(elseFrag.entry);
            CFGNode merge = new CFGNode("Others", null);
            if (thenFrag.exit != null) thenFrag.exit.addChild(merge);
            if (elseFrag.exit != null) elseFrag.exit.addChild(merge);
            CFGFragment frag = new CFGFragment(condNode, merge);
            frag.breakNodes.addAll(thenFrag.breakNodes);
            frag.breakNodes.addAll(elseFrag.breakNodes);
            frag.continueNodes.addAll(thenFrag.continueNodes);
            frag.continueNodes.addAll(elseFrag.continueNodes);
            return frag;
        } else {
            CFGNode simple = new CFGNode("Others", node);
            return new CFGFragment(simple, simple);
        }
    }

    public static void printCFG(CFGNode node, String indent) {
        if (node == null) return;
        System.out.println(indent + node.type + " : " + (node.node != null ? node.node.toString() : "null"));
        for (int i = 0; i < node.totChild; i++) {
            printCFG(node.children[i], indent + "  ");
        }
    }
}