package com.ntu.api.model;

import com.ntu.api.domain.RoadConstraction;
import com.ntu.api.domain.listCreate.Objects.Element;
import com.ntu.api.domain.listCreate.Objects.GroundWtParameters;
import com.ntu.api.domain.listCreate.Objects.Layers.*;

import java.util.ArrayList;

public class RoadConstractionModel {
    /* Клас, який описує модель конструкції дорожнього одягу для розрахунку характеристик його конструкції та надійності
    * roadConstraction - конструкція дорожнього одягу, яка розраховується
    * dinamicLoadDuration - тривалість дії навантаження від рухомого автотранспорту
    * staticLoadDuration - тривалість дії статичного навантаження
    * humidityVariationCoefficient - коефіцієент варіації вологості
    *  wTg - характеристик за вологістю грунту
    *  wTs - характеристик за вологістю невя'зкого шару (піску)
    *  dinamicCoefficient - коефіцієнт динамічності
    *  nominalDynamicLoad - номінальне динамічне  навантаження
    *  neededElasticModule - потрібний модуль пружності Епотр
    *  calculatedWeelImprintDiameter - діаметр розрахункового відбитку колеса D,м
    *  elasticModule - загальний розрахований модуль пружності Езаг
    *  elasticDeflectionCoefficient -  коефіцієнт запасу Еза/Епотр (за критерієм опору пружному прогину)
    *  elasticTensionCoefficient - коефіцієнт запасу міцності Rzg/gr (за критерієм опору розтягу при згині)
    *  movementSubGradCoefficient -  коефіцієнт запасу міцності (за критерієм зсуву у грунті земляного полотна)
    *  movementInviscidLayerCoefficient - список коефіцієнтів запасу міцності (за критерієм зсуву у шарі невязкого середовища)
    *  rZg - гранично допустиме напруження розтягу матеріалу шару
    *  gRMax - найбільше напруження розтягу у розглянутому шарі, МПа
    *  rLabCalculated - розрахункове значення опору розтягу при згині Rp, МПа
    *  kkp - коефіцієнт, що враховує короткочасність та повторюваність навантаження на дорозі
    *  kb - коефіцієнт, що враховує особливості напруженого стану дорожнього покриття під колесом автомобіля
    *  (0.85 - для коліс зі спарениими балонами, 1 - при колесо з одним балоном)
    *  k1 - коефіцієнт, який враховує вплив навантаження на опір зсуву грунту к1=1 при статичній дії навантаження
    *       к1=1.5 при навантаженні з малою повторністю
    *  k2 - коефіцієнт запасу на неоднорідності умов роботи конструкції
    *  k3 - коефіцієнт, який враховує особливості роботи грунту
    *  k4 - коефіцієнт, який враховує ймовірність впливу чинників на надійність стабільної роботи грунту
    *  criticalTension - граничне напруження зсуву в грунті;
    *  ownTension - напруження зсуву в грунті від власної ваги дорожнього одягу;
    *  activeTension - активне навантаження зсуву грунту від тимчасового навантаження;
    * */
    private static RoadConstraction roadConstraction;
    private static final Integer staticLoadDuration = 600;
    private static final Double dinamicLoadDuration = 0.1;
    private static final Double humidityVariationCoefficient = 0.1;
    private static final Double dinamicCoefficient = 1.3;
    private static GroundWtParameters wTg;
    private static GroundWtParameters wTs;
    private static Double nominalDynamicLoad;
    private static Double calculatedDailyIntensity;
    private static Double neededElasticModule;
    private static Double calculatedWeelImprintDiameter;
    private static Double elasticModule;
    private static Double elasticDeflectionCoefficient;
    private static Double elasticTensionCoefficient;
    private static Double movementSubGradeCoefficient;
    private static ArrayList<Double> movementInviscidLayerCoefficient;
    private static Double criticalTension;
    private static Double ownTension;
    private static Double activeTension;
    private static double k1;
    private static double k2;
    private static double k3;
    private static double k4;

    public static RoadConstraction getRoadConstraction() {
        return roadConstraction;
    }
    public static void setRoadConstraction(RoadConstraction roadConstraction) {
        RoadConstractionModel.roadConstraction = roadConstraction;
    }
    public static Integer getStaticLoadDuration() {
        return staticLoadDuration;
    }
    public static Double getDinamicLoadDuration() {
        return dinamicLoadDuration;
    }
    public static Double getHumidityVariationCoefficient() {
        return humidityVariationCoefficient;
    }
    public static Double getDinamicCoefficient() {
        return dinamicCoefficient;
    }
    public static GroundWtParameters getwTg() {
        return wTg;
    }
    public static void setwTg(GroundWtParameters wTg) {
        RoadConstractionModel.wTg = wTg;
    }
    public static GroundWtParameters getwTs() {
        return wTs;
    }
    public static void setwTs(GroundWtParameters wTs) {
        RoadConstractionModel.wTs = wTs;
    }
    public static Double getNominalDynamicLoad() {
        return nominalDynamicLoad;
    }
    public static void setNominalDynamicLoad(Double nominalDynamicLoad) {
        RoadConstractionModel.nominalDynamicLoad = nominalDynamicLoad;
    }
    public static Double getCalculatedDailyIntensity() {
        return calculatedDailyIntensity;
    }
    public static void setCalculatedDailyIntensity(Double calculatedDailyIntensity) {
        RoadConstractionModel.calculatedDailyIntensity = calculatedDailyIntensity;
    }
    public static Double getNeededElasticModule() {
        return neededElasticModule;
    }
    public static void setNeededElasticModule(Double neededElasticModule) {
        RoadConstractionModel.neededElasticModule = neededElasticModule;
    }
    public static Double getElasticDeflectionCoefficient() {
        return elasticDeflectionCoefficient;
    }
    public static Double getElasticTensionCoefficient() {
        return elasticTensionCoefficient;
    }
    public static void setElasticTensionCoefficient(Double elasticTensionCoefficient) {
        RoadConstractionModel.elasticTensionCoefficient = elasticTensionCoefficient;
    }
    public static Double getMovementSubGradeCoefficient() {
        return movementSubGradeCoefficient;
    }
    public static void setMovementSubGradeCoefficient(Double movementSubGradeCoefficient) {
        RoadConstractionModel.movementSubGradeCoefficient = movementSubGradeCoefficient;
    }
    public static ArrayList<Double> getMovementInviscidLayerCoefficient() {
        return movementInviscidLayerCoefficient;
    }
    public static void setMovementInviscidLayerCoefficient(ArrayList<Double> movementInviscidLayerCoefficient) {
        RoadConstractionModel.movementInviscidLayerCoefficient = movementInviscidLayerCoefficient;
    }

    //    метод розрахунку коефіцієнту запасу міцності за критерієм опору шарів з монолітних матеріалів розтягу при згині
    public static Double bendingTensionCalculation(){
        Double kb = 0.85;
        ArrayList<Layer> calculatedLayersList = invertLayerList(roadConstraction.getBituminous());
        double calculatedElasticModule = roadConstraction.getEstimatedGroundWt().geteGr();
        for (Layer temp: calculatedLayersList){
            Bituminous iterTemp = (Bituminous) temp;
            calculatedElasticModule = iterationElasticModule(temp,calculatedElasticModule,iterTemp.getElasticityModulTension());
        }
        Double E1E2 = calculatedElasticModule/roadConstraction.getEstimatedGroundWt().geteGr();
        Double hD= bitumThickness()/calculatedWeelImprintDiameter;
        Double ar = 0.083*Math.pow(Math.log(E1E2),2.2)+1.87;
        Double br = 0.00004*Math.pow(E1E2,1.4)+0.007;
        Double cr = ar*Math.pow(hD,0.9)+br;
        Bituminous lowerBitum = roadConstraction.getBituminous().get(roadConstraction.getBituminous().size()-1);
        Double kkp = lowerBitum.getKoeficientKpr()*Math.pow(roadConstraction.getPassageNumber(),((-1.0)/lowerBitum.getM()));
        Double rZg = lowerBitum.getrLab()*(1-roadConstraction.getRoad().getkStudent()
                *lowerBitum.getVariationCoeficientCE())*lowerBitum.getkM()*lowerBitum.getkT()*kkp;
        Double gR = 1.28*E1E2*hD*((1-(0.637*Math.atan(cr)))*Math.pow(Math.atan((1.0/cr)),2.0))*
                roadConstraction.getDesigionLoad().getPreassureWeel()*kb;
        elasticTensionCoefficient = rZg/gR;
        return elasticTensionCoefficient;
    }

//    метод розрахунку коефіцієнт запасу міцності за критерієм пружного прогину(загального модуля пружності)
    public static Double elasticDeflectionCalculation(){
        ArrayList<Layer> calculatedLayersList = invertLayerList(roadConstraction.getTotalLayerList());
        double calculatedElasticModule = roadConstraction.getEstimatedGroundWt().geteGr();
        for (Layer temp: calculatedLayersList){
            calculatedElasticModule = iterationElasticModule(temp,calculatedElasticModule,temp.getElasticModuleDeflection());
        }
        elasticModule = calculatedElasticModule;
        elasticDeflectionCoefficient = elasticModule/neededElasticModule;
        return elasticDeflectionCoefficient;
    }

//    метод розрахунку коефіцієнту запасу міцності за критерієм зсуву у грунті земляного полотна
    public static Double movementSubGradeCalculation(){
        k3 = roadConstraction.getGround().getK3();
        criticalTension = roadConstraction.getEstimatedGroundWt().getcGr()*k1*k2*k3*k4;
        ownTension = 0.00001*(5-3*roadConstraction.getEstimatedGroundWt().getFi())*roadConstraction.getTotalLayersThickness();
        activeTension = roadConstraction.getDesigionLoad().getPreassureWeel()*Math.exp(-roadConstraction.getEstimatedGroundWt().getFi()/33)/
                (Math.pow(roadConstraction.getTotalLayersThickness()/calculatedWeelImprintDiameter,2.0)*
                 Math.pow(tensionMidleElasticModule()/roadConstraction.getEstimatedGroundWt().geteGr(),2.0/3.0)*57/25+4);
        movementSubGradeCoefficient = criticalTension/(ownTension+activeTension);
        return movementSubGradeCoefficient;
    }
//    метод розрахунку коефіцієнту запасу міцності за критерієм зсуву у шарі нев'язкого середовища
    public static ArrayList<Double> movementInviscidLayerCalculation(){
        double layersThickness = roadConstraction.getTotalLayersThickness();
        ArrayList<Sand> roadSands = new ArrayList<>();
        for(Sand sand: roadConstraction.getSands()){
            roadSands.add(sand);
        }
        movementInviscidLayerCoefficient = new ArrayList<>();
        while (roadSands.size()>0){
            Sand tempSand = roadSands.get(roadSands.size()-1);
            roadSands.remove(roadSands.size()-1);
            layersThickness -= tempSand.getThickness();
            k3 = tempSand.getK3();
            criticalTension = tempSand.getEstimatedWt().getcGr()*k1*k2*k3*k4;
            ownTension = 0.00001*(5-3*tempSand.getEstimatedWt().getFi())*layersThickness;
            activeTension = roadConstraction.getDesigionLoad().getPreassureWeel()*
                    Math.exp(-tempSand.getEstimatedWt().getFi()/33)/(Math.pow(layersThickness/calculatedWeelImprintDiameter,2.0)*
                    Math.pow(tensionMidleElasticModule(roadSands, layersThickness)/tempSand.getEstimatedWt().geteGr(),2.0/3.0)*57/25+4);
            movementInviscidLayerCoefficient.add(0,criticalTension/(ownTension+activeTension));
        }
        return movementInviscidLayerCoefficient;
    }
    //    метод розрахунку надійності за критерієм опору шарів з монолітних матеріалів розтягу при згині
    public static Double bendingTensionReliabilityCalculation(){
//        ArrayList<Bituminous> calculatedBitumsList = new ArrayList<>();
//        for (Bituminous temp: roadConstraction.getBituminous()){
//            calculatedBitumsList.add(0,temp);
//        }
//        double calculatedElasticModule = roadConstraction.getEstimatedGroundWt().geteGr();
//        for (Bituminous temp: calculatedBitumsList){
//            Double hEkvD = 2*temp.getThickness()*Math.pow(temp.getElasticityModulTension()/(6*calculatedElasticModule),1.0/3.0)/calculatedWeelImprintDiameter;
//            calculatedElasticModule = (1.05-(0.1*temp.getThickness()/calculatedWeelImprintDiameter*
//                    (1-Math.pow(calculatedElasticModule/temp.getElasticityModulTension(),1.0/3.0))))*temp.getElasticityModulTension()
//                    /(0.71*Math.pow(calculatedElasticModule/temp.getElasticityModulTension(),1.0/3.0)*Math.atan(1.35*hEkvD)
//                    +(temp.getElasticityModulTension()/calculatedElasticModule*2/Math.PI*Math.atan(1/hEkvD)));
//        }


        return 0.0 ;
    }

    //    метод розрахунку надійності за критерієм пружного прогину(загального модуля пружності)
    public static Double elasticDeflectionReliabilityCalculation(){

        return 0.0 ;
    }

    //    метод розрахунку надійності за критерієм зсуву у грунті земляного полотна
    public static Double movementSubGradeReliabilityCalculation(){

        return 0.0 ;
    }
    //    метод розрахунку надійності за критерієм зсуву у шарі нев'язкого середовища
    public static Double movementInviscidLayerReliabilityCalculation(){

        return 0.0 ;
    }

    // метод розрахунку загального модуля пружності Езаг
    public static Double elasticModuleCalculation(){
        return 0.0 ;
    }

    //     метод визначення загальної товщини шарів дорожного покриття
    public static Double totalLayersThickness(RoadConstraction roadConstraction){
        Double layersThickness = 0.0;
        for(RoadLayers el: roadConstraction.getRoadLayers()){
            for (Layer layer: el.getLayers()) {
                layersThickness += layer.getThickness();
            }
        }
        return layersThickness;
    }

//    метод визначення загальної товщини в'язких шарів дорожнього покриття (без шарів піску + з шарами піску)
//    public static ArrayList<Double> layersThicknessWithoutSand(RoadConstraction roadConstraction){
//        ArrayList<Double> layerThicknessWithoutSands = new ArrayList<>();
//        Double tempLAyerThickness=0.0;
//        for(RoadLayers el: roadConstraction.getRoadLayers()){
//            if(!el.getName().equals("Шар нев'язкого середовища") && el.getLayers().size()>0){
//                for (Layer layer:el.getLayers()){
//                    tempLAyerThickness += layer.getThickness()*0.01;
//                }
//            }
//        }
//        layerThicknessWithoutSands.add(tempLAyerThickness);
//        for (Sand sand: roadConstraction.getSands()){
//            layerThicknessWithoutSands.add(layerThicknessWithoutSands.get(layerThicknessWithoutSands.size()-1)+sand.getThickness()*0.01);
//        }
//        return layerThicknessWithoutSands;
//    }

//    метод визначення розрахункової вологості грунту
    public static void estimatedGroundMoisture(){
        Double moisture = (roadConstraction.getHumidity()-roadConstraction.getCorrection())
                *(1+roadConstraction.getRoad().getkStudent()*humidityVariationCoefficient);
        roadConstraction.setEstimatedGroundMoisture(moisture);
        wTg = estimatedParameters(roadConstraction.getGround().getwT(), roadConstraction.getEstimatedGroundMoisture());
        roadConstraction.setEstimatedGroundWt(wTg);
    }

//    метод визначення розрахугковох вологості нев'язкого шару (піску)
    public static void estimatedSandMoisture(){
        Double moisture;
        if(roadConstraction.getRoadLayers().get(4).getLayers().size()>0) {
            if (roadConstraction.getHumidity() <= 0.75 && roadConstraction.getTotalLayersThickness() <= 0.75) {
                moisture = (roadConstraction.getHumidity() - roadConstraction.getCorrection())
                        * (1 + roadConstraction.getRoad().getkStudent() * humidityVariationCoefficient);
            } else {
                moisture = (-0.967 * Math.pow(roadConstraction.getHumidity(), 2) + 1.956 * roadConstraction.getHumidity() - 0.924)
                        * 0.0001 * Math.pow(roadConstraction.getTotalLayersThickness(), 2) +
                        (-2.531 * Math.pow(roadConstraction.getHumidity(), 2) + 5.745 * roadConstraction.getHumidity() - 2.885)
                                * 0.01 * roadConstraction.getTotalLayersThickness() +
                        (-2.029 * Math.pow(roadConstraction.getHumidity(), 2) + 5.319 * roadConstraction.getHumidity() - 2.098);
            }
            for(Sand sand: roadConstraction.getSands()){
                wTs = estimatedParameters(sand.getwT(), moisture);
                sand.setEstimatedWt(wTs);
                sand.setElasticModuleDeflection(sand.getEstimatedWt().geteGr());
                sand.setElasticModuleMovement(sand.getEstimatedWt().geteGr());
            }
        }
    }

//  метод визначення розрахункового значення вологості земляного полотна або нев'язкового шару
    private static GroundWtParameters estimatedParameters(ArrayList<GroundWtParameters> wt, double estimatedMoisture){
        GroundWtParameters temp;
        double estimatedMoisturetemp = estimatedMoisture;
        double max =(double)Math.round(Math.ceil(estimatedMoisture/0.05)*5)/100;
        double min =(double)Math.round(Math.floor(estimatedMoisture/0.05)*5)/100;
        GroundWtParameters wtMin = new GroundWtParameters();
        GroundWtParameters wtMax = new GroundWtParameters();

        if(min<wt.get(0).getWt()){
            temp = wt.get(0);
        }
        else if(max>wt.get(8).getWt()){
            temp = wt.get(8);
        }
        else{
            for (GroundWtParameters el : wt) {
                if (el.getWt().equals(min)) {
                    wtMin = el;
                } else if (el.getWt().equals(max)) {
                    wtMax = el;
                }
            }
            Double eGrEstimated = wtMin.geteGr() + (estimatedMoisturetemp-wtMin.getWt())*
                    ((wtMin.geteGr()-wtMax.geteGr())/(wtMin.getWt()-wtMax.getWt()));
            Double fiEstimated = wtMin.getFi() + (estimatedMoisturetemp-wtMin.getWt())*
                    ((wtMin.getFi()-wtMax.getFi())/(wtMin.getWt()-wtMax.getWt()));
            Double cGrEstimated = wtMin.getcGr() + (estimatedMoisturetemp-wtMin.getWt())*
                    ((wtMin.getcGr()-wtMax.getcGr())/(wtMin.getWt()-wtMax.getWt()));
            temp = new GroundWtParameters(String.valueOf(estimatedMoisture),estimatedMoisturetemp,eGrEstimated,fiEstimated,cGrEstimated);
        }
        return temp;
    }

//    метод проведення попередніх розрахунків
    public static void preCalculation(){
        estimatedGroundMoisture();
        estimatedSandMoisture();
        nominalDynamicLoad = dinamicCoefficient*roadConstraction.getDesigionLoad().getStaticLoadAxis();
        calculatedDailyIntensity = roadConstraction.getPassageNumber()/roadConstraction.getOperationTime()/roadConstraction.getRbcz().getCheckoutDay();
        double tempNeededElasticModule = 42.843*Math.log(roadConstraction.getPassageNumber())-roadConstraction.getDesigionLoad().getbKoeficient();
        if(tempNeededElasticModule<roadConstraction.getMinElasticModule()){
            neededElasticModule = (double)(roadConstraction.getMinElasticModule());
        }
        else{neededElasticModule = tempNeededElasticModule;}
        calculatedWeelImprintDiameter = 0.01*Math.sqrt((4*nominalDynamicLoad*10)/(2*Math.PI*roadConstraction.getDesigionLoad().getPreassureWeel()));
        roadConstraction.layers();
        k1 = 1.0;
        k2 = 1.816-0.15*Math.log(calculatedDailyIntensity);
        k4 = 1;
    }
//метод визначення мінімального модуля пружності
    public static void minElasticModuleChoose(){
        if(roadConstraction.getRoadType().getName().equals("Капітальний")){
            roadConstraction.setMinElasticModule(roadConstraction.getRoad().getMinElasticModulCapital());
        }
        else if(roadConstraction.getRoadType().getName().equals("Вдосконалений полекшений")){
            roadConstraction.setMinElasticModule(roadConstraction.getRoad().getMinElasticModulLite());
        }
        else if(roadConstraction.getRoadType().getName().equals("Перехідний")){
            roadConstraction.setMinElasticModule(roadConstraction.getRoad().getMinElasticModulTransfer());
        }
    }

//    метод формування списку шарів дорожнього одягу з кінця в початок
    private static ArrayList<Layer> invertLayerList(ArrayList<? extends Layer> layers){
        ArrayList<Layer> tempLayersList = new ArrayList<>();
        for (Layer temp: layers){
            tempLayersList.add(0,temp);
        }
        return tempLayersList;
    }

//    метод визначення розрахункового модуля пружності
    private static Double iterationElasticModule(Layer tempLayer, Double iterElasticModule, Double layerParameterElasticModule){
        double hEkvD = 2*tempLayer.getThickness()*Math.pow(layerParameterElasticModule/(6*iterElasticModule),1.0/3.0)/calculatedWeelImprintDiameter;
        double calculatedElasticModule = (1.05-(0.1*tempLayer.getThickness()/calculatedWeelImprintDiameter*
                (1-Math.pow(iterElasticModule/layerParameterElasticModule,1.0/3.0))))*layerParameterElasticModule
                /(0.71*Math.pow(iterElasticModule/layerParameterElasticModule,1.0/3.0)*Math.atan(1.35*hEkvD)
                +(layerParameterElasticModule/iterElasticModule*2/Math.PI*Math.atan(1/hEkvD)));
        return calculatedElasticModule;
    }

//  метод визначення загальної товщини шарів асвальтобетону
    private static Double bitumThickness(){
        Double thickness = 0.0d;
        for (Bituminous bitum: roadConstraction.getBituminous()){
            thickness += bitum.getThickness();
        }
        return thickness;
    }
//    метод визначення модуля пружності верхнього шару моделі Еср, МПа (для розрахунку за критерієм зсуву у грунті земляного полотна)
    private static Double tensionMidleElasticModule() {
        double tensionElasticModule = 0.0;
        for (RoadLayers el : roadConstraction.getRoadLayers()) {
                for (Layer layer : el.getLayers()) {
                    tensionElasticModule += layer.getThickness() * layer.getElasticModuleMovement();
                }
            }
            return tensionElasticModule / roadConstraction.getTotalLayersThickness();
        }

    private static Double tensionMidleElasticModule(ArrayList<Sand> sands, Double layerThickness) {
        double tensionElasticModule = 0.0;
        for (RoadLayers el : roadConstraction.getRoadLayers()) {
            if (el.getName()!=("Шар нев'язкого середовища")) {
                for (Layer layer : el.getLayers()) {
                    tensionElasticModule += layer.getThickness() * layer.getElasticModuleMovement();
                }
            }
            else{
                for (Sand sand : sands) {
                    tensionElasticModule += sand.getThickness() * sand.getElasticModuleMovement();
                }
            }
        }
        return tensionElasticModule / layerThickness;
    }




//    метод для формування списку матеріалів з їх товщиною та модулями пружності для розрахунку загального модуля пружності при прогині
//    private static ArrayList<LayerModele> DeflectionElasticModelesCalculating(ArrayList<Layer> layers){
//        ArrayList<LayerModele> layerModeles = new ArrayList<>();
//        for(Layer layer: layers){
//            if(layer.getClass().equals(Bituminous.class)) {
//                Bituminous tempBitum = (Bituminous) layer;
//                layerModeles.set(0, new LayerModele(tempBitum.getName(), tempBitum.getThickness(), (double)tempBitum.getShortElasticModuls().get(0).getElasticModule());
//            }
//            else if (layer.)
//
//        }
//
//        return 0.0;
//    }

}
