package org.AbstractFabric.MarsSpaceShip;

import org.AbstractFabric.BaseTeam.Engineer;

public class MarsMissionEngineer implements Engineer {
    @Override
    public void maintains() {
        System.out.println("Mars mission engineer maintains all the equipment");
    }
}
