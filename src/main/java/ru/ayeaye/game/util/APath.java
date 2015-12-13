package ru.ayeaye.game.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ru.ayeaye.game.model.FieldCell;

public class APath {
	// A* path finding
	public static List<FieldCell> findPath(FieldCell start, FieldCell goal) {
	    Set<FieldCell> closedset = new HashSet<FieldCell>(); // The set of nodes already evaluated.
	    
	    Set<FieldCell> openset = new HashSet<FieldCell>(); // The set of tentative nodes to be evaluated, initially containing the start node
	    openset.add(start);

	    Map<FieldCell, FieldCell> came_from = new HashMap<FieldCell, FieldCell>();    // The map of navigated nodes.
	    
	    Map<FieldCell, Float> g_score = new HashMap<FieldCell, Float>();
	    g_score.put(start, 0f);    // Cost from start along best known path.
	    // Estimated total cost from start to goal through y.
	    Map<FieldCell, Float> f_score = new HashMap<FieldCell, Float>();
	    f_score.put(start, g_score.get(start) + heuristic_cost_estimate(start, goal));
	 
	    while (!openset.isEmpty()) {
	        FieldCell current = nodeWithLowestScore(openset, f_score);
	        if (current == goal) {
	            return reconstruct_path(came_from, goal);
	        }
	 
	        openset.remove(current);
	        closedset.add(current);
	        for (FieldCell neighbor: neighbor_nodes(current)) {
	            if (neighbor == null || closedset.contains(neighbor)) {
	                continue;
	            }
	            
	            float tentative_g_score = g_score.get(current) + dist_between(current, neighbor);
	 
	            if (!openset.contains(neighbor) || tentative_g_score < g_score.get(neighbor)) { 
	                came_from.put(neighbor, current);
	                g_score.put(neighbor, tentative_g_score);
	                f_score.put(neighbor, g_score.get(neighbor) + heuristic_cost_estimate(neighbor, goal));
	                if (!openset.contains(neighbor)) {
	                    openset.add(neighbor);
	                }
	            }
	        }
	    }
	    
	    throw new RuntimeException("fail");
	}
	
	private static FieldCell nodeWithLowestScore(Set<FieldCell> openset, Map<FieldCell, Float> f_score) {
		
		FieldCell lowest = null;
		
		for (FieldCell cell: openset) {
			if (lowest == null) {
				lowest = cell;
			} else {
				if (f_score.get(cell) < f_score.get(lowest)) {
					lowest = cell;
				}
			}
		}
		
		return lowest;
	}
	
	private static List<FieldCell> neighbor_nodes(FieldCell cell) {
		List<FieldCell> neighbors = new ArrayList<FieldCell>(9);
		neighbors.add(cell.getUpCell());
		neighbors.add(cell.getRightUpCell());
		neighbors.add(cell.getRightCell());
		neighbors.add(cell.getRightDownCell());
		neighbors.add(cell.getDownCell());
		neighbors.add(cell.getLeftDownCell());
		neighbors.add(cell.getLeftCell());
		neighbors.add(cell.getLeftUpCell());
		return neighbors;
	}
	
	private static float heuristic_cost_estimate(FieldCell start, FieldCell goal) {
		float y = start.getI() - goal.getI();
		float x = start.getJ() - goal.getJ();
		
		return (float) Math.sqrt(x*x + y*y);
	}
	
	private static float dist_between(FieldCell start, FieldCell goal) {
		float y = start.getI() - goal.getI();
		float x = start.getJ() - goal.getJ();
		
		return (float) Math.sqrt(x*x + y*y);
	}
	
	private static List<FieldCell> reconstruct_path(Map<FieldCell, FieldCell> came_from, FieldCell current) {
		LinkedList<FieldCell> total_path = new LinkedList<FieldCell>();
	    total_path.add(current);
	    
	    while (came_from.get(current) != null) {
	        current = came_from.get(current);
	        total_path.addFirst(current);
	    }
	    
	    return total_path;
	}
	
}
