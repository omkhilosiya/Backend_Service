package com.fastag.backend_services.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

@Service
public class RcPdfService {

    public byte[] generateRcPdf(Map<String, Object> apiResponse) throws Exception {

        // Extract "result.data"
        Map<String, Object> result = (Map<String, Object>) apiResponse.get("result");
        Map<String, Object> data = (Map<String, Object>) result.get("data");

        // Load HTML template from classpath (works in JAR also)
        String html = new String(
                new ClassPathResource("templates/rc_template.html")
                        .getInputStream()
                        .readAllBytes(),
                StandardCharsets.UTF_8
        );

        // 🔥 Remove BOM / hidden junk characters BEFORE <html>
        html = html.replace("\uFEFF", "").trim();

        // Replace placeholders
        for (String key : data.keySet()) {
            Object value = data.get(key);
            html = html.replace("{{" + key + "}}", value == null ? "-" : value.toString());
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        // --------- IMPORTANT BASE URI ----------
        String baseUri = Objects.requireNonNull(
                getClass().getClassLoader().getResource("static/")
        ).toString();

        com.openhtmltopdf.pdfboxout.PdfRendererBuilder builder =
                new com.openhtmltopdf.pdfboxout.PdfRendererBuilder();

        builder.withHtmlContent(html, baseUri);
        builder.toStream(out);
        builder.run();

        return out.toByteArray();
    }
}
