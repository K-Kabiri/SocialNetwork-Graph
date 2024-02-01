package graph;

import graph.Graph;
import position.Position;
import position.PositionalList;

import java.util.*;

public class AdjacencyMatrix<V,E> implements Graph<V,E> {
    // ------------------- innerVertex class ---------------------
    private class InnerVertex<V> implements Vertex<V> {
        // -------------- Fields ------------------------
        private V element;
        private Position<Vertex<V>> pos;

        // ------------- Constructor --------------------
        public InnerVertex(V elm, boolean graphIsDirected) {
            element = elm;
        }

        // -------------- Setter & Getter ----------------
        @Override
        public V getElement() {
            return element;
        }

        @Override
        public void setElement(V elem) {
            element = elem;
        }

        public void setPos(Position<Vertex<V>> pos) {
            this.pos = pos;
        }

        public Position<Vertex<V>> getPos() {
            return pos;
        }

    }

    // ------------------- innerEdge class ---------------------

    private class InnerEdge<E> implements Edge<E> {

        // -------------- Fields----------------
        private E element;
        private Position<Edge<E>> pos;
        private Vertex<V>[] endpoints;

        // -------------- Constructor ----------------

        public InnerEdge(Vertex<V> u, Vertex<V> v, E elem) {
            element = elem;
            endpoints = (Vertex<V>[]) new Vertex[]{u, v};
        }

        // -------------- Setter & Getter ----------------
        @Override
        public E getElement() {
            return element;
        }

        @Override
        public void setElement(E elem) {
            element = elem;
        }

        public Vertex<V>[] getEndpoints() {
            return endpoints;
        }

        public Position<Edge<E>> getPos() {
            return pos;
        }

        public void setPos(Position<Edge<E>> pos) {
            this.pos = pos;
        }
    }
    //---------------------------- Fields -------------------------------------
    private ArrayList<Vertex<V>> vertices;
    private PositionalList<Edge<E>> edges;
    private boolean directed ;
    private  Edge[][] graph;
    //--------------------------- Constructor --------------------------------
    public AdjacencyMatrix(int size,boolean directed){
        this.directed = directed;
        this.graph = new InnerEdge[size][size];
        vertices = new ArrayList<>();
    }
    // ----------------------------- insert -------------------------------
    public  Edge[][] resizeArray(Edge[][] originalArray, int newSize) {
        int rows = originalArray.length;
        int columns = originalArray[0].length;
        Edge[][] resizedArray = (Edge[][]) new Object[newSize][columns];
        for (int i = 0; i < rows; i++) {
            System.arraycopy(originalArray[i], 0, resizedArray[i], 0, columns);
        }
        graph = resizedArray;
        return resizedArray;
    }
    public Vertex<V> insertVertex(V element){
        InnerVertex<V> vertex = new InnerVertex<>(element,this.directed);
        vertices.add(vertex);
        if(vertices.size()>graph.length)
            resizeArray (graph,(graph.length*2));
        return vertex;
    }
    public Edge<E> insertEdge(Vertex<V> org, Vertex<V> des, E element)
    {
        // control
        InnerEdge<E> edge = new InnerEdge<>(org,des,element);
        int indexOfOrigin = (vertices.indexOf(org));
        int indexOfDestination = vertices.indexOf(des);
        if(graph[indexOfOrigin][indexOfDestination] == null && graph[indexOfDestination][indexOfOrigin] == null)
        {
            graph[indexOfDestination][indexOfDestination] = edge;
            graph[indexOfOrigin][indexOfDestination] = edge;
            edges.addLast(edge);
        }
        else
            throw new RuntimeException("this edge has been existed");
        return edge;
    }
    //-----------------------------------------------------------------------------------
    @Override
    public int numVertices() {
        return vertices.size();
    }

    @Override
    public Iterable<Vertex<V>> vertices() {
        return vertices;
    }

    @Override
    public int numEdges() {
        return edges.size();
    }
    @Override
    public PositionalList<Edge<E>> edges() {
        return edges;
    }
    public Vertex<V> validate(Vertex<V>v) throws Exception {
        if(vertices.contains(v))
            return v;
        else
            throw new Exception("not exist");
    }
    @Override
    public int outDegree(Vertex<V> v) {
        InnerVertex<V> vert = null;
        try {
            vert = (InnerVertex<V>) validate(v);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return graph[vertices.indexOf(vert)].length;
    }

    @Override
    public int inDegree(Vertex<V> v) {
        InnerVertex<V> vert = null;
        try {
            vert = (InnerVertex<V>) validate(v);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return graph[vertices.indexOf(vert)].length;
    }

    @Override
    public Iterable<Edge<E>> outgoingEdges(Vertex<V> v) {
        InnerVertex<V> vert = null;
        try {
            vert = (InnerVertex<V>) validate(v);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return List.of(graph[vertices.indexOf(vert)]);
    }

    @Override
    public Iterable<Edge<E>> incomingEdges(Vertex<V> v) {
        ArrayList<Edge> saveEdges = new ArrayList<>();
        int index = vertices.indexOf(v);
        for(Edge edge : graph[index])
        {
            if(edge != null)
                saveEdges.add(edge);
        }
        return edges;
    }
    @Override
    public Edge<E> getEdge(Vertex<V> v, Vertex<V> u) {
        // null
        return graph[vertices.indexOf(v)][vertices.indexOf(u)];
    }

    @Override
    public Vertex<V>[] endVertices(Edge<E> e) {
        //if(edges.contain(e))
        return ((InnerEdge)e).getEndpoints();
    }
    @Override
    public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws IllegalArgumentException {
        InnerEdge<E> edge = (InnerEdge<E>) e;
        Vertex<V>[] endpoints = edge.getEndpoints();
        if (endpoints[0] == v) {
            return endpoints[1];
        } else if (endpoints[1] == v) {
            return endpoints[0];
        } else {
            throw new IllegalArgumentException("v is not incident to this edge!");
        }
    }
    //---------------------- remove ---------------------
    public  Edge[][] removeRowAndColumn(Edge[][] originalArray, int rowIndex, int columnIndex) {
        int rows = originalArray.length;
        int columns = originalArray[0].length;

        // Create a new array with one less row and one less column
        Edge[][] newArray = (Edge[][]) new Object[rows - 1][columns - 1];

        int destRow = 0;
        for (int i = 0; i < rows; i++) {
            if (i != rowIndex) {
                int destColumn = 0;

                // Copy elements excluding the specified row and column
                for (int j = 0; j < columns; j++) {
                    if (j != columnIndex) {
                        newArray[destRow][destColumn++] = originalArray[i][j];
                    }
                }
                destRow++;
            }
        }
        originalArray = newArray;
        return newArray;
    }
    public void removeVertex(Vertex<V> v) {
        //InnerVertex<V> vert = validate(v);
        int index = vertices.indexOf(v);
        removeRowAndColumn(graph,index,index);
        vertices.remove(index);
    }
    public void removeEdge(Edge<E> e)
    {
        int index1 = vertices.indexOf(((InnerEdge<E>)e).getEndpoints()[0]);
        int index2 = vertices.indexOf(((InnerEdge<E>)e).getEndpoints()[1]);
        graph[index1][index2] = null;
        graph[index2][index1]= null;

    }
}

