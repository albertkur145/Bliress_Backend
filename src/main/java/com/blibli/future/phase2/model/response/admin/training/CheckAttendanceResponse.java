package com.blibli.future.phase2.model.response.admin.training;

import com.blibli.future.phase2.entity.TrainingAttendance;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckAttendanceResponse {
    private Set<TrainingAttendance> employeeList;
}
