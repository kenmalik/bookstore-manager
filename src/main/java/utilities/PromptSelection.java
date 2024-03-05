package main.java.utilities;

/**
 * An interface that defines the behavior of an object which can be displayed in a choice prompt.
 */
public interface PromptSelection {
    /**
     * Gets the display label associated with the selection.
     * @return the display label.
     */
    String getLabel();

    /**
     * An enumeration of standard options in choice prompts.
     */
    enum StandardOption implements PromptSelection {
        YES, NO, QUIT;

        public String getLabel() {
            return name().charAt(0) + name().substring(1).toLowerCase();
        }
    }
}
