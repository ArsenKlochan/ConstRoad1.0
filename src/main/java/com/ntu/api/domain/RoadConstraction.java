package com.ntu.api.domain;

import com.ntu.api.domain.listCreate.Objects.*;
import com.ntu.api.domain.listCreate.Objects.Layers.*;
import com.ntu.api.model.RoadConstractionModel;

import java.io.Serializable;
import java.util.ArrayList;

public class RoadConstraction extends Element implements Serializable {

    /*RoadConstraction - конструкція дорожнього одягу
    * Road Constraction описує конструкцію дорожнього одягу, його розташування та параметри,
    * а також результати розрахунку експлуатаційних характеристик конструкції дорожнього одягу та її надійності
    * rbcz - дорожньо-кліматична зона України, розташування ділянки дорожнього одягу, що рохраховується
    * road - описує категорію дороги:
    * roadType - описує тип дорожнього одягу
    * desigionLoad - описує групу розрахункового навантаження
    * soils -  описує вид грунтів
    * groundType - описує вид грунту;
    * ground - описує різновид глинистих грунтів;
    * roadLines - описує коефіцієнти суг руху
    * groundCorection - описує поправку на конструктивні особливості використовуваних
    дренажних конструкцій у дорожньо-кліматичних зонах І-IV
    * humidity - вологість грунту
    * correction - поправка на конструктивні особливості дренажної конструкції
    * operationTime - Строк експлуатації до капітального ремонту, років
    * passageNumber - Сумарна кількість проїздів розрахункового навантаження за строк служби
    * estimatedGroundMoisture - розрахункова вологість грунту

    * estimatedGroundWt - розрахункова вологість грунту
    * minElasticModule - мінімальний портрібний модуль пружності МПа для дорожнього одягу
    *
    * elasticModele - наявний модуль пружності
    * neededElasticModule - необхідний модуль пружності
    */

    private RBCZ rbcz;
    private Road road;
    private RoadType roadType;
    private DesigionLoad desigionLoad;
    private Soils soils;
    private GroundType groundType;
    private Ground ground;
    private RoadLines roadLines;
    private GroundCorection groundCorection;
    private double humidity;
    private double correction;
    private double operationTime;
    private double passageNumber;
    private double estimatedGroundMoisture;
    private GroundWtParameters estimatedGroundWt;
    private static Double elasticModele;
    private static Double neededElasticModule;
    private double totalLayersThickness;
    private int minElasticModule;

    private ArrayList<Bituminous> bituminous= new ArrayList<>();
    private ArrayList<StrengthenedMaterial> strengthenedMaterials= new ArrayList<>();
    private ArrayList<UnstrengthenedMaterial> unstrengthenedMaterialsCover = new ArrayList<>();
    private ArrayList<UnstrengthenedMaterial> unstrengthenedMaterialsBase = new ArrayList<>();
    private ArrayList<Sand> sands = new ArrayList<>();
    private ArrayList<RoadLayers> roadLayers = new ArrayList<>();

    public RBCZ getRbcz() {
        return rbcz;
    }
    public void setRbcz(RBCZ rbcz) {
        this.rbcz = rbcz;
    }
    public Road getRoad() {
        return road;
    }
    public void setRoad(Road road) {
        this.road = road;
    }
    public RoadType getRoadType() {
        return roadType;
    }
    public void setRoadType(RoadType roadType) {
        this.roadType = roadType;
    }
    public DesigionLoad getDesigionLoad() {
        return desigionLoad;
    }
    public void setDesigionLoad(DesigionLoad desigionLoad) {
        this.desigionLoad = desigionLoad;
    }
    public Soils getSoils() {
        return soils;
    }
    public void setSoils(Soils soils) {
        this.soils = soils;
    }
    public GroundType getGroundType() {
        return groundType;
    }
    public void setGroundType(GroundType groundType) {
        this.groundType = groundType;
        humidityChoose();
    }
    public Ground getGround() {
        return ground;
    }
    public void setGround(Ground ground) {
        this.ground = ground;
    }
    public RoadLines getRoadLines() {
        return roadLines;
    }
    public void setRoadLines(RoadLines roadLines) {
        this.roadLines = roadLines;
    }
    public GroundCorection getGroundCorection() {
        return groundCorection;
    }
    public void setGroundCorection(GroundCorection groundCorection) {
        this.groundCorection = groundCorection;
        correctionChoose();
    }
    public double getHumidity() {
        return humidity;
    }
    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }
    public double getCorrection() {
        return correction;
    }
    public void setCorrection(double correction) {
        this.correction = correction;
    }
    public double getOperationTime() {
        return operationTime;
    }
    public void setOperationTime(double operationTime) {
        this.operationTime = operationTime;
    }
    public double getPassageNumber() {
        return passageNumber;
    }
    public void setPassageNumber(double passageNumber) {
        this.passageNumber = passageNumber;
    }
    public ArrayList<Bituminous> getBituminous() {
        return bituminous;
    }
    public void setBituminous(ArrayList<Bituminous> bituminous) {
        this.bituminous = bituminous;
    }
    public ArrayList<StrengthenedMaterial> getStrengthenedMaterials() {
        return strengthenedMaterials;
    }
    public void setStrengthenedMaterials(ArrayList<StrengthenedMaterial> strengthenedMaterials) {
        this.strengthenedMaterials = strengthenedMaterials;
    }
    public ArrayList<UnstrengthenedMaterial> getUnstrengthenedMaterialsCover() {
        return unstrengthenedMaterialsCover;
    }
    public void setUnstrengthenedMaterialsCover(ArrayList<UnstrengthenedMaterial> unstrengthenedMaterialsCover) {
        this.unstrengthenedMaterialsCover = unstrengthenedMaterialsCover;
    }
    public ArrayList<UnstrengthenedMaterial> getUnstrengthenedMaterialsBase() {
        return unstrengthenedMaterialsBase;
    }
    public void setUnstrengthenedMaterialsBase(ArrayList<UnstrengthenedMaterial> unstrengthenedMaterialsBase) {
        this.unstrengthenedMaterialsBase = unstrengthenedMaterialsBase;
    }
    public ArrayList<Sand> getSands() {
        return sands;
    }
    public void setSands(ArrayList<Sand> sands) {
        this.sands = sands;
    }
    public double getEstimatedGroundMoisture() {
        return estimatedGroundMoisture;
    }
    public void setEstimatedGroundMoisture(double estimatedGroundMoisture) {
        this.estimatedGroundMoisture = estimatedGroundMoisture;
    }
    public double getTotalLayersThickness() {
        return totalLayersThickness;
    }
    public void setTotalLayersThickness(double totalLayersThickness) {
        this.totalLayersThickness = totalLayersThickness;
    }
    public static Double getElasticModele() {
        return elasticModele;
    }
    public static void setElasticModele(Double elasticModele) {
        RoadConstraction.elasticModele = elasticModele;
    }
    public static Double getNeededElasticModule() {
        return neededElasticModule;
    }
    public static void setNeededElasticModule(Double neededElasticModule) {
        RoadConstraction.neededElasticModule = neededElasticModule;
    }
    public int getMinElasticModule() {
        return minElasticModule;
    }
    public void setMinElasticModule(int minElasticModule) {
        this.minElasticModule = minElasticModule;
    }
    public GroundWtParameters getEstimatedGroundWt() {
        return estimatedGroundWt;
    }
    public void setEstimatedGroundWt(GroundWtParameters estimatedGroundWt) {
        this.estimatedGroundWt = estimatedGroundWt;
    }
    public ArrayList<RoadLayers> getRoadLayers() {
        return roadLayers;
    }
    public void setRoadLayers(ArrayList<RoadLayers> roadLayers) {
        this.roadLayers = roadLayers;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RoadConstraction\n");
        sb.append("Дорожньо-кліматична зона України ").append(rbcz.getName()).append("\n");
        sb.append("Категорія дороги ").append(road.getName()).append("\n");
        sb.append("Тип дорожнього одягу ").append(roadType.getName()).append("\n");
        sb.append("Група розрахункового навантаження ").append(desigionLoad.getName()).append("\n");
        sb.append("Вид грунтів ").append(soils.getName()).append("\n");
        sb.append("Вид грунту ").append(groundType.getName()).append("\n");
        sb.append("Різновид глинистих грунтів ").append(ground.getName()).append("\n");
        sb.append("Кількість суг руху ").append(roadLines.getName()).append("\n");
        sb.append("Дренажна конструкція ").append(groundCorection.getName()).append("\n");
        sb.append("Вологість грунту ").append(humidity).append("\n");
        sb.append("Поправка на конструктивні особливості дренажної конструкції ").append(correction).append("\n");
        sb.append("Строк експлуатації до капітального ремонту ").append(operationTime).append(" років \n");
        sb.append("Сумарна кількість проїздів розрахункового навантаження за строк служби ").append(passageNumber).append("\n\n");
        if(bituminous.size()>0) {
            sb.append("Шари асфальтобетонів: \n");
            for (Bituminous bituminous : bituminous) {
                sb.append(bituminous.getName()).append("товщина  " + bituminous.getThickness() + " \n");
            }
        }
        if(strengthenedMaterials.size()>0) {
            sb.append("\nШари матеріалів і грунтів укрвплених в'яжучими речовинами: \n");
            for (StrengthenedMaterial strengthenedMaterial : strengthenedMaterials) {
                sb.append(strengthenedMaterial.getName()).append(" товщина " + strengthenedMaterial.getThickness() + " \n");
            }
        }
        if(unstrengthenedMaterialsCover.size()>0) {
            sb.append("\nШари покриття з матеріалів і грунтів неукрвплених в'яжучими речовинами: \n");
            for (UnstrengthenedMaterial unstrengthenedMaterial : unstrengthenedMaterialsCover) {
                sb.append(unstrengthenedMaterial.getName()).append(" товщина " + unstrengthenedMaterial.getThickness() + " \n");
            }
        }
        if(unstrengthenedMaterialsBase.size()>0) {
            sb.append("\nШари основи з матеріалів і грунтів неукрвплених в'яжучими речовинами: \n");
            for (UnstrengthenedMaterial unstrengthenedMaterial : unstrengthenedMaterialsBase) {
                sb.append(unstrengthenedMaterial.getName()).append(" товщина " + unstrengthenedMaterial.getThickness() + " \n");
            }
        }
        if(sands.size()>0) {
            sb.append("\nШари піску: \n");
            for (Sand sand : sands) {
                sb.append(sand.getName()).append(" товщина " + sand.getThickness() + " \n");
            }
        }
        return sb.toString();
    }

    // метод вибору вологості грунту
    private void humidityChoose(){
        if(getRoad().getName().equals("I-a") || getRoad().getName().equals("I-b") || getRoad().getName().equals("II")){
            setHumidity(getGroundType().getHumidity_1_2());
        }
        else if(getRoad().getName().equals("III") || getRoad().getName().equals("IV")){
            setHumidity(getGroundType().getHumidity_3_4());
        }
        else if(getRoad().getName().equals("V")){
            setHumidity(getGroundType().getHumidity_5());
        }
        else{
            System.out.println("Error");
        }
    }

    // метод вибору поправки на конструктивні особливості дренажної конструкції
    private void correctionChoose(){
        if(getRoad().getName().equals("I-a") || getRoad().getName().equals("I-b")){
            setCorrection(getGroundCorection().getCorrection1());
        }
        else if(getRoad().getName().equals("II")){
            setCorrection(getGroundCorection().getCorrection2());
        }
        else if(getRoad().getName().equals("III")){
            setCorrection(getGroundCorection().getCorrection3());
        }
        else if(getRoad().getName().equals("IV")){
            setCorrection(getGroundCorection().getCorrection4());
        }
        else if(getRoad().getName().equals("V")){
            setCorrection(0.0);
        }
        else{
            System.out.println("Error");
        }
    }

//    метод формування списку - записів шарів покриття для відображення в фронт-складовій програми
    public ArrayList<String> layerTableStringList(){
        ArrayList<String> list = new ArrayList<>();
        for(LayerT el: layerTableList()){
            list.add(new String(el.getId() + " " + el.getType() + " " + el.getConstruction() + " " + el.getThickness()));
        }
        return list;
    }

// метод для визначення типу шару дорожнього одягу
    public RoadLayers roadLayersCheck(String layerType){
        RoadLayers roadLayers = new RoadLayers();
        for (RoadLayers el: Lists.getRoadLayers()){
            if(el.getName().equals(layerType)){
                roadLayers = el;
            }
        }
        return roadLayers;
    }
    // метод для формування списку шарів конкретного типу шару дорожнього одягу
    public ArrayList<String> positionCheck(String layerType){
        ArrayList<String> positionList = new ArrayList<>();
        for(LayerT el: layerTableList()){
            if(el.getType().equals(layerType)){
                positionList.add(el.getId().toString());
            }
        }
        return positionList;
    }

//    метод для формування списку смуг руху
    public ArrayList<RoadLines> checkRoadLines(ArrayList<Integer> roadLines){
        ArrayList<RoadLines> checkedRoadLines = new ArrayList<>();
        for(RoadLines lines: Lists.getRoadLines()){
            for (Integer element: roadLines){
                if(lines.getNumberLines().equals(element)){
                    checkedRoadLines.add(lines);
                }
            }
        }
        return checkedRoadLines;
    }

    //    метод формування список шарів покриття
    public void layers(){
        roadLayers = new ArrayList<>();
        roadLayers.add(new RoadLayers("Асфальтобетон", bituminous));
        roadLayers.add(new RoadLayers("Матеріали і грунти укріплені в'яжучими речовинами", strengthenedMaterials));
        roadLayers.add(new RoadLayers("Неукріплені матеріали покриття",unstrengthenedMaterialsCover));
        roadLayers.add(new RoadLayers("Неукріплені матеріали основ",unstrengthenedMaterialsBase));
        roadLayers.add(new RoadLayers("Шар нев'язкого середовища",sands));
    }

    // метод формування списку обєктів - шарів покриття для відображення в фронт-складовій програми
    public ArrayList<LayerT> layerTableList(){
        ArrayList<LayerT> layers = new ArrayList<>();
        int temp =1;
        for(RoadLayers roadLayers: roadLayers){
            for(Layer el:roadLayers.getLayers()){
                layers.add(new LayerT(temp,roadLayers.getName(),el.getName() + " " + el.getMaterial(), el.getThickness()));
                temp++;
            }
        }
        return layers;
    }

}