package org.AbstractFabric.MarsSpaceShip;

import org.AbstractFabric.BaseTeam.Captain;

public class MarsMissionCaptain implements Captain {
    @Override
    public void command() {
        System.out.println("Captain commands Mars mission...");
    }
}
