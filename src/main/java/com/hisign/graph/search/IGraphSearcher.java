package com.hisign.graph.search;

import com.hisign.common.resource.GraphRelation;
import org.apache.tinkerpop.gremlin.structure.Graph;

import java.util.Set;

public interface IGraphSearcher {
    boolean search(Graph graph, boolean detail, GraphRelation relation, String conditionSql);
}
