package com.draniksoft.ome.editor.extensions.sub;

import com.draniksoft.ome.editor.extensions.Extension;
import com.draniksoft.ome.editor.extensions.export.ExtensionExporter;
import com.draniksoft.ome.editor.support.track.ReferenceTracker;
import com.draniksoft.ome.support.execution_base.ExecutionProvider;

public abstract class SubExtension implements ReferenceTracker {

    public Extension extension;

    /*
    	Loading
     */

    public abstract void load(ExecutionProvider p);

    /*
    	Exporting
     */

    /*
    	DO ALL DAO WRITES ON THE SAME THREAD, EXPORT FILES ON ANOTHER !!
     */

    public abstract void export(ExtensionExporter exporter);

    /*
    	Usage tracking
     */

    private int references = 0;

    @Override
    public int references() {
	  return references;
    }

    @Override
    public int reference(int delta) {
	  if (delta == 0) return 0;

	  int oldref = references;
	  references += delta;

	  if (oldref == 0 && references > 0) {
		extension.reference(1);
		return 1;
	  }
	  if (references == 0 && oldref > 0) {
		extension.reference(-1);
		return -1;
	  }

	  return 0;
    }
}
