package org.example.utils.Graph;


import java.util.HashMap;
import java.util.HashSet;

public class NodeSet {
    private final HashMap<String,Integer> nodes;    // node can not be duplicated

    public NodeSet() {
        this.nodes = new HashMap<>();
    }

    public int addNode(String node,int nodeId) {
        if(this.nodes.containsKey(node))
            return nodes.get(node);
        else {
            this.nodes.put(node,nodeId);
            return 1;
        }
    }

    public boolean getNode(String content) {
        return this.nodes.containsKey(content);
    }

    public int findID(String string) {
        return this.nodes.get(string);
    }

}
