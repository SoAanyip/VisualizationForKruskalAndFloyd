package graphTest;

class GraphNode{ 
	int val;
	GraphNode next;
	GraphNode[] neighbors;
	boolean visited;
	int kruskalGroup;
 
	GraphNode(int x) {
		val = x;
		kruskalGroup = 0;
	}
 
	GraphNode(int x, GraphNode[] n){
		val = x;
		neighbors = n;
		kruskalGroup = 0;
	}
 
	public String toString(){
		return "µã "+ this.val; 
	}
}