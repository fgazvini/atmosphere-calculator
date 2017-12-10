package com.chelonix.atmocalc.server;

import com.chelonix.atmocalc.AtmosphereCalculator;
import net.codestory.http.WebServer;

/**
 * Created with IntelliJ IDEA.
 * User: sirot
 * Date: 22/08/15
 * Time: 08:31
 * To change this template use File | Settings | File Templates.
 */
public class Main
{
    public static void main(String[] args) {
        new WebServer().configure(
                routes -> routes.add("/atmosphere", AtmosphereCalculatorResource.class))
            .start(8181);
    }
}
