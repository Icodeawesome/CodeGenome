package com.codegenome.neo4j;

import com.codegenome.model.CodeEntity;
import com.codegenome.model.CodeRelationship;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;

import java.util.Map;

public class Neo4jGraphRepository {

    private final Driver driver;

    public Neo4jGraphRepository(Driver driver) {
        this.driver = driver;
    }

    public void saveEntity(CodeEntity entity) {

        String label = entity.getType().name();

        String cypher = """
                MERGE (n:%s {
                    qualifiedName: $qualifiedName
                })
                SET n.name = $name
                """.formatted(label);

        try (Session session = driver.session()) {

            session.run(
                    cypher,
                    Map.of(
                            "qualifiedName",
                            entity.getQualifiedName(),

                            "name",
                            entity.getName()
                    )
            );
        }

        System.out.println(
                "Saved entity: " + entity
        );
    }

    public void saveRelationship(CodeRelationship relationship) {

        String relationshipType =
                relationship.getType().name();

        String cypher = """
            MATCH (source {qualifiedName: $source})
            MATCH (target {qualifiedName: $target})
            MERGE (source)-[:%s]->(target)
            """.formatted(relationshipType);

        try (Session session = driver.session()) {

            session.run(
                    cypher,
                    Map.of(
                            "source",
                            relationship.getSource(),

                            "target",
                            relationship.getTarget()
                    )
            );
        }

        System.out.println(
                "Saved relationship: " + relationship
        );
    }
}