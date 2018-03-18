package com.draniksoft.ome.editor.struct.text_ext_test;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.draniksoft.ome.editor.extensions.export.ExtensionExporter;
import com.draniksoft.ome.editor.extensions.sub.SubExtension;
import com.draniksoft.ome.support.execution_base.ExecutionProvider;
import com.draniksoft.ome.utils.FUtills;

public class TheTextSubExt extends SubExtension {

    private static final String tag = "TheTextSubExt";

    String name;
    String content;

    public TheText t;

    @Override
    public void load(ExecutionProvider p, World w) {

	  Gdx.app.debug(tag, "Loading");

	  String path = FUtills.uriToPath(extension.dao.URI + "/text.txt");
	  content = Gdx.files.absolute(path).readString();
	  t = new TheText();
	  t.text = content;
	  t.name = name;
    }

    @Override
    public void export(ExtensionExporter exporter) {
	  Gdx.app.debug(tag, "Exporting");
	  FUtills.uriToFile(extension.dao.URI + "/text.txt").writeString(t.text, false);
    }
}
