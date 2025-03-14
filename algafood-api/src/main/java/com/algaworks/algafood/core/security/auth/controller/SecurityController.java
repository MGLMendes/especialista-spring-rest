package com.algaworks.algafood.core.security.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class SecurityController {

    private final ClientDetailsService clientDetailsService;

    @GetMapping("/login")
    public  String login() {
        return "pages/login";
    }

    @GetMapping("/oauth/confirm_access")
    public ModelAndView oauth2Approval(@RequestParam("client_id") String clientId) {
        ClientDetails client = clientDetailsService.loadClientByClientId(clientId);
        ModelAndView modelAndView = new ModelAndView("pages/oauth2_approval");
        modelAndView.addObject("scopes", client.getScope());
        return modelAndView;
    }
}
