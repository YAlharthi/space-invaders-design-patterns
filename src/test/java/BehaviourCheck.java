import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Replays the scenarios in GameTrace and compares the result, line by line,
 * with the baseline recorded from the code before any pattern was applied.
 * Pass "record" as the first argument to write the baseline.
 */
public class BehaviourCheck {

	private static final Path BASELINE = Paths.get("src/test/resources/baseline-trace.txt");

	public static void main(String[] args) throws Exception {
		String trace = new GameTrace(false).record();
		if (args.length > 0 && args[0].equals("record")) {
			Files.write(BASELINE, trace.getBytes(StandardCharsets.UTF_8));
			System.out.println("Baseline written: " + trace.split("\n").length + " lines");
			return;
		}
		List<String> expected = Files.readAllLines(BASELINE, StandardCharsets.UTF_8);
		String[] actual = trace.split("\n");
		for (int i = 0; i < Math.max(expected.size(), actual.length); i++) {
			String want = i < expected.size() ? expected.get(i) : "(nothing)";
			String got = i < actual.length ? actual[i] : "(nothing)";
			if (!want.equals(got)) {
				System.out.println("FAIL at line " + (i + 1) + "\n  expected: " + want + "\n  actual:   " + got);
				System.exit(1);
			}
		}
		System.out.println("PASS: " + expected.size() + " recorded lines match the baseline");
	}
}
