package com.codegenome;

import com.codegenome.extractor.RelationshipExtractor;
import com.codegenome.model.CodeRelationship;
import com.codegenome.parser.JavaFileParser;
import com.github.javaparser.ast.CompilationUnit;

import java.nio.file.Path;
import java.util.List;

public class CodeGenomeApplication {

    static void main() throws Exception {

        Path testFile =
                Path.of("sample-project/src/com/example/User.java");

        // Parse Java file
        JavaFileParser parser = new JavaFileParser();
        CompilationUnit cu = parser.parseFile(testFile);

        // Extract CONTAINS relationships
        RelationshipExtractor extractor =
                new RelationshipExtractor();

        List<CodeRelationship> relationships =
                extractor.extractContainsRelationships(cu);

        // Extract EXTENDS relationships
        List<CodeRelationship> extendsRelationships =
                extractor.extractExtendsRelationships(cu);

        for (CodeRelationship relationship : extendsRelationships) {
            System.out.println(relationship);
        }

        // Extract IMPLEMENTS relationships
        List<CodeRelationship> implementsRelationships =
                extractor.extractImplementsRelationships(cu);

        for (CodeRelationship relationship : implementsRelationships) {
            System.out.println(relationship);
        }

        // Extract CREATES relationships
        List<CodeRelationship> createsRelationships =
                extractor.extractCreatesRelationships(cu);

        for (CodeRelationship relationship : createsRelationships) {
            System.out.println(relationship);
        }

        // Extract CALLS relationships
        List<CodeRelationship> callsRelationships =
                extractor.extractCallsRelationships(cu);

        for (CodeRelationship relationship : callsRelationships) {
            System.out.println(relationship);
        }

        // Display relationships
        for (CodeRelationship relationship : relationships) {
            System.out.println(relationship);
        }
    }
}