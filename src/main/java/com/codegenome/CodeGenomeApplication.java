package com.codegenome;

import com.codegenome.graph.ProjectGraphBuilder;
import com.codegenome.model.CodeEntity;
import com.codegenome.model.CodeGraph;
import com.codegenome.model.CodeRelationship;

import java.nio.file.Path;

public class CodeGenomeApplication {

    static void main() throws Exception {

        Path projectPath =
                Path.of("sample-project");

        ProjectGraphBuilder builder =
                new ProjectGraphBuilder();

        CodeGraph graph =
                builder.buildGraph(projectPath);

        System.out.println("=== ENTITIES ===");

        for (CodeEntity entity : graph.getEntities()) {
            System.out.println(entity);
        }

        System.out.println();

        System.out.println("=== RELATIONSHIPS ===");

        for (CodeRelationship relationship :
                graph.getRelationships()) {

            System.out.println(relationship);
        }

        System.out.println();
        System.out.println("=== GRAPH STATISTICS ===");
        System.out.println("Total entities: " + graph.getEntityCount());
        System.out.println("Total relationships: " + graph.getRelationshipCount());
    }
}