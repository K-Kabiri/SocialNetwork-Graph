package edge;

import position.Position;
import vertex.Vertex;

public class InnerEdge<E> implements Edge<E>{
    private E element;
    private Position<Edge<E>> pos;
    private Vertex[] endpoints;

    public InnerEdge(Vertex u, Vertex v, E elem) {
        element = elem;
        endpoints = (Vertex[]) new Vertex[]{u, v};
    }

    @Override
    public E getElement() {
        return element;
    }

    @Override
    public void setElement(E elem) {
        element = elem;
    }

    public Vertex[] getEndpoints() {
        return endpoints;
    }

    public Position<Edge<E>> getPos() {
        return pos;
    }

    public void setPos(Position<Edge<E>> pos) {
        this.pos = pos;
    }
}

