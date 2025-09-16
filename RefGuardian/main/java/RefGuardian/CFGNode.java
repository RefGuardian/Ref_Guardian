package CFGDetector;

import org.eclipse.jdt.core.dom.ASTNode;

public class CFGNode {
    String type; // Assignment, Condition, MethodInvocation, Expression, Others
    ASTNode node;
    CFGNode[] children;
    int totChild;
    public CFGNode(String type, ASTNode node) {
        this.type = type;
        this.node = node;
        this.children = new CFGNode[2];
        totChild = 0;
    }
    public CFGNode(String type, ASTNode node, CFGNode[] children) {
        this.type = type;
        this.node = node;
        this.children = children;
        totChild = children.length;
    }

    public void addChild(CFGNode child) {
        if (totChild >= children.length) {
            CFGNode[] newChildren = new CFGNode[children.length + 2];
            System.arraycopy(children, 0, newChildren, 0, children.length);
            children = newChildren;
        }
        children[totChild++] = child;
    }
}




