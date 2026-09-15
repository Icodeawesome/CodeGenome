package com.codegenome;

import com.codegenome.extractor.EntityExtractor;
import com.codegenome.model.CodeEntity;
import com.codegenome.parser.JavaFileParser;
import com.github.javaparser.ast.CompilationUnit;

import java.nio.file.Path;
import java.util.List;

public class CodeGenomeApplication {

    static void main() throws Exception {

        Path testFile =
                Path.of("sample-project/src/com/example/User.java");

        // Parse the Java file
        JavaFileParser parser = new JavaFileParser();
        CompilationUnit cu = parser.parseFile(testFile);

        // Extract entities
        EntityExtractor extractor = new EntityExtractor();
        List<CodeEntity> entities =
                extractor.extractEntities(cu);

        // Display extracted entities
        for (CodeEntity entity : entities) {
            System.out.println(entity);
        }
    }
}