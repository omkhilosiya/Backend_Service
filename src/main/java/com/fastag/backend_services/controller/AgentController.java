package com.fastag.backend_services.controller;

import com.fastag.backend_services.dto.AgentResponse;
import com.fastag.backend_services.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AgentController {

    @Autowired
    private AgentService agentService;

    @GetMapping("/agent")
    public ResponseEntity<?> getClients(@RequestParam(required = false) String search) {
        return ResponseEntity.ok(agentService.getAgents(search));
    }

}



