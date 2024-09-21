package com.example.cab302assessment10b0101.model;

import com.example.cab302assessment10b0101.views.ViewFactory;

/**
 * The ViewManager class provides a singleton instance for managing views
 * in the library management system. It acts as a centralised point for
 * creating and accessing views through the ViewFactory.
 */
public class ViewManager {
    private static ViewManager viewManager;
    private final ViewFactory viewFactory;

    /**
     * Private constructor to prevent direct instantiation.
     * Initialises the ViewFactory instance for creating views.
     */
    private ViewManager(){
        this.viewFactory = new ViewFactory();

    }

    /**
     * Singleton pattern to get the single instance of ViewManager.
     * Ensures that only one instance of this class is created throughout the application.
     *
     * @return The single instance of ViewManager.
     */
    public static synchronized ViewManager getInstance(){
        if(viewManager == null) {
            viewManager = new ViewManager();
        }
        return viewManager;
    }

    /**
     * Retrieves the ViewFactory instance used for creating views.
     *
     * @return The ViewFactory instance.
     */
    public ViewFactory getViewFactory(){
        return viewFactory;
    }
}
