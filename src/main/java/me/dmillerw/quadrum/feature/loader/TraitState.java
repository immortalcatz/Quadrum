package me.dmillerw.quadrum.feature.loader;

/**
 * @author dmillerw
 */
public class TraitState {

    private static State currentlyLoading;

    public static void setCurrentlyLoading(State state) {
        TraitState.currentlyLoading = state;
    }

    public static State getCurrentlyLoading() {
        return TraitState.currentlyLoading;
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
