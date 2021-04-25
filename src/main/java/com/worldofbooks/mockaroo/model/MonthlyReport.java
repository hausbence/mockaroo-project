package com.worldofbooks.mockaroo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@AllArgsConstructor
@Builder
@Data
public class MonthlyReport {

    private Date date;

    private MonthlyData monthlyData;

}
