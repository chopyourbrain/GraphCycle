package graph;

import java.awt.BasicStroke;
import java.awt.Button;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.TextField;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class GraphPanel extends JComponent {
	ArrayList<Integer> rez = new ArrayList<Integer>();
	ArrayList<ArrayList<Integer>> cyc1 = new ArrayList<ArrayList<Integer>>();

	public GraphPanel() {

		b1.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				k = Integer.parseInt(t1.getText());
				mat = new int[k][k];
				for (int i = 0; i < k; i = i + 1)
					for (int j = 0; j < k; j = j + 1) {
						mat[i][j] = 0;
					}
				Integer j = 0;
				if (ar1 != null)
					ar1 = null;
				if (cycle != null)
					cycle = null;
				list.clear();
				edges.clear();
				for (int i = 0; i < k; i = i + 1) {
					list.add(new node((int) (xc + r * Math.cos(Math.toRadians(j))),
							(int) (yc + r * Math.sin(Math.toRadians(j))), i));
					j += 360 / k;
				}
				repaint();
			}
		});
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				edges.add(new Edge(list.get(Integer.parseInt(t2.getText())), list.get(Integer.parseInt(t3.getText()))));
				repaint();
			}

		});
		b3.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ar1 = new ArrayList<ArrayList<Integer>>();
				ar2 = new ArrayList<ArrayList<Integer>>();
				cyc1 = new ArrayList<ArrayList<Integer>>();

				ArrayList<Integer> mass3 = new ArrayList<Integer>();

				ar1.clear();
				mass3.add(1);
				findcenter(mass3, rez);

				ar1.clear();
				for (int i = 0; i < k; i++) {
					ArrayList<Integer> mass = new ArrayList<Integer>();
					int[][] matr = new int[k][k];
					for (int h = 0; h < k; h++)
						for (int y = 0; y < k; y++)
							matr[h][y] = mat[h][y];
					mass.add(i);
					find(i, i, mass);
				}
				for (int i = 0; i < ar1.size(); i++) {
					int counter = 0;
					for (int j = 0; j < rez.size(); j++) {
						if (ar1.get(i).contains(rez.get(j)))
							counter++;
					}
					if (counter <= 2) {
						cyc1.add(ar1.get(i));
					}
				}
				System.out.println("cycle=" + cyc1);
				repaint();
			}
		});
	}

	public void findway(Integer g, Integer n, ArrayList<Integer> mass1) {

		for (int i = 0; i < list.get(g).con.size(); i++) {
			ArrayList<Integer> mass = new ArrayList<Integer>();
			mass.addAll(mass1);
			int no = list.get(g).con.get(i);
			if (no == n) {
				mass.add(no);
				ar1.add(mass);
			}
			if (!mass.contains(no)) {
				mass.add(no);
				findway(no, n, mass);
			}
		}
	}

	public void findcenter(ArrayList<Integer> mass1, ArrayList<Integer> rez) {
		ArrayList<Integer> d1 = new ArrayList<Integer>();
		ArrayList<Integer> d2 = new ArrayList<Integer>();
		d2.clear();
		int max = 0, min = 0;
		for (int i = 0; i < k; i++) {

			for (int j = 0; j < k; j++) {
				if (i != j) {
					ArrayList<Integer> mass = new ArrayList<Integer>();
					ar1.clear();
					findway(i, j, mass);
					max = ar1.get(0).size();
					for (int l = 1; l < ar1.size(); l++)
						if (ar1.get(l).size() < max) {
							max = ar1.get(l).size();
						}

					d2.add(max);
				}
			}
			min = d2.get(0);
			for (int l = 1; l < d2.size(); l++) {
				if (d2.get(l) > min) {
					min = d2.get(l);
				}
			}
			d1.add(min);
			d2.clear();
		}
		min = d1.get(0);
		for (int l = 1; l < d1.size(); l++) {
			if (d1.get(l) < min) {
				min = d1.get(l);
			}
		}
		for (int l = 0; l < d1.size(); l++) {
			if (d1.get(l) == min) {
				rez.add(l);
			}
		}
	}

	public void find(Integer g, Integer n, ArrayList<Integer> mass1) {

		for (int i = 0; i < list.get(g).con.size(); i++) {
			ArrayList<Integer> mass = new ArrayList<Integer>();
			mass.addAll(mass1);
			int no = list.get(g).con.get(i);
			if ((no == n) & (mass.size() > 2)) {
				if (mass.get(mass.size() - 1) == no) {
					continue;
				}
				mass.add(no);
				mass.remove(mass.size() - 1);
				ArrayList<Integer> tmp = new ArrayList<Integer>();
				tmp.addAll(mass);
				Collections.sort(tmp);
				if (!ar2.contains(tmp)) {
					ar2.add(tmp);
					ar1.add(mass);
				}
				return;
			}
			if (!mass.contains(no)) {
				mass.add(no);
				find(no, n, mass);
			}
		}
	}

	ArrayList<ArrayList<Integer>> cycle = new ArrayList<ArrayList<Integer>>();
	ArrayList<node> list = new ArrayList<node>();
	ArrayList<Edge> edges = new ArrayList<Edge>();

	public void paint(Graphics g) {
		super.paint(g);
		((Graphics2D) g).setStroke(new BasicStroke(2.0f));
		for (int i = 0; i < edges.size(); i = i + 1) {
			g.drawLine(edges.get(i).id1.x + 10, edges.get(i).id1.y + 10, edges.get(i).id2.x + 10,
					edges.get(i).id2.y + 10);
		}
		((Graphics2D) g).setStroke(new BasicStroke(1.0f));
		for (int i = 0; i < list.size(); i = i + 1) {
			if (rez.contains(i)) {
				g.setColor(Color.RED);
			} else {
				g.setColor(new Color(255, 255, 255));
			}
			g.fillOval(list.get(i).x, list.get(i).y, 20, 20);
			g.setColor(new Color(0, 0, 0));
			g.drawOval(list.get(i).x, list.get(i).y, 20, 20);
			g.drawString("" + (i), list.get(i).x + 7, list.get(i).y + 15);
		}
	}

	ArrayList<ArrayList<Integer>> ar2;
	ArrayList<ArrayList<Integer>> ar1;
	static Integer xc = 140;
	static Integer yc = 230;
	static Integer r = 100;
	static JFrame f;
	static JLabel l1;
	static TextField t1;
	static Button b1;
	static JLabel l2;
	static JLabel l3;
	static JLabel l4;
	static TextField t2;
	static TextField t3;
	static Button b2;
	static Button b3;
	static Integer k = 1;
	private int[][] mat;

	public static void CreateGUI() {
		f = new JFrame();
		l1 = new JLabel("Число узлов:");
		t1 = new TextField("1", 1);
		b1 = new Button("Ок");
		l1.setBounds(10, 10, 80, 25);
		t1.setBounds(130, 10, 40, 25);
		b1.setBounds(230, 10, 70, 25);
		f.add(b1);
		f.add(t1);
		f.add(l1);
		l2 = new JLabel("Ребро:");
		l3 = new JLabel("Узел 1");
		l4 = new JLabel("Узел 2");
		t2 = new TextField("0", 1);
		t3 = new TextField("0", 1);
		b2 = new Button("Добавить");
		b3 = new Button("Найти цикл");
		l2.setBounds(48, 60, 50, 25);
		l3.setBounds(130, 45, 40, 15);
		l4.setBounds(180, 45, 40, 15);
		t2.setBounds(130, 60, 40, 25);
		t3.setBounds(180, 60, 40, 25);
		b2.setBounds(230, 60, 70, 25);
		b3.setBounds(10, 90, 140, 25);
		f.add(l2);
		f.add(l3);
		f.add(l4);
		f.add(t2);
		f.add(t3);
		f.add(b2);
		f.add(b3);
		f.setSize(320, 400);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(new GraphPanel());
		f.setVisible(true);
		f.setResizable(false);
	}

	public static void main(String[] args) {
		CreateGUI();

	}
}