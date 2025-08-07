package com.ntu.api.controller.additional;

import com.ntu.api.controller.main.InputController;
import com.ntu.api.domain.Message;
import com.ntu.api.domain.RoadConstraction;
import com.ntu.api.model.RoadConstractionModel;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ActionChooseController {
    @FXML AnchorPane actionChoosePane;

    @FXML private void initialize(){
        RoadConstractionModel.preCalculation();
    }

    @FXML private void analysisOnClick(){
        RoadConstractionModel.setEnumerated(false);
        RoadConstractionModel.analisys(actionChoosePane);

        Stage report = new Stage();
        report.setTitle("Результати розрахунку конструкції дорожнього одягу");
        report.setResizable(false);
        AnchorPane reportPane;
        try {
            reportPane = FXMLLoader.load(getClass().getResource("/com/ntu/api/fx/model/additional/report.fxml"));
            report.initOwner(actionChoosePane.getScene().getWindow());
            report.initModality(Modality.WINDOW_MODAL);
            report.setScene(new Scene(reportPane));
            report.show();
        } catch (IOException e) {
            Message.errorCatch(actionChoosePane, "Error", "Analysis Error");
//            e.printStackTrace();
        }
    }

    @FXML private void editOnClick(){
        cancelOnClick();
        Stage edit = new Stage();
        edit.setTitle("Редагування конструкції дорожнього одягу");
        edit.setResizable(false);
        InputController.setEditBool(true);

        ScrollPane editDlg = null;
        try {
            editDlg = FXMLLoader.load(getClass().getResource("/com/ntu/api/fx/model/main/input.fxml"));
            edit.initModality(Modality.WINDOW_MODAL);
            edit.initOwner(actionChoosePane.getScene().getWindow());
            edit.setScene(new Scene(editDlg));
            edit.show();
        } catch (IOException e) {
            e.printStackTrace();
//            Message.errorCatch(actionChoosePane, "Error", "Edit Error");
        }
    }

    @FXML private void enumerationOnClick(){
        Alert progressAlert = new Alert(Alert.AlertType.INFORMATION);
        progressAlert.setTitle("Перебір варіантів");
        progressAlert.setHeaderText(null);
        progressAlert.setContentText("Йде перебір варіантів, будь ласка, зачекайте...");
        progressAlert.initOwner(actionChoosePane.getScene().getWindow());
        progressAlert.show();

        Task<Void> enumerationTask = new Task<Void>() {
            @Override
            protected Void call() {
                try (FileInputStream is = new FileInputStream(RoadConstractionModel.getFile());
                     ObjectInputStream ois = new ObjectInputStream(is)) {
                    RoadConstractionModel.setEnumerated(true);
                    Iterable<ArrayList<Double>> variationsIter = RoadConstractionModel.layerThinckVariation();
                    List<ArrayList<Double>> variations = new ArrayList<>();
                    variationsIter.forEach(variations::add);
                    int total = variations.size();
                    AtomicInteger processed = new AtomicInteger();
                    System.out.println("Початок перебору варіантів: загалом " + total + " конструкцій");
                    RoadConstraction original = (RoadConstraction) ois.readObject();

                    int cores = Math.max(1, Runtime.getRuntime().availableProcessors());
                    int batchSize = (int) Math.ceil((double) total / cores);
                    List<List<ArrayList<Double>>> portions = new ArrayList<>();
                    for (int i = 0; i < total; i += batchSize) {
                        portions.add(variations.subList(i, Math.min(i + batchSize, total)));
                    }

                    ExecutorService executor = Executors.newFixedThreadPool(portions.size());
                    for (List<ArrayList<Double>> portion : portions) {
                        executor.submit(() -> {
                            for (ArrayList<Double> thinckness : portion) {
                                int count = processed.incrementAndGet();
                                updateMessage("Обробка " + count + " з " + total);
                                try {
                                    RoadConstraction roadConstraction = copyRoadConstraction(original);
                                    RoadConstractionModel.setRoadConstraction(roadConstraction);
                                    for (int i = 0; i < RoadConstractionModel.getTotalLayerList().size(); i++) {
                                        RoadConstractionModel.getTotalLayerList().get(i).setThickness(thinckness.get(i) / 100);
                                    }
                                    initialize();
                                    RoadConstractionModel.analisys(actionChoosePane);
                                } catch (IOException | ClassNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                    executor.shutdown();
                    try {
                        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }
                    System.out.println("Перебір варіантів завершено");
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

        progressAlert.contentTextProperty().bind(enumerationTask.messageProperty());

        enumerationTask.setOnSucceeded(event -> {
            progressAlert.close();
            Message.errorCatch(actionChoosePane,"Результати", "Перебір варіантів конструкції дорожнього покриття успішно завершена.\nРезультати представлені у відповідному файлі звіту (.../report/...)");
        });

        enumerationTask.setOnFailed(event -> progressAlert.close());

        Thread th = new Thread(enumerationTask);
        th.setDaemon(true);
        th.start();
    }

    @FXML private void optByCostOnClick(){
        // в розробці
    }

    @FXML private void optByBitumDepthOnClick(){
        // в розробці
    }

    @FXML private void optByFundamentDepthOnClick(){
        // в розробці
    }

    @FXML private void optByTotalDepthOnClick(){
        // в розробці
    }

    @FXML private void cancelOnClick(){
        Stage dlg = (Stage)(actionChoosePane.getScene().getWindow());
        dlg.close();
    }

    private RoadConstraction copyRoadConstraction(RoadConstraction original) throws IOException, ClassNotFoundException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream out = new ObjectOutputStream(bos)) {
            out.writeObject(original);
            out.flush();
            try (ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
                 ObjectInputStream in = new ObjectInputStream(bis)) {
                return (RoadConstraction) in.readObject();
            }
        }
    }
}
