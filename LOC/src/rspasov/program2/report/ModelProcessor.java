package rspasov.program2.report;

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
public class ModelProcessor {

	public static ModelReport process(List<Item> model) {
		ModelReport report = new ModelReport();
		process(report, model);
		return report;
	} // end

	private static void process(ModelReport report, List<Item> model) {
		for (Item item : model) {
			switch (item.getType()) {
			case FILE:
				report.addReportItems(ReportItemType.LINES, item.getLOC());
				report.addReportItems(ReportItemType.FILES, 1);
				process(report, item.getChildren());
				break;
			case CLASS:
				report.addReportItems(ReportItemType.CLASSES, 1);
				process(report, item.getChildren());
				break;
			case METHOD:
				report.addReportItems(ReportItemType.METHODS, 1);
				break;
			}
		}
	} // end

} // end
