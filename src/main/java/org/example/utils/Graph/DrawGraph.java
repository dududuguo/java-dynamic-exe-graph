package org.example.utils.Graph;

import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Factory;
import guru.nidi.graphviz.model.Graph;

import java.io.File;
import java.io.IOException;

public class DrawGraph {
    Graph g;
    String OutputPath;

    public DrawGraph(String path) {
        g = Factory.graph("dynamic graph").directed();

        OutputPath = path + "/graph.dot";
        System.out.println(OutputPath);
    }

    public void draw(dynamicNode prev, dynamicNode cur) {
        String label= cur.getPC();
        if(prev.getLabel()!=null){
            label=cur.getLabel();
        }
        g = g.with(Factory.node(String.valueOf(prev.getNodeId()))
                .with(Label.of("NodeID: " + prev.getNodeId() + "\n" + prev.getContents()))
                .link(
                        Factory.to(Factory.node(String.valueOf(cur.getNodeId())))
                                .with(prev.getColor(), Label.of(label))
                )
        );
    }

    public void drawLast(dynamicNode prev) {
        g = g.with(Factory.node(String.valueOf(prev.getNodeId()))
                .with(Label.of("NodeID: " + prev.getNodeId() + "\n" + prev.getContent()+"\n"+prev.getLabel()))
        );
    }

    public void outputFile() {
//        System.out.println(g);
        try {
            File file = new File(OutputPath);
            Graphviz.fromGraph(g).width(300).render(Format.DOT).toFile(file);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
