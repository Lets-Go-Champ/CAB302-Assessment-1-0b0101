package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.model.ViewManager;
import com.example.cab302assessment10b0101.views.MenuOptions;


public class ProfileIconController {

    public void getProfileView() {
        ViewManager.getInstance().getViewFactory().getUserSelectedMenuItem().set(MenuOptions.PROFILE);
    }
}
