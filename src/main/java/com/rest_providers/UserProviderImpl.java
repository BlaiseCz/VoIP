package com.rest_providers;

import com.google.common.collect.Lists;
import com.gui.components.MainController;
import com.models.UserTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Component
public class UserProviderImpl {
    final String url = "https://server-voip.herokuapp.com/users";
    private final RestTemplate restTemplate = RestTemplateConfiguration.restTemplate();

    public List<UserTO> getAllUsers(String token) {
        String endpointUrl = url + "/all";
        UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromHttpUrl(endpointUrl)
                                                              .queryParam("userID",
                                                                          MainController.getUserMe()
                                                                                        .getUserID());

        HttpHeaders headers = new HttpHeaders();
        headers.set("token", token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        List<UserTO> users = Lists.newArrayList(restTemplate.exchange(urlBuilder.toUriString(),
                                                                      HttpMethod.GET,
                                                                      entity,
                                                                      UserTO[].class)
                                                            .getBody());
        return users;
    }
}
