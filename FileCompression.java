import java.nio.file.Files;
import java.nio.file.Paths;

public class FileCompression {
    public static void main(String[] args) throws Exception {
        System.out.println("Welcome, this is a file compression application! \n");

        String dsa = readFileAsString("C:\\Users\\slmtt\\Desktop\\DSA-Projects.txt");
        System.out.println(dsa);

    }

    // read method to convert text file into string
    public static String readFileAsString(String filePath) throws Exception
    {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }
}
