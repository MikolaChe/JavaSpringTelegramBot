package org.Fabric;

import org.Fabric.staff.Manager;
import org.Fabric.staff.Programmer;
import org.Fabric.staff.StaffMember;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        StaffMember staffMember = getStaffMember(chooseProfession());
        staffMember.working();

    }
    private static StaffMember getStaffMember(String profession){
        if(profession.equalsIgnoreCase("programmer")){
            return new Programmer();
        } else if (profession.equalsIgnoreCase("manager")) {
            return new Manager();
        }
        else System.out.println(profession + " is unknown profession");
        return null;
    }
    private static String chooseProfession(){
        Scanner scanner = new Scanner(System.in);
        return scanner.next();
    }
}