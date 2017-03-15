package me.dmillerw.quadrum.feature.loader;

import me.dmillerw.quadrum.feature.trait.Traits;

/**
 * @author dmillerw
 */
public class LoaderState {

    private static State currentlyLoading;

    public static void setCurrentlyLoading(State state) {
        LoaderState.currentlyLoading = state;
    }

    public static State getCurrentlyLoading() {
        return LoaderState.currentlyLoading;
    }

    public static class State {

        public String filename;
        public Type type;

        public Traits loadingTrait;

        public State(String filename, Type type) {
            this.filename = filename;
            this.type = type;
        }

        public void setLoadingTrait(Traits type) {
            this.loadingTrait = type;
        }

        public Traits getLoadingTrait() {
            return loadingTrait;
        }
    }

    public static enum Type {

        BLOCK, ITEM
    }
}
