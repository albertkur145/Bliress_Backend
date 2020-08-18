package com.blibli.future.phase2.model.response.admin.trainer;

import com.blibli.future.phase2.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetAllTrainerResponse {
    Set<User> trainerList;
}
