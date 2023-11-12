package com.qwerty.tokenbucket.model;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class CacheData {

    Integer tokens;

    Long timestamp;
}
