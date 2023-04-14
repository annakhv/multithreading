package org.threads.task5.dao.impl;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.threads.task5.models.AccountBalance;
import org.threads.task5.models.Currency;
import org.threads.task5.models.ExchangeRate;
import org.threads.task5.models.UserAccount;

public class DataParser {

    String filePathRate = "C:\\Users\\Ana_Khvedelidze\\IdeaProjects\\multithreading\\src\\main\\java\\org\\threads\\task5\\data\\rate\\";
    String filePathUser="C:\\Users\\Ana_Khvedelidze\\IdeaProjects\\multithreading\\src\\main\\java\\org\\threads\\task5\\data\\user\\";

    ExchangeRate parseExchangeFile(String fileName) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(filePathRate + fileName));
        JSONObject jsonObject = (JSONObject) obj;
        return new ExchangeRate((Currency) jsonObject.get("from"), (Currency) jsonObject.get("to"), (BigDecimal) jsonObject.get("rate"), (LocalDate) jsonObject.get("date"));
    }


    UserAccount parseUserFile(String fileName) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(filePathUser + fileName));
        JSONObject jsonObject = (JSONObject) obj;
        JSONArray accountBalances = (JSONArray) jsonObject.get("accountNumber");
        Iterator iter = accountBalances.iterator();
        List<AccountBalance> balanceList = new ArrayList<>();
        while (iter.hasNext()) {
            JSONObject object = (JSONObject) iter.next();
            AccountBalance bal = new AccountBalance((long) jsonObject.get("userAccountId"),
                                                    (Currency) object.get("currency"),
                                                    (BigDecimal) object.get("balance"));
            balanceList.add(bal);
        }
        return new UserAccount((long) jsonObject.get("userAccountId"),
                               (String) jsonObject.get("firstName"),
                               (String) jsonObject.get("lastName"),
                               (String) jsonObject.get("accountNumber"),
                               balanceList);
    }
}
