package alok.naukari.trees;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class MainLowestCommonAncestor {

	public static void main(String[] args) {
		
//		long seed = System.currentTimeMillis();
		long seed = 1402215865040L;  /* 1402216091735L 1402215865040L 1402215931218L 1402216062370L */
		Random random = new Random(seed);
		_LOGGER.info("Seeding random number generator with " + seed);

		TreeGenerator generator = new TreeGenerator(random);

		TreeNode root = generator.generateBST(20);
		TreePrinter printer = new TreePrinter();
		printer.print(root);
//		Trees.inOrderTraversal(root);
		
		TreeNode first = Trees.getRandomNode(root, random, 0.7);
		TreeNode second = Trees.getRandomNode(root, random, 0.6);
		_LOGGER.info("first: " + first);
		_LOGGER.info("second: " + second);
		
		TreeNode commonAncestor = Trees.commonAncestor(root, first, second);
		_LOGGER.info(commonAncestor.toString());
	}
	
	private final static Logger _LOGGER = LoggerFactory.getLogger(MainLowestCommonAncestor.class);
}
