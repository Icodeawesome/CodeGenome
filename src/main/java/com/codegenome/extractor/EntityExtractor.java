package com.codegenome.extractor;

import com.codegenome.model.CodeEntity;
import com.codegenome.model.EntityType;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

import java.util.ArrayList;
import java.util.List;

public class EntityExtractor {

    public List<CodeEntity> extractEntities(CompilationUnit cu) {

        List<CodeEntity> entities = new ArrayList<>();

        // Extract package
        cu.getPackageDeclaration().ifPresent(packageDeclaration -> {
            String packageName = packageDeclaration.getNameAsString();

            entities.add(
                    new CodeEntity(
                            EntityType.PACKAGE,
                            packageName,
                            packageName
                    )
            );
        });

        // Extract classes and interfaces
        cu.findAll(ClassOrInterfaceDeclaration.class)
                .forEach(declaration -> {

                    EntityType type = declaration.isInterface()
                            ? EntityType.INTERFACE
                            : EntityType.CLASS;

                    String name = declaration.getNameAsString();

                    String qualifiedName = getQualifiedName(cu, name);

                    entities.add(
                            new CodeEntity(
                                    type,
                                    name,
                                    qualifiedName
                            )
                    );
                });

        // Extract methods
        cu.findAll(MethodDeclaration.class)
                .forEach(method -> {

                    method.findAncestor(ClassOrInterfaceDeclaration.class)
                            .ifPresent(parent -> {

                                String methodName =
                                        method.getNameAsString();

                                String ownerName =
                                        getQualifiedName(
                                                cu,
                                                parent.getNameAsString()
                                        );

                                String qualifiedName =
                                        ownerName + "." + methodName + "()";

                                entities.add(
                                        new CodeEntity(
                                                EntityType.METHOD,
                                                methodName,
                                                qualifiedName
                                        )
                                );
                            });
                });

        return entities;
    }

    private String getQualifiedName(
            CompilationUnit cu,
            String name) {

        return cu.getPackageDeclaration()
                .map(packageDeclaration ->
                        packageDeclaration.getNameAsString()
                                + "." + name)
                .orElse(name);
    }
}
