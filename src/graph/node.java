package graph;

import java.util.ArrayList;

public class node {
	public node(int i, int j, int i2) {
		this.x=i;
		this.y=j;
		this.id=i2;
		this.con=new ArrayList<Integer>();
		this.mas=new ArrayList<Integer>();
	}
    public void add (int i,int m) {
    	con.add(i);
    	mas.add(m);
    }
	Integer x,y,id;
	ArrayList<Integer> con,mas;
}
