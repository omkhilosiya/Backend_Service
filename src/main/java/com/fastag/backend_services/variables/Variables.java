package com.fastag.backend_services.variables;


import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class Variables {
        public static final String AUTHORISED_KEY = "TVRJek5EVTJOelUwTnpKRFQxSlFNREF3TURFPQ==";
        public static final int SMART_RC = 20;
        public static final String CLIENT_ID = "5adc4db77eb60bedf8cffb4b98b546fc:dbdd43b2a35b948dd750ef2fd93a280b";
        public static final String SECRET_KEY = "qrMLUo7eZIHyKM3bIkHl3TpgEStLIugSgqODxMmzfhgwXdxrvhjZMbJySAXdTVSzs";
        public static final int PARIVAAN_RC = 20;
        public static final int DIGI_LOCKER_RC= 10;
        public static final int DRIVING_LICENCE_RC  = 0;
        public static final int VOTER_ID = 10;
        public static final String Currency_Type = "INR";
        public static final String SUB_AGENT = "SUBAGENT";
        public static final String AGENT = "AGENT";
        public static final String ADMIN = "ADMIN";
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

    public static final URI INVINCIBLE_RC_URL = URI.create("https://api.invincibleocean.com/invincible/vehicleRcV6");
    public static final URI REVERSE_RC = URI.create("https://api.invincibleocean.com/invincible/vehicleByChassisLive");

}
