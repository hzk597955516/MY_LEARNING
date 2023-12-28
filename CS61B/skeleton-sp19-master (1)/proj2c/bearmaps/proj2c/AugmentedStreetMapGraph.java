package bearmaps.proj2c;

import bearmaps.hw4.streetmap.Node;
import bearmaps.hw4.streetmap.StreetMapGraph;
import bearmaps.proj2ab.KDTree;
import bearmaps.proj2ab.MyTrieSet;
import bearmaps.proj2ab.Point;
import edu.princeton.cs.algs4.Out;

import java.util.*;

/**
 * An augmented graph that is more powerful that a standard StreetMapGraph.
 * Specifically, it supports the following additional operations:
 *
 *
 * @author Alan Yao, Josh Hug, ________
 */
public class AugmentedStreetMapGraph extends StreetMapGraph {
    private KDTree kdtree;
    private Map<Point, Node> PointToNode;
    private List<Point> points;

    private MyTrieSet trieSet;
    private Map<String, List<Node>> cleanNameToNodes;

    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);
        // You might find it helpful to uncomment the line below:
        // List<Node> nodes = this.getNodes();
        List<Node> nodes = this.getNodes();
        points = new LinkedList<>();
        PointToNode = new HashMap<>();

        trieSet = new MyTrieSet();
        cleanNameToNodes = new HashMap<>();
        List<Node> nodelist;

        for (Node node: nodes){
            if (node.name() != null){
                String cleanName = cleanString(node.name());

                trieSet.add(cleanName);

                if (!cleanNameToNodes.containsKey(cleanName)){
                    cleanNameToNodes.put(cleanName, new LinkedList<>());
                }

                nodelist = cleanNameToNodes.get(cleanName);
                nodelist.add(node);
                cleanNameToNodes.put(cleanName, nodelist);

            }

            if (!neighbors(node.id()).isEmpty()){
                Point point = new Point(node.lon(), node.lat());
                PointToNode.put(point, node);
                points.add(point);
            }
        }

        kdtree = new KDTree(points);
    }


    /**
     * For Project Part II
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    public long closest(double lon, double lat) {
        Point point = kdtree.nearest(lon, lat);
        return PointToNode.get(point).id();
    }


    /**
     * For Project Part III (gold points)
     * In linear time, collect all the names of OSM locations that prefix-match the query string.
     * @param prefix Prefix string to be searched for. Could be any case, with our without
     *               punctuation.
     * @return A <code>List</code> of the full names of locations whose cleaned name matches the
     * cleaned <code>prefix</code>.
     */
    public List<String> getLocationsByPrefix(String prefix) {
        String seachPrefix = cleanString(prefix);
        List<String> Seach = trieSet.keysWithPrefix(seachPrefix);
        List<String> result = new LinkedList<>();

        for (String string: Seach){
            for (Node node: cleanNameToNodes.get(string)){
                result.add(node.name());
            }
        }
        return result;
    }

    /**
     * For Project Part III (gold points)
     * Collect all locations that match a cleaned <code>locationName</code>, and return
     * information about each node that matches.
     * @param locationName A full name of a location searched for.
     * @return A list of locations whose cleaned name matches the
     * cleaned <code>locationName</code>, and each location is a map of parameters for the Json
     * response as specified: <br>
     * "lat" -> Number, The latitude of the node. <br>
     * "lon" -> Number, The longitude of the node. <br>
     * "name" -> String, The actual name of the node. <br>
     * "id" -> Number, The id of the node. <br>
     */
    public List<Map<String, Object>> getLocations(String locationName) {
        String cleanedName = cleanString(locationName);
        List<Map<String, Object>> locations = new LinkedList<>();

        if (cleanNameToNodes.containsKey(cleanedName)){
            for (Node node: cleanNameToNodes.get(cleanedName)){
                Map<String, Object> location = new HashMap<>();
                location.put("id", node.id());
                location.put("name", node.name());
                location.put("lon", node.lon());
                location.put("lat", node.lat());
                locations.add(location);
            }
        }
        return locations;
    }


    /**
     * Useful for Part III. Do not modify.
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    private static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

}
