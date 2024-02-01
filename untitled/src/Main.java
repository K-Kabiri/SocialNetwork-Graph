import implementation.graph.AdjacencyMapGraph;
import implementation.graph.Vertex;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        AdjacencyMapGraph<Integer , Integer> test = new AdjacencyMapGraph<>(false);
        Vertex<Integer> v1 =  test.insertVertex(1);
        Vertex<Integer> v2 =   test.insertVertex(2);
        Vertex<Integer> v3 =  test.insertVertex(3);
        Vertex<Integer> v4 =  test.insertVertex(4);
        Vertex<Integer> v5 =   test.insertVertex(5);
        Vertex<Integer> v6 =  test.insertVertex(6);
        Vertex<Integer> v7 =  test.insertVertex(7);
        Vertex<Integer> v8 =  test.insertVertex(8);
        Vertex<Integer> v9 =  test.insertVertex(9);
        Vertex<Integer> v10 =  test.insertVertex(10);
        test.insertEdge(v1,v2,0);
        test.insertEdge(v1,v3,0);
        test.insertEdge(v2,v4,0);
        test.insertEdge(v4,v5,0);
        test.insertEdge(v5,v2,0);
        test.insertEdge(v5,v6,0);
        test.insertEdge(v7,v6,0);
        test.insertEdge(v8,v7,0);
        test.insertEdge(v8,v9,0);
        test.insertEdge(v9,v10,0);
        Set<Vertex<Integer>> set = new HashSet<>();
        Map<Vertex<Integer>,Integer> map = new HashMap<>();
        test.BFS(v3,set,map);
        for(Map.Entry<Vertex<Integer>, Integer> e : map.entrySet())
        {
            System.out.println(e.getKey().getElement() + "***"+ e.getValue());
        }

    }
}
