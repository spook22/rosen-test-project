package rspasov.folder.comparison;

import java.util.ArrayList;
import java.util.List;

public class StopWatch {

	public static final String S = "s";
	public static final String MS = "ms";
	public static final String NS = "ns";

	private final String interval;

	private long start;

	private final List<Long> laps = new ArrayList<>();

	private long end;

	public StopWatch(String interval) {
		super();
		this.interval = interval;
	}

	private double divider() {
		int divider;
		switch (interval) {
		case "s":
			divider = 1000 * 1000 * 1000;
			break;
		case "ms":
			divider = 1000 * 1000;
			break;
		case "ns":
			divider = 1;
			break;
		default:
			throw new RuntimeException("Interval " + interval + " is not supported by the system.");
		}
		return divider;
	}

	public long endLap() {
		long endLap = nano();
		laps.add(endLap);
		return endLap - start;
	}

	public void endLapAndPrint() {
		System.out.println(msg(endLap()));
	}

	private String msg(long delta) {
		return String.format("It took %.2f%s to complete.", delta / divider(), interval);
	}

	private long nano() {
		return System.nanoTime();
	}

	public void start() {
		start = nano();
	}

	public long stop() {
		end = nano();
		return end - start;
	}

	public void stopAndPrint() {
		System.out.println(msg(stop()));
	}

}
