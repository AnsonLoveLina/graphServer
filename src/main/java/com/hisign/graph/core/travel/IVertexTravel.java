package com.hisign.graph.core.travel;

import org.apache.tinkerpop.gremlin.structure.Direction;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import java.util.Iterator;

public interface IVertexTravel {
    public void nodeCallBack(Vertex vertex, int index);

    public void outEdgeCallBack(Vertex outVertex, Integer outIndex, Vertex inVertex, Integer inIndex, Edge edge);
}
