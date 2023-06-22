package org.AbstractFabric.MarsSpaceShip;

import org.AbstractFabric.BaseTeam.Captain;
import org.AbstractFabric.BaseTeam.Engineer;
import org.AbstractFabric.BaseTeam.Scientist;
import org.AbstractFabric.BaseTeam.SpaceShipTeam;

public class MarsMissionTeam implements SpaceShipTeam {

    @Override
    public Captain getCaptain() {
        return new MarsMissionCaptain();
    }

    @Override
    public Engineer getEngineer() {
        return new MarsMissionEngineer();
    }

    @Override
    public Scientist getScientist() {
        return new MarsMissionScientist();
    }
}
