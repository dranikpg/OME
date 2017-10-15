package com.draniksoft.ome.editor.support.event.asset;

import net.mostlyoriginal.api.event.common.Event;

public class AssetListChange implements Event {

    public AssetListChange(String id) {
        this.id = id;
    }

    public AssetListChange() {
    }

    public String id;

}
