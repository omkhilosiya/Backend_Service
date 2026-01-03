// ======================= RcPdfService =======================
package com.fastag.backend_services.service;

import com.fastag.backend_services.dto.RcTemplateBuilder;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import java.util.Objects;

@Service
public class RcPdfService {

    public byte[] generateRcPdf(Map<String, Object> apiResponse) throws Exception {

        Map<String, Object> result = (Map<String, Object>) apiResponse.get("result");
        Map<String, Object> data = (Map<String, Object>) result.get("data");

        String html = new String(
                new ClassPathResource("templates/rc_template.html")
                        .getInputStream()
                        .readAllBytes(),
                StandardCharsets.UTF_8
        );

        html = html.replace("\uFEFF", "").trim();

        // ===================== USE QR FROM API-2 =====================
        String qrBase64 = (String) data.get("qrCode");   // <-- already generated QR
        String qrDataUrl = "data:image/png;base64," + qrBase64;
        html = html.replace("{{QR_CODE}}", qrDataUrl);

        // ===================== FILL OTHER DATA ======================
        for (String key : data.keySet()) {
            Object value = data.get(key);
            html = html.replace("{{" + key + "}}", value == null ? "-" : value.toString());
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        String baseUri = Objects.requireNonNull(
                getClass().getClassLoader().getResource("static/")
        ).toString();

        PdfRendererBuilder builder = new PdfRendererBuilder();
        builder.withHtmlContent(html, baseUri);
        builder.toStream(out);
        builder.run();

        return out.toByteArray();
    }

    public byte[] generateVehicleSmartCard(Map<String, Object> apiResponse) throws Exception {

        Map<String, Object> result = (Map<String, Object>) apiResponse.get("result");
        Map<String, Object> data = (Map<String, Object>) result.get("data");

        // Load HTML Template
        String html = new String(
                new ClassPathResource("templates/rc_card.html")
                        .getInputStream()
                        .readAllBytes(),
                StandardCharsets.UTF_8
        );

        html = html.replace("\uFEFF", "").trim();

        // ---------------- QR CODE ----------------
        String qrBase64 = (String) data.get("qrCode");
        if(qrBase64 != null){
            String qrDataUrl = "data:image/png;base64," + qrBase64;
            html = html.replace("{{QR_CODE}}", qrDataUrl);
        }

        // ---------------- SAFETY HTML ESCAPE ----------------
        for (String key : data.keySet()) {

            Object value = data.get(key);

            String safeValue = value == null ? "-" :
                    value.toString()
                            .replace("&", "&amp;")
                            .replace("<", "&lt;")
                            .replace(">", "&gt;")
                            .replace("\"", "&quot;")
                            .replace("'", "&#39;");

            html = html.replace("{{" + key + "}}", safeValue);
        }

        // ----------- Extra Safe Optional Fields ----------
        html = html.replace("{{horsePower}}",
                data.getOrDefault("horsePower","-").toString());

        html = html.replace("{{wheelbase}}",
                data.getOrDefault("wheelbase","-").toString());

        // ================== PDF BUILD ==================
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        String baseUri = Objects.requireNonNull(
                getClass().getClassLoader().getResource("static/")
        ).toString();

        PdfRendererBuilder builder = new PdfRendererBuilder();
        builder.withHtmlContent(html, baseUri);
        builder.toStream(out);
        builder.run();

        return out.toByteArray();
    }
}
