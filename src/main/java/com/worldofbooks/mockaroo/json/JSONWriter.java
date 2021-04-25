package com.worldofbooks.mockaroo.json;

import com.worldofbooks.mockaroo.model.Report;
import com.worldofbooks.mockaroo.service.ReportProvider;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;

@Service
public class JSONWriter {

    @Autowired
    ReportProvider reportProvider;


    public void createJSONFile() throws IOException {
        Report report = reportProvider.getReport();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Total listing count:", report.getTotalListingCount());
        jsonObject.put("Total eBay listing count:", report.getTotalEbayListingCount());
        jsonObject.put("Total eBay listing price:", report.getTotalEbayListingPrice());
        jsonObject.put("Average eBay listing price:", report.getAvgEbayListingPrice());
        jsonObject.put("Total Amazon listing count:", report.getTotalAmazonListingCount());
        jsonObject.put("Total Amazon listing price:", report.getTotalAmazonListingPrice());
        jsonObject.put("Average Amazon listing price:", report.getAvgAmazonListingPrice());
        jsonObject.put("Best lister email address", report.getBestListerEmailAddress());

        FileWriter jsonFile = new FileWriter("report.json");
        jsonFile.write(jsonObject.toString());
        jsonFile.close();
    }
}
