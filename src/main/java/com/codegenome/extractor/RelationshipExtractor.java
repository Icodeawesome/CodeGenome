package com.codegenome.extractor;

import com.codegenome.model.CodeRelationship;
import com.codegenome.model.RelationshipType;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

import java.util.ArrayList;
import java.util.List;

public class RelationshipExtractor {

    public List<CodeRelationship> extractContainsRelationships(
            CompilationUnit cu) {

        List<CodeRelationship> relationships = new ArrayList<>();

        cu.findAll(MethodDeclaration.class)
                .forEach(method -> {

                    method.findAncestor(ClassOrInterfaceDeclaration.class)
                            .ifPresent(parent -> {

                                String packageName = cu.getPackageDeclaration()
                                        .map(packageDeclaration ->
                                                packageDeclaration.getNameAsString())
                                        .orElse("");

                                String className = parent.getNameAsString();
                                String methodName = method.getNameAsString();

                                String qualifiedClassName =
                                        packageName.isEmpty()
                                                ? className
                                                : packageName + "." + className;

                                String qualifiedMethodName =
                                        qualifiedClassName + "." + methodName + "()";

                                relationships.add(
                                        new CodeRelationship(
                                                RelationshipType.CONTAINS,
                                                qualifiedClassName,
                                                qualifiedMethodName
                                        )
                                );
                            });
                });

        return relationships;
    }

    public List<CodeRelationship> extractExtendsRelationships(
            CompilationUnit cu) {

        List<CodeRelationship> relationships = new ArrayList<>();

        cu.findAll(ClassOrInterfaceDeclaration.class)
                .forEach(classDeclaration -> {

                    if (classDeclaration.getExtendedTypes().isEmpty()) {
                        return;
                    }

                    String packageName = cu.getPackageDeclaration()
                            .map(packageDeclaration ->
                                    packageDeclaration.getNameAsString())
                            .orElse("");

                    String className = classDeclaration.getNameAsString();

                    String qualifiedClassName =
                            packageName.isEmpty()
                                    ? className
                                    : packageName + "." + className;

                    classDeclaration.getExtendedTypes()
                            .forEach(extendedType -> {

                                String parentName =
                                        extendedType.getNameAsString();

                                String qualifiedParentName =
                                        packageName.isEmpty()
                                                ? parentName
                                                : packageName + "." + parentName;

                                relationships.add(
                                        new CodeRelationship(
                                                RelationshipType.EXTENDS,
                                                qualifiedClassName,
                                                qualifiedParentName
                                        )
                                );
                            });
                });

        return relationships;
    }

    public List<CodeRelationship> extractImplementsRelationships(
            CompilationUnit cu) {

        List<CodeRelationship> relationships = new ArrayList<>();

        cu.findAll(ClassOrInterfaceDeclaration.class)
                .forEach(classDeclaration -> {

                    if (classDeclaration.getImplementedTypes().isEmpty()) {
                        return;
                    }

                    String packageName = cu.getPackageDeclaration()
                            .map(packageDeclaration ->
                                    packageDeclaration.getNameAsString())
                            .orElse("");

                    String className = classDeclaration.getNameAsString();

                    String qualifiedClassName =
                            packageName.isEmpty()
                                    ? className
                                    : packageName + "." + className;

                    classDeclaration.getImplementedTypes()
                            .forEach(implementedType -> {

                                String interfaceName =
                                        implementedType.getNameAsString();

                                String qualifiedInterfaceName =
                                        packageName.isEmpty()
                                                ? interfaceName
                                                : packageName + "."
                                                + interfaceName;

                                relationships.add(
                                        new CodeRelationship(
                                                RelationshipType.IMPLEMENTS,
                                                qualifiedClassName,
                                                qualifiedInterfaceName
                                        )
                                );
                            });
                });

        return relationships;
    }

    public List<CodeRelationship> extractCreatesRelationships(
            CompilationUnit cu) {

        List<CodeRelationship> relationships = new ArrayList<>();

        cu.findAll(com.github.javaparser.ast.expr.ObjectCreationExpr.class)
                .forEach(objectCreation -> {

                    String packageName = cu.getPackageDeclaration()
                            .map(packageDeclaration ->
                                    packageDeclaration.getNameAsString())
                            .orElse("");

                    String createdClassName =
                            objectCreation.getType().getNameAsString();

                    String qualifiedCreatedClassName =
                            packageName.isEmpty()
                                    ? createdClassName
                                    : packageName + "." + createdClassName;

                    objectCreation.findAncestor(
                                    ClassOrInterfaceDeclaration.class)
                            .ifPresent(parent -> {

                                String parentClassName =
                                        parent.getNameAsString();

                                String qualifiedParentName =
                                        packageName.isEmpty()
                                                ? parentClassName
                                                : packageName + "."
                                                + parentClassName;

                                relationships.add(
                                        new CodeRelationship(
                                                RelationshipType.CREATES,
                                                qualifiedParentName,
                                                qualifiedCreatedClassName
                                        )
                                );
                            });
                });

        return relationships;
    }
}
