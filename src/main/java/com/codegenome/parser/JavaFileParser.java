package com.codegenome.parser;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;

import java.io.IOException;
import java.nio.file.Path;

public class JavaFileParser {

    public CompilationUnit parseFile(Path filePath) throws IOException {
        return StaticJavaParser.parse(filePath);
    }
}
