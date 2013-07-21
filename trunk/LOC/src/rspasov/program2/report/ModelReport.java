package rspasov.program2.report;

/**
 * Program Assignment: LOC Counting Program
 * <p>
 * Description: This program is used for counting source code lines, classes,
 * and methods. It's used by the PSP to track and estimate programs' sizes.
 * 
 * @author Rosen Spasov
 * @version 1.0
 */
public class ModelReport {

	private int totalLines;

	private int files;

	private int classes;

	private int methods;

	public int getTotalLines() {
		return totalLines;
	} // end

	public int getFiles() {
		return files;
	} // end

	public int getClasses() {
		return classes;
	} // end

	public int getMethods() {
		return methods;
	} // end

	public void addReportItems(ReportItemType type, int value) {
		switch (type) {
		case LINES:
			totalLines += value;
			break;
		case FILES:
			files += value;
			break;
		case CLASSES:
			classes += value;
			break;
		case METHODS:
			methods += value;
			break;
		default:
			throw new RuntimeException("Type not supported: " + type);
		}
	} // end

	@Override
	public String toString() {
		final String newLine = System.getProperty("line.separator", "\r\n");
		StringBuilder builder = new StringBuilder();
		builder.append("=====================================").append(newLine);
		builder.append("Total program lines: ").append(totalLines).append(newLine);
		builder.append("Classes: ").append(classes).append(newLine);
		builder.append("Methods: ").append(methods).append(newLine);
		builder.append("=====================================");
		return builder.toString();
	}

} // end
