package com.junwu.plugin;


import com.junwu.plugin.factory.PlugFactory;

/**
 * Created by Glen on 2016/6/7.
 * To modify the relevant code, please contact the developer Glen
 */
public class Plugs extends PlugFactory {
    private static PlugFactory factory;

    private Plugs() {
        super();
    }

    public static PlugFactory getInstance() {
        if (factory == null) {
            factory = new Plugs();
        }
        return factory;
    }
}