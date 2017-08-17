package com.draniksoft.ome.editor.map_load;

public class ErrorInfo {

    public static class Types{
        public static final short FILE_CREATION_ERROR = 1;
    }

    public short type;
    public boolean critical;

    public String file;
    public String additionalInfo;

}
