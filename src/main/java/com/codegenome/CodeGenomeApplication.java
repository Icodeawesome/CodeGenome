package com.codegenome;

import com.codegenome.graph.ProjectGraphBuilder;
import com.codegenome.model.CodeEntity;
import com.codegenome.model.CodeGraph;
import com.codegenome.neo4j.Neo4jConnection;
import com.codegenome.neo4j.Neo4jGraphRepository;

import java.nio.file.Path;

public class CodeGenomeApplication {

    static void main() throws Exception {

        // Build CodeGenome's in-memory graph
        Path projectPath =
                Path.of("sample-project");

        ProjectGraphBuilder builder =
                new ProjectGraphBuilder();

        CodeGraph graph =
                builder.buildGraph(projectPath);

        System.out.println(
                "Entities extracted: "
                        + graph.getEntityCount()
        );

        // Connect to Neo4j
        Neo4jConnection connection =
                new Neo4jConnection();

        Neo4jGraphRepository repository =
                new Neo4jGraphRepository(
                        connection.getDriver()
                );

        // Save all entities to Neo4j
        for (CodeEntity entity : graph.getEntities()) {

            repository.saveEntity(entity);
        }

        // Save all relationships to Neo4j
        for (var relationship : graph.getRelationships()) {

            repository.saveRelationship(relationship);
        }

        connection.close();

        System.out.println(
                "All entities and relationships saved to Neo4j."
        );

        connection.close();

        System.out.println(
                "All entities saved to Neo4j."
        );
    }
}