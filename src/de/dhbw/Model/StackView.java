package de.dhbw.Model;

/**
 * Created by tobi on 22.05.17.
 */
public class StackView {
    private String stackContent;

    public String getStackContent() {
        return stackContent;
    }

    public void setStackContent(Integer stackContent) {
        this.stackContent = String.format("%04x", stackContent);
    }
}
