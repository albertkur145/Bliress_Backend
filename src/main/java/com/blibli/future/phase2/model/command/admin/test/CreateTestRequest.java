package com.blibli.future.phase2.model.command.admin.test;

import com.blibli.future.phase2.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateTestRequest {
     private String batchId;

     private String training;

     private String materialId;

     @Pattern(regexp = "^[0-3]\\d-[0-1]\\d-\\d\\d\\d\\d$", message = "Invalid Date")
     private String available;

     @Pattern(regexp = "^[0-3]\\d-[0-1]\\d-\\d\\d\\d\\d$", message = "Invalid Date")
     private String closed;

     private Integer timeLimit;

     private Set<Question> questions;
}
