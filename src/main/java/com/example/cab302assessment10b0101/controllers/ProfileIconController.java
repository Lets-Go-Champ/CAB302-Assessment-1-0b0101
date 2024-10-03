package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.model.ViewManager;
import com.example.cab302assessment10b0101.views.MenuOptions;

/**
 * The ProfileIconController class handles the displaying of the Icon and switching of views
 * when interacted with.
 */
public class ProfileIconController {

    // Change the view to the user's profile
    public void getProfileView() {
        ViewManager.getInstance().getViewFactory().getUserSelectedMenuItem().set(MenuOptions.PROFILE);
    }
}
