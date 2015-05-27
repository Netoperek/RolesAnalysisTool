package pl.edu.agh.toik;

import edu.uci.ics.jung.algorithms.scoring.BetweennessCentrality;
import edu.uci.ics.jung.algorithms.scoring.PageRank;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseMultigraph;
import org.apache.commons.collections15.Transformer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by pkala on 5/26/15.
 */
public class GraphUtils {
    private static final int ALPHA = 10;

    static class ValueComparator implements Comparator<String> {

        Map<String, Double> base;
        public ValueComparator(Map<String, Double> base) {
            this.base = base;
        }

        public int compare(String a, String b) {
            if (base.get(a) >= base.get(b)) {
                return -1;
            } else {
                return 1;
            }
        }
    }

    private static Transformer<MyLink, Double> wtTransformer = new Transformer<MyLink,Double>() {
        public Double transform(MyLink link) {
            return link.getWeight();
        }
    };

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
                graph.addEdge(new MyLink(weight, sourceVertex, targetVertex), sourceVertex, targetVertex);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return graph;
    }

    public static TreeMap<String, Double> verticesBetweenness(Graph<String, MyLink> graph) {
        BetweennessCentrality<String, MyLink> bcb = new BetweennessCentrality<String, MyLink>(graph, wtTransformer);
        HashMap<String, Double> result= new HashMap<String, Double>();

        for(int i = 0; i < graph.getVertices().size(); i++) {
            String vertex = graph.getVertices().toArray()[i].toString();
            result.put(vertex, bcb.getVertexScore(vertex));
        }
        ValueComparator bvc =  new ValueComparator(result);
        TreeMap<String,Double> sortedResult = new TreeMap<String,Double>(bvc);
        sortedResult.putAll(result);
        return sortedResult;
    }

    public static TreeMap<String, Double> verticesPageRank(Graph<String, MyLink> graph) {
        PageRank<String, MyLink> pageRank = new PageRank(graph, wtTransformer, ALPHA);
        HashMap<String, Double> result= new HashMap<String, Double>();
        for(int i = 0; i < graph.getVertices().size(); i++) {
            String vertex = graph.getVertices().toArray()[i].toString();
            result.put(vertex, pageRank.getVertexScore(vertex));
        }
        ValueComparator bvc =  new ValueComparator(result);
        TreeMap<String,Double> sortedResult = new TreeMap<String,Double>(bvc);
        sortedResult.putAll(result);
        return sortedResult;
    }

    public static HashMap<String, Role> markRoles(Graph<String, MyLink> graph) {
        TreeMap<String, Double> betweenness = GraphUtils.verticesBetweenness(graph);
        TreeMap<String, Double> pageRanks = GraphUtils.verticesPageRank(graph);
        int limit = graph.getVertexCount() / 5;
        HashMap<String, Role> result = new HashMap<String, Role>();
        HashMap<String, Role> mediators = new HashMap<String, Role>();
        HashMap<String, Role> standards = new HashMap<String, Role>();
        HashMap<String, Role> influentials = new HashMap<String, Role>();

        int counter = 0;
        for(Map.Entry<String,Double> entry : betweenness.entrySet()) {
            if(counter >= limit) break;
            counter++;
            String key = entry.getKey();
            mediators.put(key, Role.MEDIATOR);
        }

        counter = 0;
        for(Map.Entry<String,Double> entry : pageRanks.entrySet()) {
            if(counter >= limit) break;
            counter++;
            String key = entry.getKey();
            if(!betweenness.containsKey(key)) {
                influentials.put(key, Role.INFLUENTIAL);
            }
        }

        for(Map.Entry<String,Double> entry : pageRanks.entrySet()) {
            String key = entry.getKey();
            if(!betweenness.containsKey(key) && !pageRanks.containsKey(key) ) {
                standards.put(key, Role.STANDARD);
            }
        }

        result.putAll(standards);
        result.putAll(influentials);
        result.putAll(mediators);
        return result;
    }
}
