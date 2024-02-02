import file.JsonFileReader;
import implementation.graph.AdjacencyMapGraph;
import implementation.graph.Edge;
import implementation.graph.Vertex;
import linkedIn.controller.LinkedInPanel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws Exception {
        JsonFileReader file=new JsonFileReader();
        file.readFile();
        LinkedInPanel panel=new LinkedInPanel();
        panel.start();
    }
}
