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
		return hasNext(p + 1);
	  }
    }

    public T next(int pp) {

	  if (pp >= its.length) return null;
	  if (!its[pp].hasNext()) return next(pp + 1);
	  T var = its[pp].next();

	  return var;
    }

    @Override
    public boolean hasNext() {
	  return hasNext(pos);
    }

    @Override
    public T next() {
	  return next(pos);
    }

    @Override
    public void remove() {

    }


}
