package org.example.utils.Graph;

import guru.nidi.graphviz.attribute.Color;
import lombok.Getter;
import lombok.Setter;
import guru.nidi.graphviz.model.Node;
import lombok.ToString;

import java.util.Objects;

@Getter
@Setter
@ToString
public class dynamicNode{
    public enum EdgeColor {
        RED, BLACK, BLUE, GREEN
    }

    private int NodeId;
    private Color color;    // default black
    private String content;
    private String PC;
    private String Label;
    private String contents;

    public dynamicNode(int NodeId, Color color, String content,String PC) {
        this.color = Objects.requireNonNullElseGet(color, () -> Color.rgb(EdgeColor.BLACK.toString()));
        this.NodeId = NodeId;
        this.content = content;
        this.PC = PC;
    }
}
