package org.Fabric.staffGetting;

import org.Fabric.staff.Programmer;
import org.Fabric.staff.StaffMember;

public class ProgrammerFabric implements StaffFabric {
    @Override
    public StaffMember employ() {
        return new Programmer();
    }
}
