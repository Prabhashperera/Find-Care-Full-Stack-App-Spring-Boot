package org.app.findcarespringboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterPostsDto {
    private String petType;
    private String district;
    private String city;
    private String status;
}
