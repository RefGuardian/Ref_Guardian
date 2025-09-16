package CFGDetector;

import java.util.HashSet;
import java.util.Set;

public class CFGComparator {

    private static class NodePair {
        CFGNode node1;
        CFGNode node2;
        
        public NodePair(CFGNode node1, CFGNode node2) {
            this.node1 = node1;
            this.node2 = node2;
        }
        
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof NodePair)) return false;
            NodePair other = (NodePair) o;
            return node1 == other.node1 && node2 == other.node2;
        }
        
        @Override
        public int hashCode() {
            return System.identityHashCode(node1) * 31 + System.identityHashCode(node2);
        }
    }

    public static boolean areCFGEqual(CFGNode n1, CFGNode n2) {
        Set<NodePair> visited = new HashSet<>();
        return compareNodes(n1, n2, visited);
    }
    
    private static boolean compareNodes(CFGNode n1, CFGNode n2, Set<NodePair> visited) {
        if (n1 == n2) return true;
        if (n1 == null || n2 == null) return false;
        
        NodePair pair = new NodePair(n1, n2);
        if (visited.contains(pair)) {
            return true;
        }
        visited.add(pair);

        if (n1.type == null) {
            if (n2.type != null) return false;
        } else if (!n1.type.equals(n2.type)) {
            return false;
        }

        if (n1.node == null) {
            if(n2.node != null) return false;
        } else if (n2.node == null) {
            return false;
        } else if (!n1.node.toString().equals(n2.node.toString())) {
            return false;
        }

        if (n1.totChild != n2.totChild) {
            return false;
        }
        for (int i = 0; i < n1.totChild; i++) {
            if (!compareNodes(n1.children[i], n2.children[i], visited)) {
                return false;
            }
        }
        return true;
    }
}
