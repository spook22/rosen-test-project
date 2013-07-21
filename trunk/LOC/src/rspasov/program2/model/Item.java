package rspasov.program2.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Program Assignment: LOC Counting Program
 * <p>
 * Description: This program is used for counting source code lines, classes,
 * and methods. It's used by the PSP to track and estimate programs' sizes.
 * 
 * @author Rosen Spasov
 * @version 1.0
 */
public class Item {

	private final String name;

	private final ItemType type;

	private int loc;

	private final List<Item> children = new ArrayList<Item>();

	private Item parent;

	public Item(String name, ItemType type) {
		this.name = name;
		this.type = type;
	} // end

	public Item(String name, ItemType type, Item parent) {
		this.name = name;
		this.type = type;
		this.parent = parent;
	} // end

	public Item getParent() {
		return parent;
	} // end

	public String getName() {
		return name;
	} // end

	public ItemType getType() {
		return type;
	} // end

	public int getLOC() {
		return loc;
	} // end

	public List<Item> getChildren() {
		return Collections.unmodifiableList(children);
	} // end

	public void addChild(Item child) {
		children.add(child);
	} // end

	public void addLine() {
		loc++;
	} // end

	public void addLines(int lines) {
		loc += lines;
	} // end

	@Override
	public String toString() {
		final String newLine = System.getProperty("line.separator", "\r\n");
		StringBuilder builder = new StringBuilder(type + " " + name + " (lines: " + loc + ")");
		if (children.size() > 0) {
			for (Item child : children) {
				builder.append(newLine).append("\t" + child);
			}
		}
		if (type == ItemType.FILE) {
			builder.append(newLine);
		}
		return builder.toString();
	} // end
} // end
