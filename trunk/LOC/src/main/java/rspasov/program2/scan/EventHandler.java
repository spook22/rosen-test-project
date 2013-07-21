package rspasov.program2.scan;

import java.util.List;

import rspasov.program2.model.Item;

/**
 * Program Assignment: LOC Counting Program
 * <p>
 * Description: This program is used for counting source code lines, classes,
 * and methods. It's used by the PSP to track and estimate programs' sizes.
 * 
 * @author Rosen Spasov
 * @version 1.0
 */
public interface EventHandler {

	void startFile(String name);

	void startClass(String name);

	void startMethod(String name);

	void end();

	void processLine(String line);

	public List<Item> getModel();

	public int getTotalLOC();

} // end
