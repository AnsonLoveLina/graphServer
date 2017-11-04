package com.hisign.controller;

import com.google.common.collect.Sets;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.T;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

public class GraphControllerTest {
    private static Graph init(){
        Graph graph = TinkerGraph.open();
        Vertex vertex11 = graph.addVertex(T.label,"ajid",T.id,"ajid1");
        vertex11.property("value","ajid1");
        vertex11.property("name","案件1");

        Vertex vertex = graph.addVertex(T.label,"groupid",T.id,"groupid1");
        vertex.property("value","groupid1");
        vertex.property("name","专案组1");

        Vertex vertex1 = graph.addVertex(T.label,"taskid",T.id,"taskid1");
        vertex1.property("value","taskid1");
        vertex1.property("name","任务名称1");

        Vertex vertex2 = graph.addVertex(T.label,"taskid",T.id,"taskid2");
        vertex2.property("value","taskid2");
        vertex2.property("name","任务名称2");

        Vertex vertex3 = graph.addVertex(T.label,"taskid",T.id,"taskid3");
        vertex3.property("value","taskid3");
        vertex3.property("name","任务名称3");

        Vertex vertex4 = graph.addVertex(T.label,"taskid",T.id,"taskid4");
        vertex4.property("value","taskid4");
        vertex4.property("name","任务名称4");

        Vertex vertex5 = graph.addVertex(T.label,"taskid",T.id,"taskid5");
        vertex5.property("value","taskid5");
        vertex5.property("name","任务名称5");

        Vertex vertex6 = graph.addVertex(T.label,"taskid",T.id,"taskid6");
        vertex6.property("value","taskid6");
        vertex6.property("name","任务名称6");

        Vertex vertex7 = graph.addVertex(T.label,"fkid",T.id,"fkid1");
        vertex7.property("value","fkid1");
        vertex7.property("name","反馈1");

        Vertex vertex8 = graph.addVertex(T.label,"fkid",T.id,"fkid2");
        vertex8.property("value","fkid2");
        vertex8.property("name","反馈2");

        Vertex vertex9 = graph.addVertex(T.label,"fkid",T.id,"fkid3");
        vertex9.property("value","fkid3");
        vertex9.property("name","反馈3");

        Vertex vertex10 = graph.addVertex(T.label,"fkid",T.id,"fkid4");
        vertex10.property("value","fkid4");
        vertex10.property("name","反馈4");

        Vertex vertex12 = graph.addVertex(T.label,"taskid",T.id,"taskid7");
        vertex12.property("value","taskid7");
        vertex12.property("name","任务名称7");

        Vertex vertex13 = graph.addVertex(T.label,"taskid",T.id,"taskidzz");
        vertex13.property("value","taskid7");
        vertex13.property("name","任务名称7");

        vertex.addEdge("涉及案件",vertex11);
        vertex.addEdge("新任务",vertex1);
        vertex.addEdge("新任务",vertex2);
        vertex.addEdge("新任务",vertex3);
        vertex2.addEdge("任务补充",vertex4);
        vertex2.addEdge("反馈",vertex7);
        vertex3.addEdge("任务补充",vertex5);
        vertex3.addEdge("反馈",vertex8);
        vertex7.addEdge("追加任务",vertex6);
        vertex4.addEdge("反馈",vertex9);
        vertex8.addEdge("追加任务",vertex12);
        vertex5.addEdge("反馈",vertex10,T.id,"反馈");

        return graph;
    }

    @Test
    public void getJsonStr() throws Exception {
        GraphController controller = new GraphController();
        Graph graph = init();
        String str = controller.getJsonStr(graph);
        System.out.println("str = " + str);
    }

}