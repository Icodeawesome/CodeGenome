package com.codegenome.graph;

import com.codegenome.extractor.EntityExtractor;
import com.codegenome.extractor.RelationshipExtractor;
import com.codegenome.model.CodeEntity;
import com.codegenome.model.CodeGraph;
import com.codegenome.model.CodeRelationship;
import com.codegenome.parser.JavaFileParser;
import com.codegenome.scanner.ProjectScanner;
import com.github.javaparser.ast.CompilationUnit;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class ProjectGraphBuilder {

    private final ProjectScanner scanner;
    private final JavaFileParser parser;
    private final EntityExtractor entityExtractor;
    private final RelationshipExtractor relationshipExtractor;

    public ProjectGraphBuilder() {
        scanner = new ProjectScanner();
        parser = new JavaFileParser();
        entityExtractor = new EntityExtractor();
        relationshipExtractor = new RelationshipExtractor();
    }

    public CodeGraph buildGraph(Path projectPath)
            throws IOException {

        CodeGraph graph = new CodeGraph();

        List<Path> javaFiles =
                scanner.scanJavaFiles(projectPath);

        for (Path javaFile : javaFiles) {

            CompilationUnit cu =
                    parser.parseFile(javaFile);

            // Extract entities
            List<CodeEntity> entities =
                    entityExtractor.extractEntities(cu);

            for (CodeEntity entity : entities) {
                graph.addEntity(entity);
            }

            // Extract relationships
            addRelationships(
                    graph,
                    relationshipExtractor.extractContainsRelationships(cu)
            );

            addRelationships(
                    graph,
                    relationshipExtractor.extractExtendsRelationships(cu)
            );

            addRelationships(
                    graph,
                    relationshipExtractor.extractImplementsRelationships(cu)
            );

            addRelationships(
                    graph,
                    relationshipExtractor.extractCreatesRelationships(cu)
            );

            addRelationships(
                    graph,
                    relationshipExtractor.extractCallsRelationships(cu)
            );
        }

        return graph;
    }

    private void addRelationships(
            CodeGraph graph,
            List<CodeRelationship> relationships) {

        for (CodeRelationship relationship : relationships) {
            graph.addRelationship(relationship);
        }
    }
}
