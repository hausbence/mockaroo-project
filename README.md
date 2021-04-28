# Mockaroo

## Description
  In this project I synchronized data from Mockaroo's API, validated and stored it in a database. <br/>
  The program collects invalid lines (null values) and collects them to a CSV file. <br/>
  It also creates a report and writes it to a JSON file, which is being uploaded to an FTP server automatically.
 
## Used
 - Java 8
 - Spring boot
 - JPA
 - PostgresSQL

 
## Usage
Before you use it, the FTP and Database credentials need to be defined in environment variables
 
## API
 [Mockaroo](https://www.mockaroo.com)
