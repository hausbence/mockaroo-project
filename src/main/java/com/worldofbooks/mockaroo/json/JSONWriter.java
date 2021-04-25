package com.worldofbooks.mockaroo.json;

import com.worldofbooks.mockaroo.ftp.FtpClient;
import com.worldofbooks.mockaroo.model.Report;
import com.worldofbooks.mockaroo.service.ReportProvider;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;

@Service
public class JSONWriter {

    @Autowired
    ReportProvider reportProvider;

    @Value("${ftp.username}")
    String ftpUser;

    @Value("${ftp.password}")
    String ftpPassword;


    public String getJsonFile() throws IOException {
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

        JSONArray array = new JSONArray();
        addMonthlyEbayReport(report, array);
        addMonthlyAmazonReport(report,array);
        jsonObject.put("Monthly reports: ", array);


        String fileName = "report.json";
        FileWriter jsonFile = new FileWriter("src/main/resources/ftp/" + fileName);
        jsonFile.write(jsonObject.toString());
        jsonFile.close();

        return fileName;
    }

    private void addMonthlyEbayReport(Report report, JSONArray array) {
        report.getMonthlyEbayReports().forEach(object -> {
            Object[] objectArray = (Object[]) object;
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("Date", objectArray[0]);
            jsonObject1.put("Total eBay listing count", objectArray[1]);
            jsonObject1.put("Total eBay listing price", objectArray[2]);
            jsonObject1.put("Average eBay listing price", objectArray[3]);
            array.put(jsonObject1);
        });
    }

    private void addMonthlyAmazonReport(Report report, JSONArray array) {
        report.getMonthlyAmazonReports().forEach(object -> {
            Object[] objectArray = (Object[]) object;
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("Date", objectArray[0]);
            jsonObject1.put("Total Amazon listing count", objectArray[1]);
            jsonObject1.put("Total Amazon listing price", objectArray[2]);
            jsonObject1.put("Average Amazon listing price", objectArray[3]);
            array.put(jsonObject1);
        });
    }

    public void uploadToFtp(String fileName) throws IOException {
        FtpClient ftpClient = new FtpClient("192.168.0.22", 21, ftpUser, ftpPassword);
        ftpClient.open();
        ftpClient.putFileToPath("src/main/resources/ftp/" + fileName, "/" + fileName);
    }
}
