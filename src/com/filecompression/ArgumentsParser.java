package com.filecompression;

public class ArgumentsParser {
    private final String option;
    private final String filePath;
    private final String dirPath;
    private final String fileName;

    ArgumentsParser(String[] arguments)
    {
        option = (arguments.length>1 && arguments[1].equals("d")) ? "Decompress" : "Compress";
        filePath = arguments[0];

        int splitIndex = filePath.lastIndexOf("\\") + 1;
        dirPath = filePath.substring(0,splitIndex);
        fileName = filePath.substring(splitIndex);
    }

    public String getOption(){ return option; }

    public String getFilePath(){ return filePath; }

    public String getDirPath(){ return dirPath; }

    public String getFileName(){ return fileName; }
}
