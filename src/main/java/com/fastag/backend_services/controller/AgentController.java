package com.fastag.backend_services.controller;

import com.fastag.backend_services.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
public class AgentController {

    @Autowired
    private AgentService agentService;


    //GET ALL TEH AGENTS AND SUBAGENTS WHETHER THOSE ACTIVE OR INACTIVE  FROM THE DATABASE
    @GetMapping("/agent")
    public ResponseEntity<?> getClients(@RequestParam(required = false) String search) {
        return ResponseEntity.ok(agentService.getAgents(search));
    }

}



