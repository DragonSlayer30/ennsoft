package app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class LunchLocations {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<LinkedList<Integer>> down_stream = new ArrayList<LinkedList<Integer>>();
		ArrayList<LinkedList<Integer>> up_stream = new ArrayList<LinkedList<Integer>>();
		HashMap<String, Integer> node_map = new HashMap<String, Integer>();
		ArrayList<String> int_name_map = new ArrayList<String>();
		int current_index = 0;
		boolean debug = true;
		Scanner input = new Scanner(System.in);
		input.nextLine();
		String line = input.nextLine();
		String condition = "Avoid";
		ArrayList<String> edge_line = new ArrayList<String>();
		String[] avoid;
		String[] peggy;
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
			avoid = line.split(" ");
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
		//if(debug) System.out.println("Sam " + line);
		sam = line.split(" ");
		create_map(edge_line, down_stream, up_stream, node_map, int_name_map, debug);
		/*
		int total_node = node_map.size();
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
			if(debug) System.out.println("Adding : " + int_name_map.get(node_map.get(line_split[0])) + " " + int_name_map.get(node_map.get(line_split[1])));
			//if(debug) System.out.println("Adding : " + int_name_map.get(node_map.get(line_split[1])) + " " + int_name_map.get(node_map.get(line_split[0])));
			if(debug) System.out.println("Edge size : " + " " + line_split[0] + " "  + node_map.get(line_split[0]) + " "  + down.get(node_map.get(line_split[0])).size());
			down.get(node_map.get(line_split[0])).add(node_map.get(line_split[1]));
			if(debug) System.out.println(line_split[0] +  " " + node_map.get(line_split[0]));
			for(Integer v : down.get(node_map.get(line_split[0]))) {
				if(debug) System.out.print(v + " " + int_name_map.get(v) + " ");
			}
			if(debug) System.out.println("");
			up.get(node_map.get(line_split[1])).add(node_map.get(line_split[0]));
		}
		if(debug) System.out.println("Upstream size : " + down.size());
		if(debug) System.out.println("downstream size : " + up.size());
		for (String vertex : node_map.keySet()) {
			//if(debug) System.out.println(vertex + " :  " + node_map.get(vertex));
		}
	} 

}
