package com.backend.graphic;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;

import java.io.File;
import java.io.IOException;

public class Graphic {

    String lexeme;
    String dot;
    char start;
    char end;

    public Graphic(String lexeme) {
        // Escape double quotes if they are present within the lexeme
        this.lexeme = lexeme.replace("\"", "\\\"");

        start = lexeme.charAt(0);
        end = lexeme.charAt(lexeme.length() - 1);
    }

    public void createPng() throws IOException {

        try {
            generateDot();
            MutableGraph graph = new Parser().read(dot);
            Graphviz.fromGraph(graph).render(Format.PNG).toFile(new File("graph.png"));

        } catch (IOException e ) {

        }


    }

    public void generateDot() {

        dot = "digraph G {rankdir=LR;" + end
                + " [shape=doublecircle];node [shape=circle];start [shape=point, width=0];start -> " + start;
        // +" -> h -> i -> l -> e}"

        for (int i = 1; i < lexeme.length() - 1; i++) {
            char currentCh = lexeme.charAt(i);
            dot += " -> " + currentCh;
        }

        dot += " -> " + end +"}";

    }
}
