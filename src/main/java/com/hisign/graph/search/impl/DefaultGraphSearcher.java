package com.hisign.graph.search.impl;

import com.google.common.base.Strings;
import com.hisign.common.resource.GraphRelation;
import com.hisign.graph.core.module.RelationId;
import com.hisign.graph.search.IGraphSearcher;
import jodd.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tinkerpop.gremlin.structure.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.ref.Reference;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Service(value = "graph.graphSearcher")
public class DefaultGraphSearcher implements IGraphSearcher {
    private static final Log logger = LogFactory.getLog(DefaultGraphSearcher.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private String getSql(GraphRelation relation, String conditionSql) {
        String sql = relation.getRelationSql() + " " + conditionSql;
        return sql;
    }

    @Deprecated
    private List<String> getNodeValues(Map<String, Object> maps, String column) {
//        logger.trace("column:" + column);
        List<String> nodeValues = new ArrayList<String>();
        for (String columnName : column.split(",")) {
            String columnValue = maps.get(columnName) == null ? null : maps.get(columnName).toString();
            if (columnValue != null) {
                nodeValues.add(columnValue);
            }
        }
        return nodeValues;
    }

    private void setProperties(Element element, Properties properties, Map<String, Object> maps) {
        for (Map.Entry entry : properties.entrySet()) {
            String valueName = (entry.getValue()) == null ? "" : entry.getValue().toString();
            //假如第一个字母是小写那么就直接使用原值
            char chr = valueName.charAt(0);
            if (Character.isLowerCase(chr)) {
                element.property((String) entry.getKey(), valueName);
            } else {
                Object value = maps.get(valueName) == null ? valueName : maps.get(valueName);
                element.property((String) entry.getKey(), value);
            }
        }
    }

    private Vertex getVertex(Graph graph, String label, Properties properties, String nodeId, Map<String, Object> maps) {
        Vertex vertex = null;
        if (nodeId == null) {
            return vertex;
        }

        try {
            vertex = graph.addVertex(T.label, label, T.id, nodeId);
            setProperties(vertex, properties, maps);
        } catch (IllegalArgumentException e) {
            logger.trace(String.format("%s is exists!", nodeId));
            vertex = graph.traversal().V().has(T.id, nodeId).next();
        }
        return vertex;
    }

    @Override
    public boolean search(final Graph graph, final Boolean detail, final GraphRelation relation, String conditionSql) {
        String sql = getSql(relation, conditionSql);
        logger.trace("relation:" + relation.getOutLabel() + "-" + relation.getInLabel());
        logger.trace("dig sql:" + sql);

        final Boolean[] incremented = new Boolean[]{false};

        jdbcTemplate.query(sql, new ColumnMapRowMapper() {
            @Override
            public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
                Map<String, Object> maps = super.mapRow(rs, rowNum);
                String outNodeId = maps.get(relation.getOutId()) == null ? null : maps.get(relation.getOutId()).toString();
                String inNodeId = maps.get(relation.getInId()) == null ? null : maps.get(relation.getInId()).toString();

                Vertex outVertex = getVertex(graph, relation.getOutLabel().toString(), relation.getOutProperties(), outNodeId, maps);
                Vertex inVertex = getVertex(graph, relation.getInLabel().toString(), relation.getInProperties(), inNodeId, maps);

                if (inNodeId != null && outNodeId != null) {
                    try {
                        Edge edge = outVertex.addEdge(relation.getRelationLabel(), inVertex, T.id, new RelationId(outNodeId, inNodeId, relation.getRelationLabel()));
                        setProperties(edge, relation.getRelationProperties(), maps);
                        incremented[0] = true;
                    } catch (IllegalArgumentException e) {
                        logger.trace(String.format("relation(%s): %s - %s is exists!", relation.getRelationLabel(), outNodeId, inNodeId));
                    }
                }
                return maps;
            }
        });
        return incremented[0];
    }
}
