package com.fastag.backend_services.variables;

import com.fastag.backend_services.Model.User;
import com.fastag.backend_services.Model.Wallet;
import com.fastag.backend_services.dto.DashboardResponse;
import com.fastag.backend_services.component.SignupRequest;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class CommonMethod {

    public static User addAlltheDetails(SignupRequest user, String Password){

        User UpdatedUser = new User();
        UpdatedUser.setUserId(generateUserId());
        UpdatedUser.setEmail(user.getUsername());
        UpdatedUser.setPassword(Password);
        UpdatedUser.setRoles("ROLE_USER");
        UpdatedUser.setUsername(user.getUsername());
        UpdatedUser.setGender("Male");
        UpdatedUser.setStatus(false);
        UpdatedUser.setPermissions(List.of(Variables.Permissions));
        UpdatedUser.setServiceAccess(Arrays.asList(Variables.ServiceAccess));
        return UpdatedUser;

    }

        public static DashboardResponse updateTheWallet(Wallet walletDetails){

            DashboardResponse tempDashboardResponse = new DashboardResponse();
            tempDashboardResponse.setSuccess(true);
            tempDashboardResponse.setWalletDetails(walletDetails);
            tempDashboardResponse.setCurrency(Variables.Currency_Type);
            tempDashboardResponse.setLastUpdated(LocalDateTime.now());
            return tempDashboardResponse;
    }

    public static Wallet createTheWallet(User user) {
        Wallet newWallet = new Wallet();
        newWallet.setUserId(user.getUserId());
        newWallet.setEntityId(generateFastagId());
        newWallet.setType(Variables.AGENT);
        newWallet.setBalance(Variables.Set_Intial_Balance);
        newWallet.setCreatedAt(LocalDateTime.now());
        newWallet.setCreatedAt(LocalDateTime.now());
        return newWallet;
    }

    public static String generateFastagId() {
        long number = (long)(Math.random() * 1_000_000_000_000L); // 12-digit range
        return "FASTAG" + String.format("%012d", number);
    }

    public static String generateUserId() {
        String chars = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(15);
        java.util.Random random = new java.util.Random();

        for (int i = 0; i < 15; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }

        return sb.toString();
    }


}
