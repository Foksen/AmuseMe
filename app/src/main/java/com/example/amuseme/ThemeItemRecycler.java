package com.example.amuseme;

public class ThemeItemRecycler {
    public String themeTitle;
    public String themeDesc;
    public int themeImgID;
    public int themeImgBWID;

    public static final int defaultImgID = R.drawable.theme_img_default;
    public static final int defaultImgBWID = R.drawable.theme_img_default_bw;

    public ThemeItemRecycler(String themeTitle, String themeDesc, int themeImgID, int themeImgBWID) {
        this.themeTitle = themeTitle;
        this.themeDesc = themeDesc;
        this.themeImgID = themeImgID;
        this.themeImgBWID = themeImgBWID;
    }
}
