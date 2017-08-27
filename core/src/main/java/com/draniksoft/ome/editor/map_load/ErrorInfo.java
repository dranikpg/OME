package com.draniksoft.ome.editor.map_load;

public class ErrorInfo {

    public static class Types{
        public static final short FILE_CREATION_ERROR = 1;
    }

    public ErrorInfo() {

    }

    public ErrorInfo(short type, boolean critical, String additionalInfo) {
        this.type = type;
        this.critical = critical;
        this.additionalInfo = additionalInfo;
    }

    public short type;
    public boolean critical;

    public String additionalInfo;

}
