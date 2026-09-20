package com.codegenome.model;

import java.util.ArrayList;
import java.util.List;

public class CodeGraph {

    private final List<CodeEntity> entities;
    private final List<CodeRelationship> relationships;

    public CodeGraph() {
        entities = new ArrayList<>();
        relationships = new ArrayList<>();
    }

    public void addEntity(CodeEntity entity) {

        for (CodeEntity existingEntity : entities) {

            if (existingEntity.getType() == entity.getType()
                    && existingEntity.getQualifiedName()
                    .equals(entity.getQualifiedName())) {

                return;
            }
        }

        entities.add(entity);
    }

    public void addRelationship(CodeRelationship relationship) {

        for (CodeRelationship existingRelationship : relationships) {

            if (existingRelationship.getType()
                    == relationship.getType()
                    && existingRelationship.getSource()
                    .equals(relationship.getSource())
                    && existingRelationship.getTarget()
                    .equals(relationship.getTarget())) {

                return;
            }
        }

        relationships.add(relationship);
    }

    public List<CodeEntity> getEntities() {
        return entities;
    }

    public List<CodeRelationship> getRelationships() {
        return relationships;
    }

    public int getEntityCount() {
        return entities.size();
    }

    public int getRelationshipCount() {
        return relationships.size();
    }
}
