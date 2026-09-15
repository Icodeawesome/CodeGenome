package com.codegenome.model;

public class CodeEntity {

    private final EntityType type;
    private final String name;
    private final String qualifiedName;

    public CodeEntity(EntityType type, String name, String qualifiedName) {
        this.type = type;
        this.name = name;
        this.qualifiedName = qualifiedName;
    }

    public EntityType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getQualifiedName() {
        return qualifiedName;
    }

    @Override
    public String toString() {
        return type + " : " + qualifiedName;
    }
}
