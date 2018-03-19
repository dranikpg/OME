package com.draniksoft.ome.editor.extensions.export;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonValue;
import com.draniksoft.ome.editor.extensions.Extension;
import com.draniksoft.ome.support.execution_base.ExecutionProvider;
import com.draniksoft.ome.utils.FUtills;

import java.util.concurrent.Callable;

public class ExtensionExporter implements Callable<Void> {

    private static final String tag = "ExtensionExporter";

    Extension ext;
    JsonValue mainIndex;
    ExportStrategy stg;

    FileHandle fileRoot;
    ExecutionProvider provider;

    public ExtensionExporter(Extension ext, ExportStrategy stg) {
	  this.ext = ext;
	  fileRoot = FUtills.uriToFile(ext.dao.URI);
	  if (!fileRoot.exists()) fileRoot.mkdirs();
    }

    @Override
    public Void call() throws Exception {
	  ext.save(this);
	  return null;
    }


    public void setProvider(ExecutionProvider provider) {
	  this.provider = provider;
    }

    public ExecutionProvider execp() {
	  return provider;
    }

    public ExportStrategy strategy() {
	  return stg;
    }

    public JsonValue mainIndex() {
	  return mainIndex;
    }

    public FileHandle getFileRoot() {
	  return fileRoot;
    }
}
