package org.app.findcarespringboot.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiResponseDto {
    private String status;
    private String message;
    private Object data;
}
