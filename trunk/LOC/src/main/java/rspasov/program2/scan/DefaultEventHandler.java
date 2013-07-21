package rspasov.program2.scan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rspasov.program2.model.Item;
import rspasov.program2.model.ItemType;

/**
 * Program Assignment: LOC Counting Program
 * <p>
 * Description: This program is used for counting source code lines, classes,
 * and methods. It's used by the PSP to track and estimate programs' sizes.
 * 
 * @author Rosen Spasov
 * @version 1.0
 */
public class DefaultEventHandler implements EventHandler {

	private int totalLOC; // Lines of code

	private final List<Item> model = new ArrayList<Item>();

	protected Item currentItem;

	@Override
	public void startFile(String name) {
		Item item = new Item(name, ItemType.FILE);
		currentItem = item;
		model.add(item);
	} // end

	@Override
	public void startClass(String name) {
		Item item = new Item(name, ItemType.CLASS, currentItem);
		currentItem.addChild(item);
		currentItem = item;
	} // end

	@Override
	public void end() {
		if (currentItem != null) {
			int childLines = currentItem.getLOC();
			currentItem = currentItem.getParent();
			if (currentItem != null) {
				currentItem.addLines(childLines);
			}
		}
	} // end

	@Override
	public void startMethod(String name) {
		Item item = new Item(name, ItemType.METHOD, currentItem);
		currentItem.addChild(item);
		currentItem = item;
	} // end

	@Override
	public List<Item> getModel() {
		return Collections.unmodifiableList(model);
	} // end

	@Override
	public void processLine(String line) {
		line = line.trim();
		// Do not process empty and comment lines.
		if (!line.isEmpty() && !line.startsWith("//")) {
			totalLOC++;
			if (currentItem != null) {
				currentItem.addLine();
			}
		}
	} // end

	@Override
	public int getTotalLOC() {
		return totalLOC;
	} // end

} // end
