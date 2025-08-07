package com.ntu.api.model;

import com.ntu.api.domain.RoadConstraction;
import com.ntu.api.domain.listCreate.Objects.Layers.Layer;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Report {
    private boolean excellentBool = true;

    private String tensionResult;
    private String tensionConclusion;
    private String tensionMargin;
    private String tensionReliability;

    private String deflectionResult;
    private String deflectionConclusion;
    private String deflectionMargin;
    private String deflectionReliability;

    private String subGradeMovementResult;
    private String subGradeMovementConclusion;
    private String subGradeMovementMargin;
    private String subGradeMovementReliability;

    private ArrayList<String> inviscLayerMovemmentResult = new ArrayList<>();
    private ArrayList<String> inviscLayerMovemmentConclusion = new ArrayList<>();
    private ArrayList<String> inviscLayerMovemmentMargin = new ArrayList<>();
    private ArrayList<String> inviscLayerMovemmentReliability = new ArrayList<>();

    private boolean tensionBool;
    private boolean tensionReliabilityBool;
    private boolean deflectionBool;
    private boolean deflectionReliabilityBool;
    private boolean subGradeMovementBool;
    private boolean subGradeMovementReliabilityBool;
    private boolean inviscLayerMovemmentBool = true;
    private boolean inviscLayerMovemmentReliabilityBool = true;

    private String roadCost;

    public String getTensionResult() {
        return tensionResult;
    }
    public String getTensionConclusion() {
        return tensionConclusion;
    }
    public String getTensionMargin() {
        return tensionMargin;
    }
    public String getTensionReliability() {
        return tensionReliability;
    }
    public String getDeflectionResult() {
        return deflectionResult;
    }
    public String getDeflectionConclusion() {
        return deflectionConclusion;
    }
    public String getDeflectionMargin() {
        return deflectionMargin;
    }
    public String getDeflectionReliability() {
        return deflectionReliability;
    }
    public String getSubGradeMovementResult() {
        return subGradeMovementResult;
    }
    public String getSubGradeMovementConclusion() {
        return subGradeMovementConclusion;
    }
    public String getSubGradeMovementMargin() {
        return subGradeMovementMargin;
    }
    public String getSubGradeMovementReliability() {
        return subGradeMovementReliability;
    }
    public ArrayList<String> getInviscLayerMovemmentResult() {
        return inviscLayerMovemmentResult;
    }
    public ArrayList<String> getInviscLayerMovemmentConclusion() {
        return inviscLayerMovemmentConclusion;
    }
    public ArrayList<String> getInviscLayerMovemmentMargin() {
        return inviscLayerMovemmentMargin;
    }
    public ArrayList<String> getInviscLayerMovemmentReliability() {
        return inviscLayerMovemmentReliability;
    }
    public boolean isTensionBool() {
        return tensionBool;
    }
    public boolean isTensionReliabilityBool() {
        return tensionReliabilityBool;
    }
    public boolean isDeflectionBool() {
        return deflectionBool;
    }
    public boolean isDeflectionReliabilityBool() {
        return deflectionReliabilityBool;
    }
    public boolean isSubGradeMovementBool() {
        return subGradeMovementBool;
    }
    public boolean isSubGradeMovementReliabilityBool() {
        return subGradeMovementReliabilityBool;
    }
    public boolean isInviscLayerMovemmentBool() {
        return inviscLayerMovemmentBool;
    }
    public boolean isInviscLayerMovemmentReliabilityBool() {
        return inviscLayerMovemmentReliabilityBool;
    }

    private static FileWriter resultWriter;
    private static FileWriter excellentResultWriter;
    private static File directoryForReport;

    public void reportMake(File directory, RoadConstraction roadConstraction, boolean bool){
        directoryForReport = new File(directory, "reports");
        if(!directoryForReport.exists()) {
            directoryForReport.mkdirs();
        }
        prepeare();
        write(directoryForReport, roadConstraction, bool);
    }

    private void prepeare(){
        excellentBool = true;
//        розрахунок на згин
        if(RoadConstractionModel.getRoadConstraction().getBituminous().size()>0) {
            tensionResult = "Розрахунковий показник міцності за критерієм опору шарів з монолітних матеріалів розтягу при згині = " + RoadConstractionModel.getElasticTensionCoefficient() + " . ";
            if (RoadConstractionModel.getElasticTensionCoefficient() >= RoadConstractionModel.getRoadConstraction().getRoad().getrBend()) {
                tensionConclusion = "Вибрана конструкція задовольняє умову міцності за критерієм опору шарів з монолітних матеріалів розтягу при згині. ";
                tensionMargin = "Розрахунковий показник міцності за критерієм опру розтягу при згині більший за необхідний на " + RoadConstractionModel.getTension() + " %. ";
                tensionBool = true;
            } else {
                excellentBool = false;
                tensionConclusion = "Вибрана конструкція незадовольняє умову міцності за критерієм опору шарів з монолітних матеріалів розтягу при згині. ";
                tensionMargin = "Розрахунковий показник міцності за критерієм опру розтягу при згині менший за необхідний на " + RoadConstractionModel.getTension() + " %. ";
                tensionBool = false;
            }
            String reliabilityT = "Розрахунковий показник надійності за критерієм опору шарів з монолітних матеріалів розтягу при згині = " + RoadConstractionModel.getBendingTensionReliability() + " .\n";
            if (RoadConstractionModel.getBendingTensionReliability() >= RoadConstractionModel.getRoadConstraction().getRoad().getkN()) {
                tensionReliability = reliabilityT + "Вибрана конструкція задовольняє вимогу надійності за критерієм опору шарів з монолітних матеріалів розтягу при згині. ";
                tensionReliabilityBool = true;
            } else {
                excellentBool = false;
                tensionReliability = reliabilityT + "Вибрана конструкція незадовольняє вимогу надійності за критерієм опору шарів з монолітних матеріалів розтягу при згині. ";
                tensionReliabilityBool = false;
            }
        }
        else{
            tensionResult = "В конструкції дорожнього одягу відсутні шари з монолітних матеріалів. Тому розрахунок показника міцності за критерієм опору шарів з монолітних матеріалів розтягу при згині не проводитися. ";
            tensionConclusion = " ";
            tensionMargin = " ";
            tensionReliability = " ";
        }
//        розрахунок на прогин
        if(RoadConstractionModel.getRoadConstraction().getDesigionLoad().getName().equals("A1")){
            deflectionResult = "Для розрахункового навантаження А1 розрахунок конструкції нежорсткого одягу за критерієм загального модуля пружності не виконується. ";
            deflectionConclusion = " ";
            deflectionMargin = " ";
            deflectionReliability = " ";
        }
        else {
            deflectionResult = "Розрахунковий показник міцності за критерієм пружного прогину = " + RoadConstractionModel.getElasticDeflectionCoefficient() + " . ";
            if(RoadConstractionModel.getElasticDeflectionCoefficient()>= RoadConstractionModel.getRoadConstraction().getRoad().getrDefl()){
                deflectionConclusion = "Вибрана конструкція задовольняє умову міцності за критерієм допустимого пружниого прогину. ";
                deflectionMargin = "Розрахунковий показник міцності за критерієм допустимого пружного прогину більший за необхідний на " + RoadConstractionModel.getDeflection() + " %. ";
                deflectionBool = true;
            }
            else{
                excellentBool = false;
                deflectionConclusion = "Вибрана конструкція незадовольняє умову міцності за критерієм допустимого пружного прогину. ";
                deflectionMargin = "Розрахунковий показник міцності за критерієм допустимого пружного прогину менший за необхідний на " + RoadConstractionModel.getDeflection() + " %. ";
                deflectionBool = false;
            }
            String reliabilityD = "Розрахунковий показник надійності за критерієм допустимого пружного прогину = " + RoadConstractionModel.getElasticDeflectionReliability() + " .\n";
            if(RoadConstractionModel.getElasticDeflectionReliability()>= RoadConstractionModel.getRoadConstraction().getRoad().getkN()){
                deflectionReliability = reliabilityD + "Вибрана конструкція задовольняє вимогу надійності за критерієм допустимого пружного прогину. ";
                deflectionReliabilityBool = true;
            }
            else{
                excellentBool = false;
                deflectionReliability = reliabilityD + "Вибрана конструкція незадовольняє вимогу надійності за критерієм допустимого пружного прогину. ";
                deflectionReliabilityBool = false;
            }
        }
//        розрахунок по зсуву грунту земляного покриття;;
        subGradeMovementResult = "Розрахунковий показник міцності за критерієм зсуву у в грунті земляного полотна = " + RoadConstractionModel.getMovementSubGradeCoefficient() + " . ";
        if(RoadConstractionModel.getMovementSubGradeCoefficient()>= RoadConstractionModel.getRoadConstraction().getRoad().getrOffset()){
            subGradeMovementConclusion = "Вибрана конструкція задовольняє умову міцності за критерієм зсуву в грунті земляного полотна. ";
            subGradeMovementMargin = "Розрахунковий показник міцності за критерієм зсуву в грунті земляного полотна більший за необхідний на " + RoadConstractionModel.getSubGradeMovement() + " %. ";
            subGradeMovementBool = true;
        }
        else{
            excellentBool = false;
            subGradeMovementConclusion = "Вибрана конструкція незадовольняє умову міцності за критерієм зсуву в грунті земляного полотна. ";
            subGradeMovementMargin = "Розрахунковий показник міцності за критерієм зсуву в грунті земляного полотна менший за необхідний на " + RoadConstractionModel.getSubGradeMovement() + " %. ";
            subGradeMovementBool = false;
        }
        String reliabilityS = "Розрахунковий показник надійності за критерієм зсуву в грунті земляного полотна = " + RoadConstractionModel.getMovementSubGradeReliability() +" .\n";
        if(RoadConstractionModel.getMovementSubGradeReliability()>= RoadConstractionModel.getRoadConstraction().getRoad().getkN()){
            subGradeMovementReliability = reliabilityS + "Вибрана конструкція задовольняє вимогу надійності за критерієм зсуву в грунті земляного полотна. ";
            subGradeMovementReliabilityBool = true;
        }
        else{
            excellentBool = false;
            subGradeMovementReliability = reliabilityS + "Вибрана конструкція незадовольняє вимогу надійності за критерієм зсуву в грунті земляного полотна. ";
            subGradeMovementReliabilityBool = false;
        }
//        розрахунок по зсуву у нев'язких матеріалах
        inviscLayerMovemmentResult.clear();
        inviscLayerMovemmentConclusion.clear();
        inviscLayerMovemmentMargin.clear();
        inviscLayerMovemmentReliability.clear();
        if(RoadConstractionModel.getRoadConstraction().getSands().size()>0){
            for(int i = 0; i<RoadConstractionModel.getRoadConstraction().getSands().size(); i++){
                Double element = RoadConstractionModel.getMovementInviscidLayerCoefficient().get(i);
                inviscLayerMovemmentResult.add("Розрахунковий показник міцності за критерієм зсуву в нев'язких матеріалах: "
                        + RoadConstractionModel.getRoadConstraction().getSands().get(i).getName() + " = " + element + " .");
                if(element>= RoadConstractionModel.getRoadConstraction().getRoad().getrOffset()){
                    inviscLayerMovemmentConclusion.add("Вибрана конструкція задовольняє умову міцності за критерієм зсуву в нев'язких матеріалах. ");
                    inviscLayerMovemmentMargin.add("Розрахунковий показник міцності за критерієм зсуву в нев'язких матеріалах більший за необхідний на " + RoadConstractionModel.getInviscLayerMovement().get(i) + " %. ");
                }
                else{
                    excellentBool = false;
                    inviscLayerMovemmentConclusion.add("Вибрана конструкція незадовольняє умову міцності за критерієм зсуву в нев'язких матеріалах. ");
                    inviscLayerMovemmentMargin.add("Розрахунковий показник міцності за критерієм зсуву в нев'язких матеріалах менший за необхідний на " + RoadConstractionModel.getInviscLayerMovement().get(i) + " %. ");
                    inviscLayerMovemmentBool = false;
                }
                String reliabilityI = "Розрахунковий показник надійності за критерієм зсуву в нев'язких матеріалах = " + RoadConstractionModel.getMovementInviscidLayerReliability().get(i) + "\n";
                if(RoadConstractionModel.getMovementInviscidLayerReliability().get(i)>= RoadConstractionModel.getRoadConstraction().getRoad().getkN()){
                    inviscLayerMovemmentReliability.add(reliabilityI + "Вибрана конструкція задовольняє вимогу надійності за критерієм зсуву в нев'язких матеріалах. ");
                }
                else{
                    excellentBool = false;
                    inviscLayerMovemmentReliability.add(reliabilityI + "Вибрана конструкція незадовольняє вимогу надійності за критерієм зсуву в нев'язких матеріалах. ");
                    inviscLayerMovemmentReliabilityBool = false;
                }
            }
        }
        else{
            inviscLayerMovemmentResult.add("В конструкції дорожнього одягу відсутні шари з нев'язких матеріалів. Тому розрахунок показника міцності за критерієм зсуву в нев'язких матеріалах не проводитися. ");
            inviscLayerMovemmentConclusion.add(" ");
            inviscLayerMovemmentMargin.add(" ");
            inviscLayerMovemmentReliability.add(" ");
        }
        roadCost = "\nВартість однієї одиниці площі дорожнього покриття = " + RoadConstractionModel.roadCost() + " . ";
    }

    private void write(File directory, RoadConstraction roadConstraction, boolean bool){
        try {
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS").format(new Date());
            File fileForReport = new File(directory, "report" + timestamp + ".txt");
            File fileExcReport = new File(directory, "excellentReport" + timestamp + ".txt");
            if(bool){
                if (excellentBool) {
                    excellentResultWriter = new FileWriter(fileExcReport, true);
                    print(excellentResultWriter, roadConstraction);
                    File directoryForRoadConstr = new File(directory.getParentFile(), "road constraction");
                    if(!directoryForRoadConstr.exists()) {
                        directoryForRoadConstr.mkdirs();
                    }
                    File dir = new File(directoryForRoadConstr, timestamp);
                    dir.mkdirs();

                    StringBuilder fileName = new StringBuilder();
                    for (Layer layer : RoadConstractionModel.getTotalLayerList()) {
                        if (fileName.length()>0){
                            fileName.append(layer.getThickness()).append("_");
                        }

                    }

                    File fileForWrite = new File(dir, fileName.toString() + ".txt");
                    try (FileOutputStream fos = new FileOutputStream(fileForWrite);
                         ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                        oos.writeObject(RoadConstractionModel.getRoadConstraction());
                        oos.flush();
                    } catch (FileNotFoundException e) {
                        System.out.println(e);
                        e.printStackTrace();
                    }
                }
            }
            else {
                if (excellentBool) {
                    excellentResultWriter = new FileWriter(fileExcReport, true);
                    print(excellentResultWriter, roadConstraction);
                } else {
                    resultWriter = new FileWriter(fileForReport, true);
                    print(resultWriter, roadConstraction);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void print(FileWriter fileWriter, RoadConstraction roadConstraction){
        try {
            fileWriter.write(roadConstraction.toString() + "\n\n");
            fileWriter.write(tensionResult + "\n");
            fileWriter.write(tensionConclusion + "\n");
            fileWriter.write(tensionMargin + "\n");
            fileWriter.write(tensionReliability + "\n\n");
            fileWriter.write(deflectionResult + "\n");
            fileWriter.write(deflectionConclusion + "\n");
            fileWriter.write(deflectionMargin + "\n");
            fileWriter.write(deflectionReliability + "\n\n");
            fileWriter.write(subGradeMovementResult + "\n");
            fileWriter.write(subGradeMovementConclusion + "\n");
            fileWriter.write(subGradeMovementMargin + "\n");
            fileWriter.write(subGradeMovementReliability + "\n\n");
            for (int i = 0; i<inviscLayerMovemmentResult.size(); i++) {
                fileWriter.write(inviscLayerMovemmentResult.get(i) + "\n");
                fileWriter.write(inviscLayerMovemmentConclusion.get(i) + "\n");
                fileWriter.write(inviscLayerMovemmentMargin.get(i) + "\n");
                fileWriter.write(inviscLayerMovemmentReliability.get(i) + "\n");
            }
            fileWriter.write(roadCost);
            fileWriter.write("\n\n");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
