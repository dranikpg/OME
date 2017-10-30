package com.draniksoft.ome.support.configs;

public abstract class ConfigValueType {

    public static BooleanType boolT = new BooleanType();
    public static FreeStringType freeStringT = new FreeStringType();

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
