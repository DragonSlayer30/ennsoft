package app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class LunchLocations {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<LinkedList<Integer>> down_stream_graph = new ArrayList<LinkedList<Integer>>();
		ArrayList<LinkedList<Integer>> up_stream_graph = new ArrayList<LinkedList<Integer>>();
		HashMap<String, Integer> name_int_map = new HashMap<String, Integer>();
		ArrayList<String> int_name_map = new ArrayList<String>();

		boolean debug = true;
		Scanner input = new Scanner(System.in);
		input.nextLine();
		String line = input.nextLine();
		String condition = "Avoid";
		ArrayList<String> edge_line = new ArrayList<String>();
		String[] avoid = null;
		String[] peggy = null;
		String[] sam;
		while(!line.contains(condition)) {
			edge_line.add(line);
			line = input.nextLine();
		}
		line = input.nextLine();
		condition = "Peggy";
		while(!line.contains(condition)) {
			line = line.trim();
			if(line.length() == 0) break;
			avoid = line.split(" ");
			line = input.nextLine();
		}
		line = input.nextLine();
		condition = "Sam";
		while(!line.contains(condition)) {
			peggy = line.split(" ");
			line = input.nextLine();
		}
		line = input.nextLine();
		input.close();
		sam = line.split(" ");
		
		create_map(edge_line, down_stream_graph, up_stream_graph, name_int_map, int_name_map, debug);
		int total_node = name_int_map.size();
		
		boolean visited[] = new boolean[total_node];
		boolean avoid_arr[] = new boolean[total_node];
		if(avoid != null) {
			for (String s : avoid) {
				avoid_arr[name_int_map.get(s)] = true;
			}
		}
		
		for(String v : peggy) {
			dfs(name_int_map.get(v), visited, avoid_arr, down_stream_graph);
		}
		
		boolean[] visited_sam = new boolean[total_node];
		for(String v : sam) {
			dfs(name_int_map.get(v), visited_sam, avoid_arr, up_stream_graph);
		}
		
		ArrayList<String> meeting_points = new ArrayList<String>();
		for (int i = 0; i < total_node; i++) {
			if(visited_sam[i] && visited[i]) meeting_points.add(int_name_map.get(i));
		}
		meeting_points.sort(String::compareToIgnoreCase);
		for (String string : meeting_points) {
			System.out.println(string);
		}
	}
	
	/**
	 * @param v node to start dfs from
	 * @param visited visited array
	 * @param avoid_arr avoid nodes
	 * @param adj_matrix 
	 */
	public static void dfs(int v, boolean[] visited, boolean[] avoid_arr, ArrayList<LinkedList<Integer>> adj_matrix) {
        visited[v] = true; 
        Iterator<Integer> iter = adj_matrix.get(v).listIterator();
        while (iter.hasNext()) {
           int n = iter.next();
           if (!visited[n] && !avoid_arr[n]) {
               dfs(n, visited, avoid_arr, adj_matrix);
           }
       }
    }
	
	/**
	 * @param edges list of edges
	 * @param down down stream graph
	 * @param up up stream graph
	 * @param node_map node name to int map
	 * @param int_name_map int to node name map
	 * @param debug 
	 */
	public static void create_map(ArrayList<String> edges, ArrayList<LinkedList<Integer>> down, ArrayList<LinkedList<Integer>> up, HashMap<String, Integer> node_map, ArrayList<String> int_name_map, boolean debug) {
		int current_index = 0;
		for (String ed : edges) {
			String[] line_split = ed.split(" ");
			if(node_map.get(line_split[0]) == null) {
				node_map.put(line_split[0], current_index);
				LinkedList<Integer> list1 = new LinkedList<Integer>();
				LinkedList<Integer> list2 = new LinkedList<Integer>();
				down.add(list1);
				up.add(list2);
				int_name_map.add(line_split[0]);
				current_index++;
			}
			if(node_map.get(line_split[1]) == null) {
				node_map.put(line_split[1], current_index);
				LinkedList<Integer> list1 = new LinkedList<Integer>();
				LinkedList<Integer> list2 = new LinkedList<Integer>();
				int_name_map.add(line_split[1]);
				down.add(list1);
				up.add(list2);
				current_index++;
			}
			down.get(node_map.get(line_split[0])).add(node_map.get(line_split[1]));
			up.get(node_map.get(line_split[1])).add(node_map.get(line_split[0]));
		}
	} 

}
