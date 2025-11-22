package com.fastag.backend_services.variables;


import java.util.HashMap;
import java.util.Map;

public class Variables {

        public static final int SMART_RC = 20;
        public static final int PARIVAAN_RC = 20;
        public static final int DIGI_LOCKER_RC= 10;
        public static final int DRIVING_LICENCE_RC  = 0;
        public static final int VOTER_ID = 10;
        public static final String Currency_Type = "INR";
        public static final String SUB_AGENT = "SUBAGENT";
        public static final String AGENT = "AGENT";
        public static final double Set_Intial_Balance = 0;
        public static final String[] Permissions = new String[] {
                "sub-agent-management",
                "services",
                "wallet",
                "support",
                "user-management"
        };
        public static final String[] ServiceAccess = new String[] {
                "smartrc",
                "digilocker",
                "parivaanrc",
                "smartrc-chassis",
                "rc_mobile"
        };

}
