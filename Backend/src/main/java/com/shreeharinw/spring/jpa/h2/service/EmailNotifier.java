package com.shreeharinw.spring.jpa.h2.service;

import com.shreeharinw.spring.jpa.h2.interfaces.Notifier;
import com.shreeharinw.spring.jpa.h2.model.NonProfit;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Component
public class EmailNotifier implements Notifier {

    @Override
    public void notify(List<NonProfit> nonProfits) {
        for(NonProfit nonProfitObj: nonProfits){
            System.out.println("** Sending email **");
            System.out.println("Email body:");
            System.out.println("Sending money to nonprofit " + nonProfitObj.getName() + " at address " + nonProfitObj.getAddress());
            System.out.println("** Email sent successfully! **");
        }
    }

    @Override
    public void notify(List<NonProfit> nonProfits, String message) {
        Map<String, Function<NonProfit, String>> placeholders = new HashMap<>();
        placeholders.put("{non_profit_name}", NonProfit::getName);
        placeholders.put("{non_profit_address}", NonProfit::getAddress);

        for(NonProfit nonProfitObj: nonProfits){
            String personalizedMessage = replacePlaceholders(message,nonProfitObj,placeholders);
            System.out.println("** Sending email **");
            System.out.println("Email body:");
            System.out.println(personalizedMessage);
            System.out.println("** Email sent successfully! **");
        }
    }

    public static String replacePlaceholders(String message, NonProfit nonProfitObj, Map<String, Function<NonProfit, String>> placeholders) {
        for (Map.Entry<String, Function<NonProfit, String>> entry : placeholders.entrySet()) {
            String placeholder = entry.getKey();
            String value = entry.getValue().apply(nonProfitObj);
            message = message.replace(placeholder, value);
        }
        return message;
    }

    /*
*  Client will provide a template
*
*   Hi {non_profit_name},
*   We are interested in you, is this your {non_profit_address}?
*
*  Server will send email as per the template
*
*
* */
}
