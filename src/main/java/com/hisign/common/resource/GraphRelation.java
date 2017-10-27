package com.hisign.common.resource;

import jodd.util.StringUtil;
import org.springframework.beans.factory.BeanNameAware;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by zhouyi1 on 2016/6/27 0027.
 */
public class GraphRelation {

    private Properties relationProperties;
    private String relationLabel;
    private GraphNodeType outLabel;
    private String outId;
    private Properties outProperties;
    private GraphNodeType inLabel;
    private String inId;
    private Properties inProperties;
    private String relationSql;
    private String relationId;

    public void setRelationProperties(Properties relationProperties) {
        this.relationProperties = relationProperties;
    }

    public Properties getRelationProperties() {
        return relationProperties;
    }

    public void setRelationLabel(String relationLabel) {
        this.relationLabel = relationLabel;
    }

    public String getRelationLabel() {
        return relationLabel;
    }

    public void setOutLabel(GraphNodeType outLabel) {
        this.outLabel = outLabel;
    }

    public GraphNodeType getOutLabel() {
        return outLabel;
    }

    public void setOutId(String outId) {
        this.outId = outId;
    }

    public String getOutId() {
        return outId;
    }

    public void setOutProperties(Properties outProperties) {
        this.outProperties = outProperties;
    }

    public Properties getOutProperties() {
        return outProperties;
    }

    public void setInLabel(GraphNodeType inLabel) {
        this.inLabel = inLabel;
    }

    public GraphNodeType getInLabel() {
        return inLabel;
    }

    public void setInId(String inId) {
        this.inId = inId;
    }

    public String getInId() {
        return inId;
    }

    public void setInProperties(Properties inProperties) {
        this.inProperties = inProperties;
    }

    public Properties getInProperties() {
        return inProperties;
    }

    public void setRelationSql(String relationSql) {
        this.relationSql = relationSql;
    }

    public String getRelationSql() {
        return relationSql;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public String getRelationId() {
        return relationId;
    }
}
