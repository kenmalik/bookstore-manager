package main.java.menus;

public interface PromptSelection {
    enum StandardOption implements PromptSelection {
        YES, NO, QUIT;
        public String getLabel() {
            return name().charAt(0) + name().substring(1).toLowerCase();
        }
    }

    String getLabel();
}
