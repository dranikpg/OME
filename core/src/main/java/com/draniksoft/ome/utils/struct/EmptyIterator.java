package com.draniksoft.ome.utils.struct;

import java.util.Iterator;

public class EmptyIterator<TYPE> implements Iterator<TYPE> {
    @Override
    public boolean hasNext() {
	  return false;
    }

    @Override
    public TYPE next() {
	  return null;
    }

    @Override
    public void remove() {

    }


}
