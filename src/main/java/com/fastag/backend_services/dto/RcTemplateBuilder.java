package com.fastag.backend_services.dto;

import java.util.Map;

public class RcTemplateBuilder {

    private static String escape(Object value){
        if(value == null) return "";
        return value.toString()
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"","&quot;")
                .replace("'","&apos;");
    }

    public static String buildHtml(Map<String, Object> d) {

        String qrBase64 = (String) d.get("qrCode");
        String signBase64 = (String) d.getOrDefault("sign", "");
        String emblemBase64 = (String) d.getOrDefault("emblem", "");

        return """
        <html>
        <head>
        <meta charset='UTF-8'/>
        <style>
            @page { size: A4; margin: 12mm; }
            body { font-family: Arial; font-size: 12px; color:#0b2240; }

            .rc-card {
                border:1px solid #cbd6df;
                background: linear-gradient(#f8fdff,#eaf5fb 40%%,#eaf5fb);
                padding:10px;
            }

            .top-strip{
                display:flex;align-items:center;gap:10px;
                padding:8px;border:1px solid rgba(75,157,214,.2);
                background:linear-gradient(90deg,#d8f0fb,#f2fbff);
                border-radius:6px;
            }

            .emblem{
                width:60px;height:60px;
                background:url('data:image/png;base64,%s') no-repeat center/contain;
            }

            .title-block{flex:1;text-align:center;}
            .title-block h1{margin:0;font-size:16px;}
            .title-block p{margin:2px 0 0;font-size:11px;}

            .badge{width:30px;height:30px;border-radius:50%%;display:flex;
                align-items:center;justify-content:center;font-weight:bold;color:white}
            .t{background:#5cc0d6;}
            .rj{background:#f0a03a;}

            .row{display:flex;gap:10px;margin:4px 0;}
            .label{width:160px;font-weight:bold;}
            .value{flex:1;}

            .qr-box{
                width:240px;height:240px;border:1px solid #cfdce6;
                display:flex;flex-direction:column;align-items:center;justify-content:center;
                background:white;
            }

            .band{
                margin-top:8px;
                background:linear-gradient(90deg,#e3f3fb,#f6fbff);
                border-top:2px solid rgba(75,157,214,.2);
                border-bottom:2px solid rgba(75,157,214,.2);
                padding:6px;
                font-weight:bold;
            }

            .lower{display:grid;grid-template-columns:240px 1fr;gap:10px;}
            .maker-box{border:1px solid rgba(11,59,85,.1);padding:8px;background:white;}

            .spec{margin-top:4px;}
            .spec strong{display:block;}

            .sign-area{text-align:right;}
            .sign-img{width:120px;height:40px;object-fit:contain;}
        </style>
        </head>

        <body>
        <div class='rc-card'>

            <div class='top-strip'>
                <div class='emblem'></div>

                <div class='title-block'>
                    <h1>Indian Union Vehicle Registration Certificate</h1>
                    <p>Issued by GOVERNMENT OF HARYANA</p>
                </div>

                <div style='display:flex;gap:6px'>
                    <div class='badge t'>T</div>
                    <div class='badge rj'>RJ</div>
                </div>
            </div>

            <div style='margin-top:10px;'>

                <div class='row'><div class='label'>Regn No</div><div class='value'>%s</div></div>
                <div class='row'><div class='label'>Owner</div><div class='value'>%s</div></div>
                <div class='row'><div class='label'>Father Name</div><div class='value'>%s</div></div>
                <div class='row'><div class='label'>Chassis</div><div class='value'>%s</div></div>
                <div class='row'><div class='label'>Engine</div><div class='value'>%s</div></div>
                <div class='row'><div class='label'>Fuel</div><div class='value'>%s</div></div>
                <div class='row'><div class='label'>Vehicle Class</div><div class='value'>%s</div></div>
                <div class='row'><div class='label'>Manufacturer</div><div class='value'>%s</div></div>
                <div class='row'><div class='label'>Model</div><div class='value'>%s</div></div>
                <div class='row'><div class='label'>Registration Date</div><div class='value'>%s</div></div>
                <div class='row'><div class='label'>RC Valid Upto</div><div class='value'>%s</div></div>
                <div class='row'><div class='label'>Address</div><div class='value'>%s</div></div>
            </div>

            <div class='band'>Vehicle Class: %s</div>

            <div class='lower'>

                <div class='qr-box'>
                    <img src='data:image/png;base64,%s' width='200' height='200'/>
                    <div>Scan to Verify</div>
                </div>

                <div class='maker-box'>
                    <div class='spec'><strong>Unladen Weight</strong>%s</div>
                    <div class='spec'><strong>Gross Weight</strong>%s</div>
                    <div class='spec'><strong>No of Cylinders</strong>%s</div>

                    <div class='sign-area'>
                        <img class='sign-img' src='data:image/png;base64,%s'/>
                        <div><b>Registration Authority</b></div>
                        <div>%s</div>
                    </div>
                </div>

            </div>

        </div>
        </body>
        </html>
        """
                .formatted(
                        emblemBase64,

                        escape(d.get("regNo")),
                        escape(d.get("owner")),
                        escape(d.getOrDefault("ownerFatherName","-")),
                        escape(d.get("chassis")),
                        escape(d.get("engine")),
                        escape(d.get("type")),
                        escape(d.get("class")),
                        escape(d.get("vehicleManufacturerName")),
                        escape(d.get("model")),
                        escape(d.get("regDate")),
                        escape(d.get("rcExpiryDate")),
                        escape(d.get("presentAddress")),

                        escape(d.get("class")),          // band text

                        qrBase64,                        // QR

                        escape(d.get("unladenWeight")),
                        escape(d.get("grossVehicleWeight")),
                        escape(d.get("vehicleCylindersNo")),

                        signBase64,
                        escape(d.getOrDefault("regAuthority",""))
                );
    }
}
