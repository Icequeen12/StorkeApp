package com.example.pamela.udar.DragAndDrop;

public class ListItem {

    String nameRes;
    String iconRes;


    public ListItem(String nameRes, String iconRes) {
        this.nameRes = nameRes;
        this.iconRes = iconRes;
    }

    public ListItem() {
    }

    public String getNameRes() {
        return nameRes;
    }

    public void setNameRes(String nameRes) {
        this.nameRes = nameRes;
    }

    public String getIconRes() {
        return iconRes;
    }

    public void setIconRes(String iconRes) {
        this.iconRes = iconRes;
    }
}