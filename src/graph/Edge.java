package graph;

public class Edge {
		public Edge(node i, node j) {
			if (i.id<j.id) {
			this.id1=i;
			this.id2=j;
		//	this.m=i2;
			}
			else
			{
				this.id1=j;
				this.id2=i;
			//	this.m=i2;
				}	
			this.id1.con.add(this.id2.id);
			this.id2.con.add(this.id1.id);
			this.id1.mas.add(this.m);
			this.id2.mas.add(this.m);
		}

		node id1,id2;
		Integer m;
	}
