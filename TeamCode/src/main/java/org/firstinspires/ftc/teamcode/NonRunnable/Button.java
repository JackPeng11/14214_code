package org.firstinspires.ftc.teamcode.NonRunnable;

public class Button
{
    private boolean    wasPressed        = false;
    private boolean    finishedExecuting = true;
    private ButtonType buttonType;
    
    public Button()
    {
    
    }
    
    public Button(ButtonType buttonType)
    {
        this.buttonType = buttonType;
    }
    
    public boolean isPressed(boolean button)
    {
        boolean tempWasPressed = wasPressed;
        wasPressed = button;
        if (button && !tempWasPressed)
        {
            finishedExecuting = false;
        }
        return button && !tempWasPressed;
    }
    
    public boolean isAlreadyFinished()
    {
        return finishedExecuting;
    }
    
    public void isFinished()
    {
        finishedExecuting = true;
    }
    
    public enum ButtonType
    {
        TOGGLE, HOLD_PRESSED;
    }
}
