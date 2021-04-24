package com.worldofbooks.mockaroo.csv;

import com.worldofbooks.mockaroo.model.InvalidObject;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
public class CSVWriter {

    String[] HEADERS = { "ListingId", "MarketplaceName", "InvalidField"};

    public void createCSVFile(List<InvalidObject> invalidObjects) throws IOException {
        FileWriter out = new FileWriter("importLog.csv");
        try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(HEADERS))) {
            for(InvalidObject invalidObject : invalidObjects)
                printer.printRecord(invalidObject.getListingId(), invalidObject.getMarketPlaceName(), invalidObject.getInvalidFieldName());
        }
    }

}
