package common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class InputFiles {
	private static final String EXAMPLE_FILE = "example.txt";
	private static final String INPUT_FILE = "input.txt";
	private static final List<Path> SOURCE_ROOTS = List.of(
			Paths.get("2015", "java"),
			Paths.get("2025", "java")
	);

	private InputFiles() {
	}

	public static String readString(Class<?> owner) throws IOException {
		return readString(owner, true);
	}

	public static String readString(Class<?> owner, boolean example) throws IOException {
		return readString(owner, example ? EXAMPLE_FILE : INPUT_FILE);
	}

	public static String readString(Class<?> owner, String fileName) throws IOException {
		return Files.readString(resolve(owner, fileName));
	}

	public static List<String> readNonEmptyLines(Class<?> owner) throws IOException {
		return readNonEmptyLines(owner, true);
	}

	public static List<String> readNonEmptyLines(Class<?> owner, boolean example) throws IOException {
		return readNonEmptyLines(owner, example ? EXAMPLE_FILE : INPUT_FILE);
	}

	public static List<String> readNonEmptyLines(Class<?> owner, String fileName) throws IOException {
		List<String> lines = new ArrayList<>();
		for (String line : Files.readAllLines(resolve(owner, fileName))) {
			if (!line.isEmpty()) {
				lines.add(line);
			}
		}
		return lines;
	}

	private static Path resolve(Class<?> owner, String fileName) throws IOException {
		Path direct = Paths.get(fileName);
		if (Files.exists(direct)) {
			return direct;
		}

		String packagePath = owner.getPackageName().replace('.', '/');
		String lowerPackagePath = packagePath.toLowerCase(Locale.ROOT);

		for (Path sourceRoot : SOURCE_ROOTS) {
			Path candidate = sourceRoot.resolve(packagePath).resolve(fileName);
			if (Files.exists(candidate)) {
				return candidate;
			}

			if (!lowerPackagePath.equals(packagePath)) {
				Path lowerCandidate = sourceRoot.resolve(lowerPackagePath).resolve(fileName);
				if (Files.exists(lowerCandidate)) {
					return lowerCandidate;
				}
			}
		}

		throw new IOException("Could not locate " + fileName + " for " + owner.getName());
	}
}

