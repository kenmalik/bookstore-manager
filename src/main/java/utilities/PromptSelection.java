package main.java.utilities;

/**
 * An interface that defines the behavior of an object which can be displayed in a choice prompt.
 */
public interface PromptSelection {
    String getLabel();

    enum StandardOption implements PromptSelection {
        YES, NO, QUIT;

        public String getLabel() {
            return name().charAt(0) + name().substring(1).toLowerCase();
        }
    }
}
