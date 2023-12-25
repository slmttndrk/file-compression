package com.filecompression;

public class ArgumentsParser {
    private final String option;
    private final String filePath;
    private final String dirPath;
    private final String fileName;

    ArgumentsParser(String[] arguments)
    {
        // store the coding type
        option = (arguments.length>1 && arguments[1].equals("d")) ? "Decompress" : "Compress";

        // store the file path to find the given file
        filePath = arguments[0];

        // find the splitting index in the file path to extract directory path and file name
        int splitIndex = filePath.lastIndexOf("\\") + 1;

        // store the directory path to write the resulting coded text
        dirPath = filePath.substring(0,splitIndex);

        // store the file name to create the name of the resulting coded text
        fileName = filePath.substring(splitIndex);
    }

    public String getOption(){ return option; }

    public String getFilePath(){ return filePath; }

    public String getDirPath(){ return dirPath; }

    public String getFileName(){ return fileName; }
}
