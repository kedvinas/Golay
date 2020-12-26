import utils.FileUtils;
import utils.Matrices;
import utils.StringUtils;

import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Usage: java -jar Golay.jar [OPTION]... INPUT_FILE OUTPUT_FILE");
            System.out.println("Encode or decode INPUT_FILE to OUTPUT_FILE (using Golay24 code)");
            System.out.println("Options:");
            System.out.println("-d    decode");
            System.out.println("-i    invert (list of positions separated by comma(,))");
            return;
        }

        String inputFile = args[args.length - 2];
        String outputFile = args[args.length - 1];

        if (args.length == 2) {
            String input = FileUtils.readFile(inputFile);
            int[] encoded = Golay.encode(StringUtils.stringToBinaryArray(input));
            FileUtils.writeFile(outputFile, StringUtils.binaryArrayToString(encoded));
        } else if (args[0].equals("-d")) {
            try {
                String input = FileUtils.readFile(inputFile);
                int[] decoded = Golay.decode(StringUtils.stringToBinaryArray(input));
                FileUtils.writeFile(outputFile, StringUtils.binaryArrayToString(decoded));
            } catch (IllegalArgumentException e) {
                System.out.println("Cannot find solution, data is too corrupted.");
            }
        } else if (args[0].equals("-i")) {
            int[] positions = Arrays.stream(args[1].split(",")).mapToInt(Integer::parseInt).toArray();

            String input = FileUtils.readFile(inputFile);
            int[] inputArray = StringUtils.stringToBinaryArray(input);
            Matrices.invert(inputArray, positions);
            FileUtils.writeFile(outputFile, StringUtils.binaryArrayToString(inputArray));
        }
    }
}
