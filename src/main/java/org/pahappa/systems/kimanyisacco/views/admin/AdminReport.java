package org.pahappa.systems.kimanyisacco.views.admin;

import java.util.*;
import java.io.*;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.pie.PieChartDataSet;
import org.primefaces.model.charts.pie.PieChartModel;

import org.pahappa.systems.kimanyisacco.services.TransactionService;
import org.pahappa.systems.kimanyisacco.services.implement.TransactionServiceImpl;
import org.pahappa.systems.kimanyisacco.constants.TransactionStatus;
import org.pahappa.systems.kimanyisacco.constants.TransactionType;
import org.pahappa.systems.kimanyisacco.models.Transaction;

@ManagedBean(name="adminReport")
@RequestScoped
public class AdminReport implements Serializable{
    private PieChartModel pieModel;
    TransactionService transactionService = new TransactionServiceImpl();

    public PieChartModel getPieModel() {
        return pieModel;
    }

    public void setPieModel(PieChartModel pieModel) {
        this.pieModel = pieModel;
    }

    @PostConstruct
    public void init() {
        createPieModel();
    }
    
     private void createPieModel() {
        pieModel = new PieChartModel();
        ChartData data = new ChartData();

        PieChartDataSet dataSet = new PieChartDataSet();
        List<Transaction> values = transactionService.getAllTransactions();
        int sum3 = 0;
        int sum4 = 0;

        for(Transaction value: values){
            if(value.getTransactionType().equals(TransactionType.DEPOSIT)){
                sum3 += value.getAmount();
            }
        }

        for(Transaction w_value:values){
            if(w_value.getTransactionType().equals(TransactionType.WITHDRAW) && w_value.getTransactionStatus().equals(TransactionStatus.APPROVED)){
                sum4 += w_value.getAmount();
            }
        }

        List<Number> lists = new ArrayList<>();

        lists.add(sum3);
        lists.add(sum4);
        // values.add(100);
        dataSet.setData(lists);

        List<String> bgColors = new ArrayList<>();
        bgColors.add("rgb(255, 99, 132)");
        bgColors.add("rgb(54, 162, 235)");
        // bgColors.add("rgb(255, 205, 86)");
        dataSet.setBackgroundColor(bgColors);

        data.addChartDataSet(dataSet);
        List<String> labels = new ArrayList<>();
        labels.add("DEPOSIT");
        labels.add("WITHDRAWS");
        // labels.add("Yellow");
        data.setLabels(labels);
        pieModel.setData(data);
    }
}
