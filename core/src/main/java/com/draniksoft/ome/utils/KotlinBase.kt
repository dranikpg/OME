package com.draniksoft.ome.utils

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.utils.Layout
import com.kotcrab.vis.ui.widget.HorizontalCollapsibleWidget

object lel {


    fun HorizontalCollapsibleWidget.getMinWidth(): Float {
        if (isCollapsed) return 0f
        println("LELELEL" + (children.get(0) as Layout).minWidth)
        return (children.get(0) as Layout).minWidth
    }

    fun HorizontalCollapsibleWidget.draw(batch: Batch, parentAlpha: Float) {}

}