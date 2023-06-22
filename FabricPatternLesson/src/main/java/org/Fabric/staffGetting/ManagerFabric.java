package org.Fabric.staffGetting;

import org.Fabric.staff.Manager;
import org.Fabric.staff.StaffMember;

public class ManagerFabric implements StaffFabric{
    @Override
    public StaffMember employ() {
        return new Manager();
    }
}
