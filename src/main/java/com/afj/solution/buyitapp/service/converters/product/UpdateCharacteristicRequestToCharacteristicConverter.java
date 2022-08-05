package com.afj.solution.buyitapp.service.converters.product;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.afj.solution.buyitapp.model.Characteristic;
import com.afj.solution.buyitapp.payload.request.UpdateCharacteristicRequest;
import com.afj.solution.buyitapp.service.converters.Converter;

/**
 * @author Tomash Gombosh
 */
@Component
@Slf4j
public class UpdateCharacteristicRequestToCharacteristicConverter
        implements Converter<UpdateCharacteristicRequest, Characteristic> {

    @Override
    public Characteristic convert(final UpdateCharacteristicRequest updateCharacteristicRequest) {
        log.info("Convert the request ({}) to characteristic", updateCharacteristicRequest);
        return new Characteristic(ch -> {
            ch.setColor(updateCharacteristicRequest.getColor());
            ch.setSize(updateCharacteristicRequest.getSize());
            ch.setAdditionalParams(updateCharacteristicRequest.getAdditionalParams());
        });
    }
}
