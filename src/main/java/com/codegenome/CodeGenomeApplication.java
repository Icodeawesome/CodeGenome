package com.codegenome;

import java.util.List;
import com.codegenome.graph.ProjectGraphBuilder;
import com.codegenome.model.CodeEntity;
import com.codegenome.model.CodeGraph;
import com.codegenome.neo4j.Neo4jConnection;
import com.codegenome.neo4j.Neo4jGraphRepository;
import com.codegenome.neo4j.Neo4jQueryRepository;

import java.nio.file.Path;

public class CodeGenomeApplication {

    static void main() throws Exception {

        // Build CodeGenome's in-memory graph
        Path projectPath =
                Path.of("sample-project/src");

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

        // Hardcoded dependency analysis
        Neo4jQueryRepository queryRepository =
                new Neo4jQueryRepository(
                        connection.getDriver()
                );

        System.out.println();
        System.out.println("=== USER DEPENDENCIES ===");

        List<String> dependencies =
                queryRepository.findOutgoingRelationships(
                        "com.example.User"
                );

        for (String dependency : dependencies) {

            System.out.println(dependency);
        }

        System.out.println();
        System.out.println("=== METHOD DEPENDENCIES ===");

        List<String> calledMethods =
                queryRepository.findCalledMethods(
                        "com.example.User.login()"
                );

        for (String calledMethod : calledMethods) {

            System.out.println(
                    "CALLS -> " + calledMethod
            );
        }

        System.out.println();
        System.out.println("=== METHOD IMPACT ANALYSIS ===");

        List<String> callingMethods =
                queryRepository.findCallingMethods(
                        "com.example.Database.connect()"
                );

        for (String callingMethod : callingMethods) {

            System.out.println(
                    "CALLED BY -> " + callingMethod
            );
        }

        connection.close();

        System.out.println(
                "All entities and relationships saved to Neo4j."
        );
    }
}