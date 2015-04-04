package rspasov.folder.comparison;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class CompareApplication {

	public static void main(String... args) throws Exception {
		StopWatch watch = new StopWatch(StopWatch.S);
		CompareApplication compare = new CompareApplication(false);
		watch.start();
		String suffix = "_MyData";
		compare.diff("D:\\" + suffix, "F:\\" + suffix);
		compare.printResult();
		watch.stopAndPrint();
	}

	private final boolean useChecksum;

	private final Set<File> result = new TreeSet<>();

	public CompareApplication(boolean useChecksum) {
		super();
		this.useChecksum = useChecksum;
	}

	private String checksum(File file) {
		if (!useChecksum) {
			return "";
		}
		try {
			InputStream fin = new FileInputStream(file);
			java.security.MessageDigest md5er = MessageDigest.getInstance("MD5");
			byte[] buffer = new byte[1024];
			int bytesRead;
			do {
				bytesRead = fin.read(buffer);
				if (bytesRead > 0) {
					md5er.update(buffer, 0, bytesRead);
				}
			} while (bytesRead != -1);
			fin.close();
			byte[] digest = md5er.digest();
			if (digest != null) {
				String strDigest = "0x";
				for (byte currByte : digest) {
					strDigest += Integer.toString((currByte & 0xff) + 0x100, 16).substring(1).toUpperCase();
				}
				return strDigest;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	private void compare(File[] first, File[] second, Map<String, File> map) throws Exception {
		for (File file : first) {
			map.put(file.getName(), file);
		}
		compare(second, map);
	}

	private void compare(File[] files, Map<String, File> map) throws Exception {
		for (File file : files) {
			String fName = file.getName();
			if (map.containsKey(fName)) {
				File fComp = map.remove(fName);
				if (fComp.isDirectory()) {
					diff(file, fComp);
				} else {
					String cSum1 = checksum(file);
					String cSum2 = checksum(fComp);
					if (!cSum1.equals(cSum2)) {
						System.out.println(file.getName() + "\t\t\t\t" + "different");
					}
				}
			} else {
				result.add(file);
			}
		}
		Iterator<File> iter = map.values().iterator();
		while (iter.hasNext()) {
			File file = iter.next();
			result.add(file);
			iter.remove();
		}
	}

	public void diff(File firstDir, File secondDir) throws Exception {
		exists(firstDir);
		exists(secondDir);

		File[] fileList1 = firstDir.listFiles();
		File[] fileList2 = secondDir.listFiles();
		HashMap<String, File> map = new HashMap<String, File>();
		if (fileList1.length < fileList2.length) {
			compare(fileList1, fileList2, map);
		} else {
			compare(fileList2, fileList1, map);
		}
	}

	public void diff(String firstDir, String secondDir) throws Exception {
		diff(new File(firstDir), new File(secondDir));
	}

	private void exists(File file) throws RuntimeException {
		if (file == null) {
			throw new RuntimeException("File or directory cannot be null.");
		} else if (!file.exists()) {
			throw new RuntimeException("File or directory " + file.getAbsolutePath() + " does not exist.");
		}
	}

	private String onlyInMsg(File file) {
		return String.format("%-50s exists only in %s", file.getName(), file.getParent());
	}

	public void printResult() {
		for (File file : result) {
			System.out.println(onlyInMsg(file));
		}
	}

}
