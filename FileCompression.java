import java.nio.file.Files;
import java.nio.file.Paths;

public class FileCompression {
    public static void main(String[] args) throws Exception {
        System.out.println("Welcome, this is a file compression application! \n");

        StringBuilder filePath = new StringBuilder(args[0]);
        StringBuilder dirPath = new StringBuilder();
        StringBuilder fileName = new StringBuilder();
        StringBuilder option =
                (args.length>1 && args[1].equals("d")) ? new StringBuilder(args[1]) : new StringBuilder();

        fileParser(filePath, dirPath, fileName);

        String text = readFileAsString(filePath);
        System.out.println(text);

    }
    // parser method to parse given filepath
    public static void fileParser(StringBuilder filePath, StringBuilder dirPath, StringBuilder fileName)
    {
        int splitIndex = filePath.lastIndexOf("\\") + 1;
        dirPath.append(filePath.substring(0,splitIndex));
        fileName.append(filePath.substring(splitIndex));
    }

    // read method to convert text file into string
    public static String readFileAsString(StringBuilder filePath) throws Exception
    {
        return new String(Files.readAllBytes(Paths.get(String.valueOf(filePath))));
    }
}
