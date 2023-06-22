package org.AbstractFabric;

import org.AbstractFabric.BaseTeam.Captain;
import org.AbstractFabric.BaseTeam.Engineer;
import org.AbstractFabric.BaseTeam.Scientist;
import org.AbstractFabric.MarsSpaceShip.MarsMissionTeam;

public class FirstMarsMission extends MarsMissionTeam {
    public static void main(String[] args) {
        FirstMarsMission firstMarsMission = new FirstMarsMission();
        firstMarsMission.getCaptain().command();
        firstMarsMission.getEngineer().maintains();
        firstMarsMission.getScientist().exploring();
    }
    @Override
    public Captain getCaptain() {
        return super.getCaptain();
    }

    @Override
    public Engineer getEngineer() {
        return super.getEngineer();
    }

    @Override
    public Scientist getScientist() {
        return super.getScientist();
    }
}
