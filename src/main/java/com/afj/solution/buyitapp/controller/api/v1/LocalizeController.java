package com.afj.solution.buyitapp.controller.api.v1;

import java.util.Map;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.afj.solution.buyitapp.service.localize.TranslatorService;

/**
 * @author Tomash Gombosh
 */
@Slf4j
@RestController
@RequestMapping(path = "/api/v1", produces = "application/json; charset=utf-8")
public class LocalizeController {

    private final TranslatorService translatorService;

    @Autowired
    public LocalizeController(final TranslatorService translatorService) {
        this.translatorService = translatorService;
    }

    @ApiOperation("Get localize config")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Localize loaded successfully"),
            @ApiResponse(code = 500, message = "Internal server error"),
    })
    @GetMapping("/localize")
    public @ResponseBody
    Map<String, Object> localize() {
        log.info("Get localize request");
        return translatorService.getLocalize();
    }
}
