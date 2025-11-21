package com.fastag.backend_services.variables;

import com.fastag.backend_services.Model.User;
import com.fastag.backend_services.component.SignupRequest;
import com.fastag.backend_services.variables.Variables;

import java.util.Arrays;
import java.util.List;

public class CommonMethod {

    public static User addAlltheDetails(SignupRequest user, String Password){

        User UpdatedUser = new User();
        UpdatedUser.setEmail(user.getUsername());
        UpdatedUser.setPassword(Password);
        UpdatedUser.setRoles("ROLE_USER");
        UpdatedUser.setUsername(user.getUsername());
        UpdatedUser.setGender("Male");
        UpdatedUser.setPermissions(List.of(Variables.Permissions));
        UpdatedUser.setServiceAccess(Arrays.asList(Variables.ServiceAccess));
        return UpdatedUser;
    }

}
