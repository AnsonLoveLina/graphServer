package com.hisign.graph.search.impl;

import com.hisign.GraphServerStartApplication;
import com.hisign.common.resource.ASpringContextInit;
import com.hisign.common.resource.GraphRelation;
import com.hisign.graph.search.IGraphSearcher;
import org.apache.tinkerpop.gremlin.structure.*;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Iterator;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class) //使用junit4进行测试
@SpringApplicationConfiguration(classes = GraphServerStartApplication.class) //加载配置文件
@WebAppConfiguration
public class DefaultGraphSearcherTest {

    @Autowired
    IGraphSearcher iGraphSearcher;

    private static String printVertex(Vertex vertex) {
        StringBuilder stringBuilder = new StringBuilder();
        Object id = vertex.id();
        stringBuilder.append(String.format("%s   ", id));
//        Iterator<VertexProperty<Object>> vertexPropertyIterator = vertex.properties();
//        for (;vertexPropertyIterator.hasNext();){
//            VertexProperty<Object> vertexProperty = vertexPropertyIterator.next();
//            String key = vertexProperty.label();
//            String value = vertex.value(key);
//            stringBuilder.append(String.format("%s-%s   ",key,value));
////            Iterator<Property<String>> propertyIterator = vertexProperty.properties();
////            for (;propertyIterator.hasNext();){
////                Property<String> property = propertyIterator.next();
////                String value = property.value();
////                System.out.println("value = " + value);
////            }
//        }
        return stringBuilder.toString();
    }

    private void printGraph(Graph graph) {
        Iterator<Vertex> iterator = graph.vertices();
        StringBuilder nodes = new StringBuilder();
        StringBuilder edges = new StringBuilder();
        for (; iterator.hasNext(); ) {
            Vertex vertex = iterator.next();
            String fromNode = printVertex(vertex);
            nodes.append(fromNode);
            Iterator<Edge> edgeIterator = vertex.edges(Direction.OUT);
            for (; edgeIterator.hasNext(); ) {
                Edge edge = edgeIterator.next();
                Vertex vertex1 = edge.inVertex();
                String toNode = printVertex(vertex1);
                edges.append(String.format("fromNode:(%s) and toNode:(%s)", fromNode, toNode));
            }
        }
        System.out.println("nodes = " + nodes);
        System.out.println("edges = " + edges);
    }

    @Test
    public void search() throws Exception {
        ApplicationContext context = ASpringContextInit.getContext();
        Map<String, GraphRelation> relationMap = context.getBeansOfType(GraphRelation.class);
        Graph graph = TinkerGraph.open();
        for (GraphRelation relation : relationMap.values()) {
            iGraphSearcher.search(graph, false, relation, " and groupid='groupid1' and deleteflag='1' ");
        }
        printGraph(graph);
    }

}