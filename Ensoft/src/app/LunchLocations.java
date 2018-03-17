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
			//if(debug) System.out.println(line);
			line = input.nextLine();
		}
		line = input.nextLine();
		condition = "Peggy";
		while(!line.contains(condition)) {
			//edge_line.add(line);
			//if(debug) System.out.println("Avoid" + " " + line);
			line = line.trim();
			if(line.length() == 0) break;
			avoid = line.split(" ");
			System.out.println(avoid[0]);
			line = input.nextLine();
		}
		line = input.nextLine();
		condition = "Sam";
		while(!line.contains(condition)) {
			//if(debug) System.out.println("Peggy" + " " + line);
			peggy = line.split(" ");
			line = input.nextLine();
		}
		line = input.nextLine();
		input.close();
		//if(debug) System.out.println("Sam " + line);
		sam = line.split(" ");
		
		create_map(edge_line, down_stream_graph, up_stream_graph, name_int_map, int_name_map, debug);
		int total_node = name_int_map.size();
		/*
		for (int i = 0; i < total_node; i++) {
			if(debug) System.out.print(int_name_map.get(i) +  " : ");
			for(Integer v : down_stream.get(i)) {
				if(debug) System.out.print(" " + int_name_map.get(v) + " ");
			}
			if(debug) System.out.println(""); 
		}
		for (int i = 0; i < total_node; i++) {
			if(debug) System.out.print(int_name_map.get(i) +  " : ");
			for(Integer v : up_stream.get(i)) {
				if(debug) System.out.print(" " + int_name_map.get(v) + " ");
			}
			if(debug) System.out.println(""); 
		}
		*/
		boolean visited[] = new boolean[total_node];
		boolean avoid_arr[] = new boolean[total_node];
		if(avoid != null) {
			for (String s : avoid) {
				//if(debug) System.out.println(s + " " + node_map.get(s));
				avoid_arr[name_int_map.get(s)] = true;
			}
		}
		
		for(String v : peggy) {
			//if(debug) System.out.println(v);
			dfs(name_int_map.get(v), visited, avoid_arr, down_stream_graph);
		}
		/*
		for (int i = 0; i < total_node; i++) {
			if(visited[i]) System.out.print(int_name_map.get(i) + " ");
		}
		System.out.println("");
		*/
		boolean[] visited_sam = new boolean[total_node];
		for(String v : sam) {
			//if(debug) System.out.println(v);
			dfs(name_int_map.get(v), visited_sam, avoid_arr, up_stream_graph);
		}
		/*
		for (int i = 0; i < total_node; i++) {
			if(visited_sam[i]) System.out.print(int_name_map.get(i) + " ");
		}
		System.out.println("");
		*/
		ArrayList<String> meeting_points = new ArrayList<String>();
		for (int i = 0; i < total_node; i++) {
			if(visited_sam[i] && visited[i]) meeting_points.add(int_name_map.get(i));
		}
		meeting_points.sort(String::compareToIgnoreCase);
		for (String string : meeting_points) {
			System.out.println(string);
		}
	}
	
	public static void dfs(int v, boolean[] visited, boolean[] avoid_arr, ArrayList<LinkedList<Integer>> adj_matrix) {
        visited[v] = true; 
        Iterator<Integer> iter = adj_matrix.get(v).listIterator();
        while (iter.hasNext()) {
           int n = iter.next();
           if (!visited[n] && !avoid_arr[n]) {
               //System.out.println("traversal " + n);
               dfs(n, visited, avoid_arr, adj_matrix);
           }
       }
    }
	
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
			else {
				//if(debug) System.out.println(line_split[0] + " " + node_map.get(line_split[0]));
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
			else {
				//if(debug) System.out.println(line_split[1] + " " + node_map.get(line_split[1]));
			}
			//if(debug) System.out.println("Adding : " + int_name_map.get(node_map.get(line_split[0])) + " " + int_name_map.get(node_map.get(line_split[1])));
			//if(debug) System.out.println("Adding : " + int_name_map.get(node_map.get(line_split[1])) + " " + int_name_map.get(node_map.get(line_split[0])));
			//if(debug) System.out.println("Edge size : " + " " + line_split[0] + " "  + node_map.get(line_split[0]) + " "  + down.get(node_map.get(line_split[0])).size());
			down.get(node_map.get(line_split[0])).add(node_map.get(line_split[1]));
			//if(debug) System.out.println(line_split[0] +  " " + node_map.get(line_split[0]));
			/*
			for(Integer v : down.get(node_map.get(line_split[0]))) {
				if(debug) System.out.print(v + " " + int_name_map.get(v) + " ");
			}
			if(debug) System.out.println("");
			*/
			up.get(node_map.get(line_split[1])).add(node_map.get(line_split[0]));
		}
		//if(debug) System.out.println("Upstream size : " + down.size());
		//if(debug) System.out.println("downstream size : " + up.size());
		/*
		for (String vertex : node_map.keySet()) {
			//if(debug) System.out.println(vertex + " :  " + node_map.get(vertex));
		}
		*/
	} 

}
