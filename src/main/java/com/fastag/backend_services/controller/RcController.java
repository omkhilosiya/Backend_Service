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

        // RC API THE FIRST API PROVIDED BY THE VENDOR
        @PostMapping("/rc/verify")
        public String verify(@RequestBody RcVerifyPaymentRequest rcVerifyPaymentRequest) {
            return rcService.verifyRc(rcVerifyPaymentRequest);
        }

        @PostMapping("/download")
        public ResponseEntity<byte[]> downloadRC(@RequestBody Map<String, Object> rcData) throws Exception {

            byte[] pdfBytes = rcPdfService.generateRcPdf(rcData);

            return ResponseEntity.ok()
                    .header("Content-Type", "application/pdf")
                    .header("Content-Disposition", "attachment; filename=RC.pdf")
                    .body(pdfBytes);
        }
    }
