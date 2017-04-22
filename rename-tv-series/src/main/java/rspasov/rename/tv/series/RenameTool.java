package rspasov.rename.tv.series;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RenameTool extends SimpleFileVisitor<Path> {

	public static void main(String[] args) throws Exception {
		new RenameTool().renameSeries("");
	}

	public void renameSeries(String rootDir) throws Exception {
		Files.walkFileTree(Paths.get(rootDir), this);
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		super.visitFile(file, attrs);
		String fileName = file.getFileName().toString();

		Pattern p = Pattern.compile(".*(S\\d{1,2}?).*");
		Matcher m = p.matcher(fileName);
		boolean b = m.matches();
		return FileVisitResult.CONTINUE;
	}

}
