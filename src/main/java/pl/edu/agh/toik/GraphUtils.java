package pl.edu.agh.toik;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseMultigraph;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pkala on 5/26/15.
 */
public class GraphUtils {
    public static Graph<String, MyLink> graphFromJson(String filepath) {
        Graph<String, MyLink> graph = new UndirectedSparseMultigraph<String, MyLink>();
        List<String> verticesList = new ArrayList<String>();

        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(filepath));
            JSONObject jsonObject = (JSONObject) obj;

            JSONArray vertices = (JSONArray) jsonObject.get("nodes");
            for(Object vertex : vertices) {
                JSONObject jsonVertex = (JSONObject) vertex;
                graph.addVertex(jsonVertex.get("name").toString());
            }

            JSONArray edges = (JSONArray) jsonObject.get("links");
            for(Object edge : edges) {
                JSONObject jsonEdge = (JSONObject) edge;
                int sourceVertexIndex = Integer.parseInt(jsonEdge.get("source").toString());
                int targetVertexIndex = Integer.parseInt(jsonEdge.get("target").toString());
                Integer weight = Integer.parseInt(jsonEdge.get("value").toString());
                String sourceVertex = graph.getVertices().toArray()[sourceVertexIndex].toString();
                String targetVertex = graph.getVertices().toArray()[targetVertexIndex].toString();
                graph.addEdge(new MyLink(weight), sourceVertex, targetVertex);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return graph;
    }
}
