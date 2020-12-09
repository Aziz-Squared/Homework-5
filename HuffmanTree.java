import java.io.PrintStream;
import java.util.PriorityQueue;
import java.util.Scanner;

public class HuffmanTree {
	
	private HuffmanNode overallRoot;
	
	
	public HuffmanTree(int[] count) {
		PriorityQueue <HuffmanNode> pQ = new PriorityQueue <HuffmanNode> ();
		
		for (int i = 1; i < count.length; i++) {
			if (count[i] > 0) {
				pQ.offer(new HuffmanNode(count[i], i);
			}
		}
		
		while (pQ.size() >= 2) {
			HuffmanNode node1 = pQ.poll();
			HuffmanNode node2 = pQ.poll();
			pQ.offer(new HuffmanNode(node1.frequency + node2.frequency, -1, node1, node2));
		}
	}
	
	
	private class HuffmanNode implements Comparable <HuffmanNode> {
		public int frequency;
		public int ascii;
		public HuffmanNode left, right;
		
		
		public HuffmanNode(int frequency, int ascii) {
			this(frequency, ascii, null, null);
		 }
		
		 public HuffmanNode(int frequency, int ascii, HuffmanNode left, HuffmanNode right){
			 this.frequency = frequency;
			 this.ascii = ascii;
			 this.left = left;
			 this.right = right;
		 }
		
		public int compareTo(HuffmanNode h) {
			return this.frequency - h.frequency;
		}

	}
	

	private HuffmanNode HuffmanTreeBuilder(HuffmanNode root, int letterCode, String code){
		if (root == null) {
		   root = new HuffmanNode(0, -1);
		}
		if (code.length() == 1) {
		   if (code.charAt(0) == '0') {
			  root.left = new HuffmanNode(0, letterCode);
		   } else {
			  root.right = new HuffmanNode(0, letterCode);
		   }
		} else {
		   char codeValue = code.charAt(0);
		  code = code.substring(1);
		   if (codeValue == '0') {
			  root.left = HuffmanTreeBuilder(root.left, letterCode, code);
		   } else {
			  root.right = HuffmanTreeBuilder(root.right, letterCode, code);      
		   }
		}
		return root;
	}


	public HuffmanTree(Scanner codeInput) {
		while (codeInput.hasNextLine()) {
			int letterCode = Integer.parseInt(codeInput.nextLine());
			String code = codeInput.nextLine();
			overallRoot = HuffmanTreeBuilder(overallRoot, letterCode, code);
		 }
	}


	public void decode(BitInputStream input, PrintStream output, int charMax) {
	}

	public void write(PrintStream output) {
		if(overallRoot != null){
			write(output, overallRoot, "");
		}
	}

	private void write(PrintStream output, HuffmanNode root, String code) {
		if (root.left == null && root.right == null) {
		   output.println(root.ascii);
		   output.println(code);
		} else {
		   write(output, root.left, code + "0");
		   write(output, root.right, code + "1"); 
		}
	 }

}
