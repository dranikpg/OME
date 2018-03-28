package com.draniksoft.ome.editor.res.impl.extmgmnt;

import com.draniksoft.ome.editor.res.impl.res_ifaces.RootResource;
import com.draniksoft.ome.utils.lang.Text;

public class ResourceContainer<TYPE> {
    public boolean resolved = false;

    public Text name;
    public ResourceContainer<TYPE> constr;
    public RootResource<TYPE> res;
}
