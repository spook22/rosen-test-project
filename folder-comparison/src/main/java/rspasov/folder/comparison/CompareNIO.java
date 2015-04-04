package rspasov.folder.comparison;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class CompareNIO extends CompareApplication {

	public static void main(String... args) throws Exception {
		StopWatch watch = new StopWatch(StopWatch.S);
		String suffix = "_MyData";
		CompareNIO compare = new CompareNIO(false, "D:\\" + suffix, "F:\\" + suffix);
		watch.start();
		compare.diff();
		compare.printResult();
		watch.stopAndPrint();
	}

	private final String sourcePrefix;

	private final String destPrefix;

	private final Path source;

	public CompareNIO(boolean useChecksum, String sourcePrefix, String destPrefix) {
		super(useChecksum);
		this.sourcePrefix = sourcePrefix;
		this.destPrefix = destPrefix;
		source = FileSystems.getDefault().getPath(sourcePrefix);
	}

	private void compare(Path path) {
		String absPath = path.toFile().getAbsolutePath();
		String relPath = absPath.substring(sourcePrefix.length());
		File destination = new File(destPrefix, relPath);
		if (!destination.exists()) {
			result.add(destination);
		}
	}

	public void diff() throws Exception {
		System.out.println("Traversing " + source);
		Files.walk(source).forEach(p -> compare(p));
	}

}
