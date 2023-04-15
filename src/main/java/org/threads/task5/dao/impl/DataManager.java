package org.threads.task5.dao.impl;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.threads.task5.models.AccountBalance;
import org.threads.task5.models.Currency;
import org.threads.task5.models.ExchangeRate;
import org.threads.task5.models.UserAccount;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataManager {

   private String filePathRate = "C:\\Users\\annak\\IdeaProjects\\multithreading\\src\\main\\java\\org\\threads\\task5\\data\\rate\\";
   private String filePathUser="C:\\Users\\annak\\IdeaProjects\\multithreading\\src\\main\\java\\org\\threads\\task5\\data\\user\\";
    private static final Logger logger= Logger.getLogger(DataManager.class.getName());
    ExchangeRate parseExchangeFile(String fileName) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(filePathRate + fileName));
        JSONObject jsonObject = (JSONObject) obj;
        return new ExchangeRate(Currency.valueOf((String) jsonObject.get("from")),
                Currency.valueOf((String) jsonObject.get("to")),
                BigDecimal.valueOf ((double)jsonObject.get("rate")),
                LocalDate.parse((String) jsonObject.get("date")));
    }


    UserAccount parseUserFile(String fileName) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(filePathUser + fileName));
        JSONObject jsonObject = (JSONObject) obj;
        JSONArray accountBalances = (JSONArray) jsonObject.get("accountBalances");
        Iterator iter = accountBalances.iterator();
        List<AccountBalance> balanceList = new ArrayList<>();
        while (iter.hasNext()) {
            JSONObject object = (JSONObject) iter.next();
            AccountBalance bal = new AccountBalance((long) jsonObject.get("userAccountId"),
                                                    Currency.valueOf((String)object.get("currency")),
                                                    BigDecimal.valueOf((Double) object.get("balance")));
            balanceList.add(bal);
        }
        return new UserAccount((long) jsonObject.get("userAccountId"),
                               (String) jsonObject.get("firstName"),
                               (String) jsonObject.get("lastName"),
                               (String) jsonObject.get("accountNumber"),
                               balanceList);
    }


    public void writeNewBalance(UserAccount account) throws IOException, ParseException {
        BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePathUser+account.getFirstName()+".json"));
        writer.write("");
        writer.flush();
        JSONObject jsonObject=new JSONObject();
        JSONArray balancesArray=new JSONArray();
        for (AccountBalance values:account.getAccountBalances()) {
            JSONObject obj=new JSONObject();
            obj.put("userAccountId",values.getUserAccountId());
            obj.put("currency",values.getCurrency().toString());
            obj.put("balance",values.getBalance());
            balancesArray.add(obj);
        }
        jsonObject.put("userAccountId",account.getUserAccountId());
        jsonObject.put("firstName",account.getFirstName());
        jsonObject.put("lastName",account.getLastName());
        jsonObject.put("accountNumber",account.getAccountNumber());
        jsonObject.put("accountBalances",balancesArray);
        writer.write(jsonObject.toJSONString());
        logger.log(Level.INFO,"balance has been updated in users account "+jsonObject);
        writer.close();
    }

}
