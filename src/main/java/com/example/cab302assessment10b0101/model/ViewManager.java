package com.example.cab302assessment10b0101.model;

import com.example.cab302assessment10b0101.views.ViewFactory;

public class ViewManager {
    private static ViewManager viewManager;
    private final ViewFactory viewFactory;

    private ViewManager(){
        this.viewFactory = new ViewFactory();

    }

    public static ViewManager getInstance(){
        if(viewManager == null) {
            viewManager = new ViewManager();
        }
        return viewManager;
    }

    public ViewFactory getViewFactory(){
        return viewFactory;
    }
}
