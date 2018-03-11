package com.draniksoft.ome.utils.struct;

import java.util.Iterator;

public class CompoundIterator<T> implements Iterator<T> {

    Iterator<T>[] its;
    int pos = 0;

    public CompoundIterator(Iterator<T>... its) {
	  this.its = its;
    }

    public boolean hasNext(int p) {
	  if (p >= its.length) return false;
	  if (its[p].hasNext()) {
		return true;
	  } else {
		if (p >= its.length) return false;
		return hasNext(p + 1);
	  }
    }

    @Override
    public boolean hasNext() {
	  return hasNext(pos);
    }

    @Override
    public T next() {
	  if (pos >= its.length) return null;
	  if (!its[pos].hasNext()) pos++;
	  T var = its[pos].next();
	  return var;
    }

    @Override
    public void remove() {

    }


}
