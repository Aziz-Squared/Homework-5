import java.io.PrintStream;
import java.util.Scanner;

public class HuffmanTree {
	
	private HuffmanNode overallRoot;
	
	
	public HuffmanTree(int[] count) {
		PriorityQueue <HuffmanNode> pQ = new PriorityQueue <HuffmanNode> ();
		
		for (int i = 1; i < count.length; i++) {
			if (count[i] > 0) {
				HuffmanNode node = null;
				node.frequency = count[i];
				node.ascii = i;
				pQ.offer(node);
			}
		}
		
		while (pQ.size() >= 2) {
			HuffmanNode node1 = pQ.poll();
			HuffmanNode node2 = pQ.poll();
			pQ.offer(new HuffmanNode(node1, node2));
		}
	}
	
	
	private class HuffmanNode implements Comparable <HuffmanNode> {
		public int frequency;
		public int ascii;
		public HuffmanNode left, right;
		
		public HuffmanNode(HuffmanNode n1, HuffmanNode n2) {
			this.left = n1;
			this.right = n2;
			this.frequency = n1.frequency + n2.frequency;
		}
		
		
		public int compareTo(HuffmanNode h) {
			if (this.frequency < h.frequency) {
				return -1;
			} else if (this.frequency == h.frequency) {
				return 0;
			} else {
				return 1;
			}
		}
	}


	public HuffmanTree(Scanner codeInput) {
	}


	public void decode(BitInputStream input, PrintStream output, int charMax) {
	}

	public void write(PrintStream output) {
	}

}
