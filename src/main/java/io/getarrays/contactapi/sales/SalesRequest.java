package io.getarrays.contactapi.sales;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class SalesRequest {

    private String itemName;
    private Integer price;
    private Integer count;
    private Long userId;
}
