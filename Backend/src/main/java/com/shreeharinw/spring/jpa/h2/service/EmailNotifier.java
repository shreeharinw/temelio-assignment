package com.shreeharinw.spring.jpa.h2.service;

import com.shreeharinw.spring.jpa.h2.interfaces.Notifier;
import com.shreeharinw.spring.jpa.h2.model.NonProfit;
import org.springframework.stereotype.Component;

import java.util.List;

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
}
