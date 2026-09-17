package com.codegenome.model;

public class CodeRelationship {

    private final RelationshipType type;
    private final String source;
    private final String target;

    public CodeRelationship(
            RelationshipType type,
            String source,
            String target) {

        this.type = type;
        this.source = source;
        this.target = target;
    }

    public RelationshipType getType() {
        return type;
    }

    public String getSource() {
        return source;
    }

    public String getTarget() {
        return target;
    }

    @Override
    public String toString() {
        return source + " -" + type + "-> " + target;
    }
}