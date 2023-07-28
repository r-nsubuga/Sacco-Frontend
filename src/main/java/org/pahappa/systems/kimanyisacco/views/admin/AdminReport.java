package org.pahappa.systems.kimanyisacco.views.admin;

import java.util.*;
import java.io.*;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.line.LineChartModel;
import org.primefaces.model.charts.line.LineChartOptions;
import org.primefaces.model.charts.optionconfig.title.Title;
import org.primefaces.model.charts.pie.PieChartDataSet;
import org.primefaces.model.charts.pie.PieChartModel;

import org.pahappa.systems.kimanyisacco.services.implement.TransactionImpl;
import org.pahappa.systems.kimanyisacco.models.Transactions;

@ManagedBean(name="adminReport")
@RequestScoped
public class AdminReport implements Serializable{
    private LineChartModel lineModel;
    private PieChartModel pieModel;
    TransactionImpl transact = new TransactionImpl();

    public PieChartModel getPieModel() {
        return pieModel;
    }

    public void setPieModel(PieChartModel pieModel) {
        this.pieModel = pieModel;
    }

    public LineChartModel getLineModel() {
        return lineModel;
    }

    public void setLineModel(LineChartModel lineModel) {
        this.lineModel = lineModel;
    }

    @PostConstruct
    public void init() {
        createLineModel();
        createPieModel();
    }

    public void createLineModel() {
        lineModel = new LineChartModel();
        ChartData data = new ChartData();

        LineChartDataSet depositDataSet = new LineChartDataSet();
        LineChartDataSet withdrawDataSet = new LineChartDataSet();
        List<Transactions> values = transact.getAllTransactions();

        List<Object> deposit_list = new ArrayList<>();
        List<Object> withdraw_list = new ArrayList<>();

        for(Transactions value: values){
            if(value.getTransactionType().equals("DEPOSIT")){
                deposit_list.add(value.getAmount());
            }
            else if(value.getTransactionType().equals("WITHDRAW") && value.getStatus() == 1){
                withdraw_list.add(value.getAmount());
            }
        }

        depositDataSet.setData(deposit_list);
        depositDataSet.setFill(false);
        depositDataSet.setLabel("Deposits");
        depositDataSet.setBorderColor("rgb(255, 99, 132)");
        depositDataSet.setTension(0.1);
        //data.addChartDataSet(depositDataSet);

        withdrawDataSet.setData(deposit_list);
        withdrawDataSet.setFill(false);
        withdrawDataSet.setLabel("Withdraws");
        withdrawDataSet.setBorderColor("rgb(54, 162, 235)");
        withdrawDataSet.setTension(0.1);
        //data.addChartDataSet(withdrawDataSet);

// Combining datasets into a single dataset
    List<LineChartDataSet> datasets = new ArrayList<>();
    datasets.add(depositDataSet);
    datasets.add(withdrawDataSet);

    // Adding the combined dataset to the chart data
    // data.setDatasets();

    // Assuming you have labels for each transaction, you can set the labels here.
    // For example, you can have a list of months or transaction dates as labels.
    // List<String> labels = new ArrayList<>();
    // for (Transactions value : values) {
    //     labels.add(value.getTransactionDate());
    // }
    // data.setLabels(labels);

    // // Options
    // LineChartOptions options = new LineChartOptions();
    // Title title = new Title();
    // title.setDisplay(true);
    // title.setText("Line Chart");
    // options.setTitle(title);
    // lineModel.setOptions(options);
    // lineModel.setData(data);
}
    

     private void createPieModel() {
        pieModel = new PieChartModel();
        ChartData data = new ChartData();

        PieChartDataSet dataSet = new PieChartDataSet();
        List<Transactions> values = transact.getAllTransactions();
        int sum3 = 0;
        int sum4 = 0;

        for(Transactions value: values){
            if(value.getTransactionType().equals("DEPOSIT")){
                sum3 += value.getAmount();
            }
        }

        for(Transactions w_value:values){
            if(w_value.getTransactionType().equals("WITHDRAW") && w_value.getStatus() == 1){
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
