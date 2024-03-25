package com.bcn.donorService.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CsvHandler {

    public static void validateCsvDonor(Map<String, String> row) throws Exception {
        validateCsvHeader(row.keySet().toArray(new String[0]));
    }

    public static void validateCsvHeader(String[] headers) throws Exception {
        List<String> requiredHeaders = Arrays.asList(
                "donorNic", "firstName", "lastName", "bloodType", "birthday", "streetNo",
                "street", "city", "contactNo", "emergencyContactNo", "weight", "gender", "units");
        for (String requiredHeader : requiredHeaders) {
            boolean found = false;
            for (String header : headers) {
                header = header.replaceAll("^\\W+", "");
                header = header.trim();
                System.out.println("current header : " + header);
                if (requiredHeader.equals(header)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                throw new Exception("Missing required header: " + requiredHeader);
            }
        }
    }

}
