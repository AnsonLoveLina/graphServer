package com.hisign.graph.core.module;

import java.util.Arrays;

public class RelationId {
    private Object outId;
    private Object inId;
    private String relationLabel;

    public RelationId(Object outId, Object inId, String relationLabel) {
        this.outId = outId;
        this.inId = inId;
        this.relationLabel = relationLabel;
    }

    public Object getOutId() {
        return outId;
    }

    public Object getInId() {
        return inId;
    }

    public String getRelationLabel() {
        return relationLabel;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new int[]{getOutId().hashCode(), getInId().hashCode(), getRelationLabel().hashCode()});
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RelationId)) {
            return false;
        }
        if (obj == null) {
            return false;
        }

        RelationId target = (RelationId) obj;
//        return getPkValue().equals(target.getPkValue()) && getType().equals(target.getType());
        return getOutId().equals(target.getOutId()) && getInId().equals(target.getInId()) && getRelationLabel().equals(target.getRelationLabel());
    }
}
