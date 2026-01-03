// ======================= RcController =======================
package com.fastag.backend_services.controller;

import com.fastag.backend_services.dto.RcVerifyPaymentRequest;
import com.fastag.backend_services.service.PaysprintRcService;
import com.fastag.backend_services.service.RcPdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Map<String, Object>> generateQR(@RequestBody Map<String, Object> apiResponse) throws Exception {

        Map<String, Object> result = (Map<String, Object>) apiResponse.get("result");
        Map<String, Object> data = (Map<String, Object>) result.get("data");

        String text =
                "Vehicle Receipt Details\n\n" +
                        "Please check response is coming from\n\n" +
                        "https://parivahan.gov.in/...\n\n" +

                        "State Name : " + data.getOrDefault("stateName","-") + "\n" +
                        "Office Name : " + data.getOrDefault("regAuthority","-") + "\n" +

                        "Vehicle Registration Number : " + data.getOrDefault("regNo","-") + "\n" +
                        "Chassis Number : " + data.getOrDefault("chassis","-") + "\n" +
                        "Engine Number : " + data.getOrDefault("engine","-") + "\n" +

                        "Seating Capacity : " + data.getOrDefault("vehicleSeatCapacity","-") + "\n" +
                        "Standing Capacity : " + data.getOrDefault("vehicleStandingCapacity","0") + "\n" +
                        "Sleeper Capacity: " + data.getOrDefault("vehicleSleeperCapacity","0") + "\n" +

                        "Owner Name: " + data.getOrDefault("owner","-") + "\n" +
                        "Father's Name: " + data.getOrDefault("ownerFatherName","-") + "\n" +

                        "Laden Weight : " + data.getOrDefault("grossVehicleWeight","-") + "\n" +
                        "Unladen Weight : " + data.getOrDefault("unladenWeight","-") + "\n" +

                        "Registration From Date: " + data.getOrDefault("regDate","-") + "\n" +
                        "Registration Upto Date: " + data.getOrDefault("rcExpiryDate","-") + "\n" +

                        "Address: " + data.getOrDefault("presentAddress","-");

        String qrData =
                "data:text/plain;charset=utf-8," +
                        java.net.URLEncoder.encode(text, java.nio.charset.StandardCharsets.UTF_8);

        String base64QR =
                com.fastag.backend_services.component.QRCodeGenerator.generateBase64QRCode(qrData);

        data.put("qrCode", base64QR);

        return ResponseEntity.ok(apiResponse);
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
