package server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.data.DTOs.ConversationTO;
import server.data.DTOs.CurrentConversationTO;
import server.data.DTOs.DHRequestTO;
import server.services.ConversationService;
import server.services.SecurityService;
import server.services.UserService;

@RestController
@RequestMapping("/call")
public class ConversationController {

    @Autowired
    private ConversationService conversationService;

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    /*
    Someone who wants to begin conversation with someone else starts here DH (gives p, g, A and list of people who he wants to call)
    */
    @PostMapping(value = "/calling")
    public ResponseEntity<ConversationTO> startConversation(
            @RequestParam("userID") String userID,
            @RequestHeader("token") String token,
            @RequestBody DHRequestTO dhRequestTO
    ) {
        if (securityService.isTokenValid(userID, token)) {
            ConversationTO conversationTO = conversationService.handleConversationRequest(userID, dhRequestTO);
            return ResponseEntity.ok(conversationTO);
        } else {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping(value = "/beingCalled")
    public ResponseEntity<ConversationTO> addToConversation(
            @RequestParam("userID") String userID,
            @RequestHeader("token") String token,
            @RequestBody DHRequestTO dhRequestTO
    ) {
        if (securityService.isTokenValid(userID, token)) {
            ConversationTO conversationTO = conversationService.handleAddToConversationRequest(userID, dhRequestTO);
            return ResponseEntity.ok(conversationTO);
        } else {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping(value = "/hangUp")
    public ResponseEntity<?> hangUp(
            @RequestParam("userID") String userID,
            @RequestParam("conversationID") String conversationID,
            @RequestHeader("token") String token
    ) {
        if (securityService.isTokenValid(userID, token)) {
            conversationService.handleHangUpRequest(userID, conversationID);
            return ResponseEntity.ok().build();
        } else {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping(value = "/currentConversation")
    public ResponseEntity<CurrentConversationTO> getCurrentConversation(
            @RequestParam("userID") String userID,
            @RequestParam("conversationID") String conversationID,
            @RequestHeader("token") String token
    ) {
        if (securityService.isTokenValid(userID, token)) {
            CurrentConversationTO currentConversation = conversationService.getCurrentConversation(conversationID);
            return ResponseEntity.ok(currentConversation);
        } else {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }
}
