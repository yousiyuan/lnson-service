package org.lnson.order.service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.lnson.service.common.JsonUtils;
import org.springframework.context.annotation.Bean;

public class JacksonConfig {

    @Bean(name = "objectMapper")
    public ObjectMapper jacksonObjectMapper() {
        return JsonUtils.getDefaultMapper();
    }

}
