package com.fastag.backend_services.Model;


import com.fastag.backend_services.variables.Variables;
import lombok.Data;

@Data
public class ServicePrices {
    private int smartrc = Variables.SMART_RC;
    private int parivaanrc = Variables.PARIVAAN_RC;
    private int digilockerrc =  Variables.DIGI_LOCKER_RC;
    private int drivingLicense = Variables.DRIVING_LICENCE_RC;
    private int voterId = Variables.VOTER_ID;
}
