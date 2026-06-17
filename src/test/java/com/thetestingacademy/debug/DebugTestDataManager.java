package com.thetestingacademy.debug;

import com.thetestingacademy.enums.FlowType;
import com.thetestingacademy.enums.SuiteType;
import com.thetestingacademy.utils.TestDataManager;

public class DebugTestDataManager {

    public static void main(String[] args) {

        TestDataManager manager = TestDataManager.getInstance();

        System.out.println("\n==============================");
        System.out.println("      TEST DATA DEBUG RUN     ");
        System.out.println("==============================\n");

        // ================= E2E =================
        System.out.println(">>> Suite: E2E | Flow: Existing OC");

        manager.initForSuite(SuiteType.E2E, FlowType.EXISTING_OC);

        System.out.println("Remaining Data: " + manager.remainingDataCount());

        for (int i = 0; i < 3; i++) {
            System.out.println("DATA -> " + manager.getNextData());
        }

        // ================= REGRESSION =================
        System.out.println("\n>>> Suite: Regression | Flow: New OC");

        manager.initForSuite(SuiteType.Regression, FlowType.NEW_OC);

        System.out.println("Remaining Data: " + manager.remainingDataCount());

        for (int i = 0; i < 3; i++) {
            System.out.println("DATA -> " + manager.getNextData());
        }

        // ================= SMOKE =================
        System.out.println("\n>>> Suite: SMOKE (E2E + REGRESSION) | Flow: Existing OC");

        manager.initForSuite(SuiteType.SMOKE, FlowType.EXISTING_OC);

        System.out.println("Remaining Data: " + manager.remainingDataCount());

        for (int i = 0; i < 5; i++) {
            System.out.println("DATA -> " + manager.getNextData());
        }

        System.out.println("\n==============================");
        System.out.println("       DEBUG COMPLETE         ");
        System.out.println("==============================\n");
    }
}