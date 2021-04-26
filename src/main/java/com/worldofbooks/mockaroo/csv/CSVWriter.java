package com.worldofbooks.mockaroo.csv;

import com.worldofbooks.mockaroo.model.InvalidListingObject;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
public class CSVWriter {

    String[] HEADERS = { "ListingId", "MarketplaceName", "InvalidField"};

    public void createCSVFile(List<InvalidListingObject> invalidListingObjects) throws IOException {
        String fileName = "importLog.csv";
        FileWriter out = new FileWriter("src/main/resources/csv/" + fileName);
        try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(HEADERS))) {
            for(InvalidListingObject invalidListingObject : invalidListingObjects)
                printer.printRecord(invalidListingObject.getListingId(), invalidListingObject.getMarketPlaceName(), invalidListingObject.getInvalidFieldName());
        }
    }

}
