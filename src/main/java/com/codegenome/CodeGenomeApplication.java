package com.codegenome;

import com.codegenome.parser.JavaFileParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

import java.nio.file.Path;

public class CodeGenomeApplication {
    static void main() throws Exception {
        Path testFile = Path.of("sample-project/src/User.java");

        JavaFileParser parser = new JavaFileParser();
        CompilationUnit cu = parser.parseFile(testFile);

        cu.findAll(ClassOrInterfaceDeclaration.class)
                .forEach(cls -> System.out.println("Class found: " + cls.getNameAsString()));

        cu.findAll(MethodDeclaration.class)
                .forEach(method -> System.out.println("Method found: " + method.getNameAsString()));
    }
}
