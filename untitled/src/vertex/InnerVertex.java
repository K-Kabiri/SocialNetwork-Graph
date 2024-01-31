package vertex;

import edge.Edge;
import position.Position;

import java.util.HashMap;
import java.util.Map;

public class InnerVertex<V> implements Vertex<V>{
    private V element;
    private Position<Vertex<V>> pos;
    private Map<Vertex<V>, Edge> outgoing, incoming;

    public InnerVertex(V elm, boolean graphIsDirected) {
        element = elm;
        outgoing = new HashMap<>();
        if (graphIsDirected) {
            incoming = new HashMap<>();
        } else {
            incoming = outgoing;
        }
    }

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

    public Map<Vertex<V>, Edge> getOutgoing() {
        return outgoing;
    }

    public Map<Vertex<V>, Edge> getIncoming() {
        return incoming;
    }
}
