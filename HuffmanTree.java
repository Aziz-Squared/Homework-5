import java.io.PrintStream;
import java.util.PriorityQueue;
import java.util.Scanner;

import jdk.jfr.Frequency;

public class HuffmanTree {

	private HuffmanNode overallRoot;

	public HuffmanTree(int[] count) {
		PriorityQueue<HuffmanNode> frequencyCounter = new PriorityQueue<HuffmanNode>();

		for (int i = 0; i < count.length; i++) {
			if (count[i] > 0) {
				HuffmanNode freqRate = new HuffmanNode(count[i], i);
				frequencyCounter.add(freqRate);
			}
		}
		HuffmanNode eof = new HuffmanNode(1, count.length);
		frequencyCounter.add(eof);
		while (frequencyCounter.size() != 1) {
			HuffmanNode current = frequencyCounter.remove();
			HuffmanNode next = frequencyCounter.remove();
			// nodes representing a sum of counts with a character number of -1
			HuffmanNode sum = new HuffmanNode(current.frequency + next.frequency, -1, current, next);
			frequencyCounter.add(sum);
		}
		overallRoot = frequencyCounter.remove();
	}

	private class HuffmanNode implements Comparable<HuffmanNode> {
		public int frequency;
		public int ascii;
		public HuffmanNode left, right;

		public HuffmanNode(int frequency, int ascii) {
			this(frequency, ascii, null, null);
		}

		public HuffmanNode(int frequency, int ascii, HuffmanNode left, HuffmanNode right) {
			this.frequency = frequency;
			this.ascii = ascii;
			this.left = left;
			this.right = right;
		}

		public int compareTo(HuffmanNode h) {
			return this.frequency - h.frequency;
		}

	}

	private HuffmanNode HuffmanTreeBuilder(HuffmanNode root, int letterCode, String code) {
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

	public void decode(BitInputStream input, PrintStream output, int eofValue) {
		int charCode = input.readBit();
		while (charCode != -1) {
			charCode = HuffmanTreeTraverse(input, charCode, overallRoot, output, eofValue);
		}
	}

	private int HuffmanTreeTraverse(BitInputStream input, int code, HuffmanNode root, PrintStream output,
			int eofValue) {
		if (root.left == null && root.right == null && root.ascii != eofValue) {
			output.write(root.ascii);
			return code;
		} else if (code == 0 && root.ascii != eofValue) {
			return HuffmanTreeTraverse(input, input.readBit(), root.left, output, eofValue);
		} else if (root.ascii != eofValue) {
			return HuffmanTreeTraverse(input, input.readBit(), root.right, output, eofValue);
		}
		return -1;
	}

	public void write(PrintStream output) {
		if (overallRoot != null) {
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
