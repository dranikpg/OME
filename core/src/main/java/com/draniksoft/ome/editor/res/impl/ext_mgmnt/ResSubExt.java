package com.draniksoft.ome.editor.res.impl.ext_mgmnt;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.draniksoft.ome.editor.extensions.export.ExtensionExporter;
import com.draniksoft.ome.editor.extensions.sub.SubExtension;
import com.draniksoft.ome.editor.manager.SerializationManager;
import com.draniksoft.ome.editor.res.impl.types.ResTypes;
import com.draniksoft.ome.support.execution_base.ExecutionProvider;
import com.draniksoft.ome.utils.FUtills;

import java.io.FileNotFoundException;

public class ResSubExt extends SubExtension {

    Array<ResContainerBase> ar;

    public ResSubExt() {

    }

    public ResContainerBase get(ResTypes t) {
	  return ar.get(t.ordinal());
    }


    public void register(ResContainer ct) {
	  reference(ct.reference(1));
    }

    public void unregister(ResContainer ct) {
	  reference(ct.reference(-1));
    }


    @Override
    public void load(ExecutionProvider p) {

	  FileHandle h = FUtills.uriToFile(extension.dao.URI + "/res.kryo");

	  if (h.exists()) {
		SerializationManager sm = extension.w.getSystem(SerializationManager.class);
		try {
		    ar = sm.loadKryoData(h.path(), Array.class);

		    for (ResContainerBase b : ar) {
			  b.init(extension.w);
		    }

		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		}
	  } else {
		ar = new Array<ResContainerBase>();
		ar.ensureCapacity(ResTypes.values().length);
		for (ResTypes t : ResTypes.values()) {
		    ResContainerBase b = new ResContainerBase();
		    b.empty(t);
		    ar.add(b);
		}
	  }


	  for (ResTypes t : ResTypes.values()) {
		ar.get(t.ordinal()).ext = this;
	  }
    }


    @Override
    public void export(ExtensionExporter exporter) {
	  FileHandle h = exporter.getFileRoot().child("res.kryo");
	  SerializationManager sm = extension.w.getSystem(SerializationManager.class);
	  try {
		sm.writeKryoData(h.path(), ar);
	  } catch (FileNotFoundException e) {
		e.printStackTrace();
	  }
    }
}
