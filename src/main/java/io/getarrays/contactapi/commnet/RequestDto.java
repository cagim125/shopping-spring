package io.getarrays.contactapi.commnet;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RequestDto {
    private Long userId;
    private Long productId;
    private String review;

}
