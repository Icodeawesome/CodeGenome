package com.codegenome.neo4j;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Record;
import org.neo4j.driver.Session;

import java.util.ArrayList;
import java.util.List;

public class Neo4jQueryRepository {

    private final Driver driver;

    public Neo4jQueryRepository(Driver driver) {
        this.driver = driver;
    }

    public List<String> findOutgoingRelationships(
            String qualifiedName) {

        String cypher = """
                MATCH (source)-[r]->(target)
                WHERE source.qualifiedName = $qualifiedName
                RETURN type(r) AS relationship,
                       target.qualifiedName AS target
                ORDER BY relationship, target
                """;

        List<String> results = new ArrayList<>();

        try (Session session = driver.session()) {

            var records = session.run(
                    cypher,
                    java.util.Map.of(
                            "qualifiedName",
                            qualifiedName
                    )
            );

            while (records.hasNext()) {

                Record record = records.next();

                String relationship =
                        record.get("relationship").asString();

                String target =
                        record.get("target").asString();

                results.add(
                        relationship + " -> " + target
                );
            }
        }

        return results;
    }

    public List<String> findCalledMethods(
            String methodQualifiedName) {

        String cypher = """
            MATCH (method:METHOD)-[:CALLS]->(called:METHOD)
            WHERE method.qualifiedName = $qualifiedName
            RETURN called.qualifiedName AS calledMethod
            ORDER BY calledMethod
            """;

        List<String> results = new ArrayList<>();

        try (Session session = driver.session()) {

            var records = session.run(
                    cypher,
                    java.util.Map.of(
                            "qualifiedName",
                            methodQualifiedName
                    )
            );

            while (records.hasNext()) {

                Record record = records.next();

                results.add(
                        record.get("calledMethod").asString()
                );
            }
        }

        return results;
    }

    public List<String> findCallingMethods(
            String methodQualifiedName) {

        String cypher = """
            MATCH (caller:METHOD)-[:CALLS]->(method:METHOD)
            WHERE method.qualifiedName = $qualifiedName
            RETURN caller.qualifiedName AS callerMethod
            ORDER BY callerMethod
            """;

        List<String> results = new ArrayList<>();

        try (Session session = driver.session()) {

            var records = session.run(
                    cypher,
                    java.util.Map.of(
                            "qualifiedName",
                            methodQualifiedName
                    )
            );

            while (records.hasNext()) {

                Record record = records.next();

                results.add(
                        record.get("callerMethod").asString()
                );
            }
        }

        return results;
    }
}