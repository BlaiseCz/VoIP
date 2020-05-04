package com.models;

import lombok.*;

import java.util.List;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserTO {
    private String username;
    private String email;
    private List<UserShortDAO> favourites;
    private String IPAddress;

    public static UserTO map(UserDAO userDAO) {
        return UserTO.builder()
                     .username(userDAO.getUsername())
                     .email(userDAO.getEmail())
                     .favourites(userDAO.getFavourites())
                     .IPAddress(userDAO.getIPAddress())
                     .build();
    }
}