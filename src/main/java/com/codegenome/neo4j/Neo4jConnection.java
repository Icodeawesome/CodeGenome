package com.codegenome.neo4j;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;

public class Neo4jConnection {

    private final Driver driver;

    public Neo4jConnection() {

        String uri = "neo4j://127.0.0.1:7687";
        String username = "neo4j";
        String password = "CodeZ123$";

        driver = GraphDatabase.driver(
                uri,
                AuthTokens.basic(username, password)
        );

        driver.verifyConnectivity();

        System.out.println("Connected to Neo4j successfully!");
    }

    public Driver getDriver() {
        return driver;
    }

    public void close() {
        driver.close();
    }
}