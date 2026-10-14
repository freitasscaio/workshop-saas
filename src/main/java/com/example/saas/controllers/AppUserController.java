
package com.example.saas.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.saas.dto.AppUserRequestDTO;
import com.example.saas.dto.AppUserResponseDTO;
import com.example.saas.services.AppUserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class AppUserController {

    private final AppUserService appUserService;

    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @PostMapping("/staff")
    public AppUserResponseDTO registerStaff(
            @Valid @RequestBody AppUserRequestDTO dto,
            @AuthenticationPrincipal Jwt jwt) {

        Long workshopId = getWorkshopId(jwt);

        return appUserService.registerStaff(dto, workshopId);
    }

    private Long getWorkshopId(Jwt jwt) {
        Object claim = jwt.getClaim("workshopId");

        if (claim instanceof Number number) {
            return number.longValue();
        }

        if (claim instanceof String value) {
            return Long.parseLong(value);
        }

        throw new IllegalStateException(
                "O token não contém um workshopId válido.");
    }
}
