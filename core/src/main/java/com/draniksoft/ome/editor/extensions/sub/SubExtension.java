package com.draniksoft.ome.editor.extensions.sub;

import com.artemis.World;
import com.draniksoft.ome.editor.extensions.Extension;
import com.draniksoft.ome.support.execution_base.ExecutionProvider;

public abstract class SubExtension {

    public Extension extension;

    /*
    	Loading
     */

    public abstract void load(ExecutionProvider p, World w);

    /*
    	Usage tracking
     */

    int usages = 0;

    public void increaseUsage() {
	  usages++;
    }

    public void decreaseUsage() {
	  usages--;
    }

    public boolean hasUsages() {
	  return usages > 0;
    }

}
