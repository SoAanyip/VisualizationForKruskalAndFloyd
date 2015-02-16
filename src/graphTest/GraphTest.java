package graphTest;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class GraphTest {
	 
	private static final int MAX = Integer.MAX_VALUE;
	
	Scanner sc = new Scanner(System.in);
	public static void main(String[] args) {           //无向图
		new GraphTest().launch();
	}
	
	public void launch(){
		System.out.println("请输入这个图共有多少个点？");
		int count = sc.nextInt();
		GraphNode[] nodes = new GraphNode[count];
		int[][] relations = new int[count][count];
		for(int i = 0;i<count;i++){
			for(int j = 0;j<count;j++){
				relations[i][j] = 0;
			}
		}
		for(int i = 0;i<count;i++){
			nodes[i] = new GraphNode(i+1);
		}
		System.out.println("你的图分别有1-" + count + "这几个点。请描述点之间的关系。如点1和点2有边的话输入1-2。用回车进行多条边的输入。输入end结束。");
		ArrayList<String> getRelation = new ArrayList<String>();
		String get = "";
		String splitGet[];
		while(true){
			get = sc.next();
			if(get.equalsIgnoreCase("end")){
				break;
			}
			splitGet = get.split("-");
			if(splitGet.length>1){
				int i = Integer.parseInt(splitGet[0]);
				int j = Integer.parseInt(splitGet[1]);
				relations[i-1][j-1] = 1;
				relations[j-1][i-1] = 1;
			}
			getRelation.add(get);
			if(getRelation.size()==(count*(count-1)/2)){
				break;
			}
		}
		System.out.println("邻接矩阵如下：");
		System.out.print("\\   ");
		for(int i = 0;i<count;i++){
			System.out.print("p"+(i+1)+ "  " );
		}
		System.out.println("");
		for(int i = 0;i<count;i++){
			System.out.print("p"+(i+1) + "  ");
			for(int j = 0;j<count;j++){
				System.out.print(relations[i][j] + "   ");
			}
			System.out.println("\r\n");
		}
			
		for(int i = 0;i<count;i++){
			int countNeighbor = 0;
			nodes[i].neighbors = new GraphNode[count];
			for(int j = 0;j<count;j++){
				if(relations[i][j]==1){
					nodes[i].neighbors[countNeighbor] = nodes[j];
					countNeighbor++;
				}
			}
			countNeighbor = 0;
		}
		/*for(int i = 0;i<count;i++){
			for(int j = 0;j<count;j++){
				System.out.println(nodess[i].neighbors[j]);
			}
			System.out.println("");
		}*/
		
		/*GraphNode[] nodes = new GraphNode[5];
		GraphNode n1 = new GraphNode(1); 
		GraphNode n2 = new GraphNode(2); 
		GraphNode n3 = new GraphNode(3); 
		GraphNode n4 = new GraphNode(4); 
		GraphNode n5 = new GraphNode(5); 
		
 
		n1.neighbors = new GraphNode[]{n2,n3,n5};
		n2.neighbors = new GraphNode[]{n1,n4};
		n3.neighbors = new GraphNode[]{n1,n5};
		n4.neighbors = new GraphNode[]{n2};
		n5.neighbors = new GraphNode[]{n1,n3};
		
		
		nodes[0] = n1;
		nodes[1] = n2;
		nodes[2] = n3;
		nodes[3] = n4;
		nodes[4] = n5;*/
		
		/*int[][] relations = new int[nodes.length][nodes.length];
		for(int i = 0;i<nodes.length;i++){
			for(int j = 0;j<nodes.length;j++){
				relations[i][j] = 0;
			}
		}
		relations[0][1] = 1;
		relations[0][2] = 1;*/
		System.out.println("\r\n\r\n从1-" + count + "哪一点开始进行遍历？");
		int beginNum = sc.nextInt();
		if(beginNum<=count && beginNum>0){
			System.out.println("广度优先遍历：");
			widFirstSearch(nodes[beginNum-1]);
			System.out.println("\r\n");
			for(int i = 0;i<count;i++){
				if(nodes[i].visited == false){
					System.out.println("第" + (i+1) + "点并没有被遍历到！请注意是非连通图");
				}
				nodes[i].visited = false;
			}
			
			System.out.println("深度优先遍历：");
			Stack<GraphNode> stack = new Stack<GraphNode>();
			deptFisrtSearch(nodes[beginNum-1],stack);
		}else{
			System.out.println("输入有误！");
		}
		
		
		System.out.println("\r\n\r\n要设置权值？Y/N");
		if(sc.next().equalsIgnoreCase("Y")){
			giveNum(count,relations);
		}
		new DrawPolygon(count,relations).launch();
		System.out.println("\r\n\r\n要应用Kruskal算法？Y/N");
		if(sc.next().equalsIgnoreCase("Y")){
			kruskal(count, relations,nodes);
		}
		
		System.out.println("\r\n\r\n要应用Floyd算法？Y/N");
		if(sc.next().equalsIgnoreCase("Y")){
			floyd(count, relations);
		}
		
		sc.close();
	}
 
	public void widFirstSearch(GraphNode root){
	
		Queue<GraphNode> queue = new LinkedList<GraphNode>();
		root.visited = true;
		queue.offer(root);
		System.out.print("  入队：" + root + "  ");
 
		while(queue.peek() != null){
			GraphNode c = queue.poll();
			System.out.print("  出队：" + c + " ");
			for(GraphNode n: c.neighbors){
				if(n!=null && (!n.visited)){
					n.visited = true;
					queue.offer(n);
					System.out.print("  入队：" + n + "  ");
				}
			}
		}
	}
	
	public void deptFisrtSearch(GraphNode root,Stack<GraphNode> stack){
		
		root.visited = true;
		stack.push(root);
		System.out.print("  入栈：" + root + "  ");
		
		if(!stack.empty()){
			GraphNode c = stack.peek();
			//System.out.print(c + "  ");
			for(GraphNode n : c.neighbors){
				if(n!=null && !n.visited)
					deptFisrtSearch(n,stack);
			}
			System.out.print("   出栈:" + stack.pop() + "  ");
		}
	}
	
	public void giveNum(int count,int[][] relations){
		for(int i = 0;i<count;i++){
			for(int j=i;j<count;j++){
				if(relations[i][j]==1){
					System.out.println("请为边" + (i+1) + "-" + (j+1) + "设置权值");
					relations[i][j] = sc.nextInt();
					relations[j][i] = relations[i][j];
				}
			}
		}
		System.out.println("邻接矩阵如下：");
		System.out.print("\\   ");
		for(int i = 0;i<count;i++){
			System.out.print("p"+(i+1)+ "  " );
		}
		System.out.println("");
		for(int i = 0;i<count;i++){
			System.out.print("p"+(i+1) + "  ");
			for(int j = 0;j<count;j++){
				System.out.print(relations[i][j] + "   ");
			}
			System.out.println("\r\n");
		}
		int result = 0;
		for(int i = 0;i<count;i++){
			for(int j = 0;j<count;j++){
				if(relations[i][j] != 0){
					result+=relations[i][j];
				}
			}
		}
		System.out.println("总权值：" + result/2 + "\r\n");
	}
	
	public void kruskal(int count,int relations[][],GraphNode[] nodes){
		int[][] useRelation = new int[count][count];
		for(int i = 0;i<count;i++){
			for(int j = 0;j<count;j++){
				useRelation[i][j] = relations[i][j];
			}
		}
		int[][] paintKruskal = new int [count][count];
		for(int i = 0;i<count;i++){
			for(int j = 0;j<count;j++){
				paintKruskal[i][j] = relations[i][j];
			}
		}
		
		//int[] countPoint = new int[count];
		int save =MAX;
		int[] saveLine = new int[2];
		int sum = 0;
		for(int i=0;i<count;i++){
			//countPoint[i] = 0;
			nodes[i].kruskalGroup = 0;
		}
		int group = 1;
		while(true){
			for(int i = 0;i<count;i++){
				for(int j = i;j<count;j++){
					if(useRelation[i][j]<save && useRelation[i][j]!=0){
						save = useRelation[i][j];
						saveLine[0] = i;
						saveLine[1] = j;
					}
				}
			}
			if(save==MAX)
				break;
			useRelation[saveLine[0]][saveLine[1]] = 0;
			useRelation[saveLine[1]][saveLine[0]] = 0;
			if( nodes[saveLine[0]].kruskalGroup != 0 && nodes[saveLine[1]].kruskalGroup == nodes[saveLine[0]].kruskalGroup){
				System.out.println("去除成圈边" + (saveLine[0]+1) + "-" + (saveLine[1]+1));
				paintKruskal[saveLine[0]][saveLine[1]] = 0;
				paintKruskal[saveLine[1]][saveLine[0]] = 0;
			}else{
				sum+=save;
				//countPoint[saveLine[0]]++;
				//countPoint[saveLine[1]]++;
				if(nodes[saveLine[0]].kruskalGroup!=0 && nodes[saveLine[1]].kruskalGroup != 0){
					int temp = nodes[saveLine[0]].kruskalGroup;
					for(int i = 0;i<count;i++){
						if(nodes[i].kruskalGroup==temp){
							nodes[i].kruskalGroup=nodes[saveLine[1]].kruskalGroup;
						}
					}
				}else if(nodes[saveLine[0]].kruskalGroup==0 && nodes[saveLine[1]].kruskalGroup == 0){
					nodes[saveLine[0]].kruskalGroup = group;
					nodes[saveLine[1]].kruskalGroup = group;
					group++;
				}else if(nodes[saveLine[0]].kruskalGroup==0 && nodes[saveLine[1]].kruskalGroup != 0){
					nodes[saveLine[0]].kruskalGroup = nodes[saveLine[1]].kruskalGroup;
				}else if(nodes[saveLine[0]].kruskalGroup!=0 && nodes[saveLine[1]].kruskalGroup == 0){
					nodes[saveLine[1]].kruskalGroup = nodes[saveLine[0]].kruskalGroup;
				}
			}
			save=MAX;
		}
		System.out.println("据Kruskal算法，最小生成树权值为：" + sum);
		new DrawPolygon(count, paintKruskal).launch();
	}
	
	public void floyd(int count,int[][] relations){
		int[][] useRelation = new int[count][count];
		for(int i = 0;i<count;i++){
			for(int j = 0;j<count;j++){
				if(i!=j && relations[i][j]==0 )
					useRelation[i][j] = MAX;
				else
					useRelation[i][j] = relations[i][j];
			}
		}
		
		int[][] path = new int[count][count];
		for(int i = 0;i<count;i++){
			for(int j = 0;j<count;j++){
				path[i][j] = i;    //储存i->j经过的最后一个节点
			}
		}
		
		for(int k = 0;k<count;k++){
			for(int i = 0;i<count;i++){
				for(int j = 0;j<count;j++){
					if(useRelation[i][j]> maxSum(useRelation[i][k],useRelation[k][j])){
						path[i][j] = path[k][j];
						useRelation[i][j] = maxSum(useRelation[i][k],useRelation[k][j]);
					}
				}
			}
		}
		
		System.out.println("邻接矩阵如下：");
		System.out.print("\\   ");
		for(int i = 0;i<count;i++){
			System.out.print("p"+(i+1)+ "  " );
		}
		System.out.println("");
		for(int i = 0;i<count;i++){
			System.out.print("p"+(i+1) + "  ");
			for(int j = 0;j<count;j++){
				System.out.print(useRelation[i][j] + "   ");
			}
			System.out.println("\r\n");
		}
		new DrawPolygon(count, useRelation).launch();
	}
	
	public int maxSum(int a,int b){
		return (a!=MAX && b!=MAX)?(a+b):MAX;
	}
	
}




