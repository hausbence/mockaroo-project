package com.worldofbooks.mockaroo.json;

import com.worldofbooks.mockaroo.ftp.FtpClient;
import com.worldofbooks.mockaroo.model.Report;
import com.worldofbooks.mockaroo.service.ReportProvider;
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

        String fileName = "report.json";
        FileWriter jsonFile = new FileWriter("src/main/resources/ftp/" + fileName);
        jsonFile.write(jsonObject.toString());
        jsonFile.close();

        uploadToFtp(fileName);
    }

    private void uploadToFtp(String fileName) throws IOException {
        FtpClient ftpClient = new FtpClient("192.168.0.22", 21, ftpUser, ftpPassword);
        ftpClient.open();
        ftpClient.putFileToPath("src/main/resources/ftp/" + fileName, "/" + fileName);
    }
}
