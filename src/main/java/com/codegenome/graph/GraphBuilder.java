package com.codegenome.graph;

import com.codegenome.model.CodeEntity;
import com.codegenome.model.CodeGraph;
import com.codegenome.model.CodeRelationship;

import java.util.List;

public class GraphBuilder {

    public CodeGraph buildGraph(
            List<CodeEntity> entities,
            List<CodeRelationship> relationships) {

        CodeGraph graph = new CodeGraph();

        // Add all entities as graph nodes
        for (CodeEntity entity : entities) {
            graph.addEntity(entity);
        }

        // Add all relationships as graph edges
        for (CodeRelationship relationship : relationships) {
            graph.addRelationship(relationship);
        }

        return graph;
    }
}