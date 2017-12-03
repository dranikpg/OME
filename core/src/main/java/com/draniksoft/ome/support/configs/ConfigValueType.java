package com.draniksoft.ome.support.configs;

public abstract class ConfigValueType {

    public static BooleanType boolT = new BooleanType();
    public static FreeStringType freeStringT = new FreeStringType();
    public static FreeIntType freeIntT = new FreeIntType();

    public static ConfigValueType constructBoundedIntT(int s, int e, int step) {
	  return new BoundedIntType(s, e, step);
    }

    public static class BoundedIntType extends ConfigValueType {

	  int s = 0;
	  int e = Integer.MAX_VALUE;
	  int st = 1;

	  public BoundedIntType(int s, int e, int st) {
		this.s = s;
		this.e = e;
		this.st = st;
	  }

	  @Override
	  public Class getT() {
		return Integer.class;
	  }

	  @Override
	  public boolean canSet(Object val) {
		Integer i = (Integer) val;

		return i % st == 0 && s <= i && i <= e;

	  }
    }


    public static class FreeIntType extends ConfigValueType {

	  @Override
	  public Class getT() {
		return Integer.class;
	  }

	  @Override
	  public boolean canSet(Object val) {
		return val instanceof Integer;
	  }
    }

    public static class BooleanType extends ConfigValueType {

        @Override
        public Class getT() {
            return Boolean.class;
        }

        @Override
        public boolean canSet(Object val) {
            return val instanceof Boolean;
        }
    }

    public static class FreeStringType extends ConfigValueType {
        @Override
        public Class getT() {
            return String.class;
        }

        @Override
        public boolean canSet(Object val) {
            return val instanceof String;
        }
    }


    public abstract Class getT();

    public abstract boolean canSet(Object val);

}
