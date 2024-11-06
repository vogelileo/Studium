package main.java;

public class TextBox extends Rectangle implements Text{
    private String text;
    private int textColor;

    public String getText(){
        return this.text;
    }

    public int getTextColor(){
        return this.textColor;
    }

    public TextBox(int left, int top, int width, int height, int areaColor, String text, int textColor){
        super(left, top, width, height, areaColor);
        this.text = text;
        this.textColor  = textColor;
    }
}
