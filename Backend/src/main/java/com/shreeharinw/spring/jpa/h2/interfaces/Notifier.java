package com.shreeharinw.spring.jpa.h2.interfaces;

import com.shreeharinw.spring.jpa.h2.model.EmailData;
import com.shreeharinw.spring.jpa.h2.model.NonProfit;

import java.util.List;

public interface Notifier {
    void notify(List<NonProfit> nonProfits);

    void notify(List<NonProfit> nonProfits, String message);
}
