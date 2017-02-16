package me.dmillerw.quadrum.feature.data.loader;

/**
 * @author dmillerw
 */
public class TraitLoader {

    private static State currentlyLoading;

    public static void setCurrentlyLoading(State state) {
        TraitLoader.currentlyLoading = state;
    }

    public static State getCurrentlyLoading() {
        return TraitLoader.currentlyLoading;
    }

    public static class State {

        public String filename;
        public Type type;

        public State(String filename, Type type) {
            this.filename = filename;
            this.type = type;
        }
    }

    public static enum Type {

        BLOCK, ITEM
    }
}
