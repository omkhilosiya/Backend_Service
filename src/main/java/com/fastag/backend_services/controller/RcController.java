// ======================= RcController =======================
package com.fastag.backend_services.controller;

import com.fastag.backend_services.dto.RcVerifyPaymentRequest;
import com.fastag.backend_services.service.PaysprintRcService;
import com.fastag.backend_services.service.RcPdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class RcController {

    @Autowired
    private PaysprintRcService rcService;

    @Autowired
    private RcPdfService rcPdfService;

    // 1️⃣ VERIFY
    @PostMapping("/rc/verify")
    public String verify(@RequestBody RcVerifyPaymentRequest rcVerifyPaymentRequest) {
        return rcService.verifyRc(rcVerifyPaymentRequest);
    }

    // 2️⃣ GENERATE QR
    @PostMapping("/generate-qr")
    public ResponseEntity<Map<String, Object>> generateQR(@RequestBody Map<String, Object> apiResponse) {
        try {
            System.out.println("===== /generate-qr API HIT =====");
            System.out.println("Incoming API Response: " + apiResponse);

            if(apiResponse == null || apiResponse.get("result") == null){
                throw new RuntimeException("result node missing in request");
            }

            Map<String, Object> result = (Map<String, Object>) apiResponse.get("result");

            if(result.get("data") == null){
                throw new RuntimeException("data node missing in request");
            }

            Map<String, Object> data = (Map<String, Object>) result.get("data");
            System.out.println("Data Node: " + data);

            String text = "Vehicle Receipt Details\n\n"
                    + "Please check response is coming from\n\n"
                    + "https://parivahan.gov.in/...\n\n"
                    + "State Name : " + data.getOrDefault("stateName","-") + "\n"
                    + "Office Name : " + data.getOrDefault("regAuthority","-") + "\n"
                    + "Vehicle Registration Number : " + data.getOrDefault("regNo","-") + "\n"
                    + "Chassis Number : " + data.getOrDefault("chassis","-") + "\n"
                    + "Engine Number : " + data.getOrDefault("engine","-") + "\n"
                    + "Seating Capacity : " + data.getOrDefault("vehicleSeatCapacity","-") + "\n"
                    + "Standing Capacity : " + data.getOrDefault("vehicleStandingCapacity","0") + "\n"
                    + "Sleeper Capacity: " + data.getOrDefault("vehicleSleeperCapacity","0") + "\n"
                    + "Owner Name: " + data.getOrDefault("owner","-") + "\n"
                    + "Father's Name: " + data.getOrDefault("ownerFatherName","-") + "\n"
                    + "Laden Weight : " + data.getOrDefault("grossVehicleWeight","-") + "\n"
                    + "Unladen Weight : " + data.getOrDefault("unladenWeight","-") + "\n"
                    + "Registration From Date: " + data.getOrDefault("regDate","-") + "\n"
                    + "Registration Upto Date: " + data.getOrDefault("rcExpiryDate","-") + "\n"
                    + "Address: " + data.getOrDefault("presentAddress","-");

            System.out.println("QR TEXT CONTENT: " + text);

            String qrData =
                    "data:text/plain;charset=utf-8,"
                            + java.net.URLEncoder.encode(text, java.nio.charset.StandardCharsets.UTF_8);

            System.out.println("Encoded QR Data Generated");

            String base64QR =
                    com.fastag.backend_services.component.QRCodeGenerator.generateBase64QRCode(qrData);

            System.out.println("Generated Base64 QR Successfully");

            data.put("qrCode", base64QR);

            System.out.println("===== QR Generation Completed Successfully =====");

            return ResponseEntity.ok(apiResponse);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR in /generate-qr: " + e.getMessage());

            Map<String,Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());

            return ResponseEntity.status(500).body(error);
        }
    }


    // 3️⃣ DOWNLOAD PDF (USE QR FROM API-2 — DO NOT REGENERATE)
    @PostMapping("/download")
    public ResponseEntity<byte[]> downloadRC(@RequestBody Map<String, Object> rcData) throws Exception {

        byte[] pdfBytes = rcPdfService.generateRcPdf(rcData);

        return ResponseEntity.ok()
                .header("Content-Type", "application/pdf")
                .header("Content-Disposition", "attachment; filename=RC.pdf")
                .body(pdfBytes);
    }



    @PostMapping("/downloadcard")
    public ResponseEntity<byte[]> downloadSmartCard(@RequestBody Map<String,Object> rcData) throws Exception {

        byte[] pdfBytes = rcPdfService.generateVehicleSmartCard(rcData);

        return ResponseEntity.ok()
                .header("Content-Type", "application/pdf")
                .header("Content-Disposition", "attachment; filename=Vehicle_RC_Card.pdf")
                .body(pdfBytes);
    }



}
