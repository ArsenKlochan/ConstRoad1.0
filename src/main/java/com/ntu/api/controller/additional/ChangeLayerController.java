package com.ntu.api.controller.additional;

import com.ntu.api.controller.main.InputController;
import com.ntu.api.domain.LayerT;
import com.ntu.api.domain.Lists;
import com.ntu.api.domain.Message;
import com.ntu.api.domain.RoadConstraction;
import com.ntu.api.domain.listCreate.Objects.Layers.*;
import com.ntu.api.model.RoadConstractionModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.ArrayList;

public class ChangeLayerController {
    @FXML
    AnchorPane changeLayerPane;
    @FXML
    ComboBox<String> layerConstractionBox;
    @FXML
    ComboBox<String> layerDepthBox;
    @FXML
    ComboBox<String> layerBox;
    @FXML
    TextArea layerType;
    @FXML
    ComboBox<String> layerPositionBox;

    private static ObservableList<String> layerList;
    private static ObservableList<String> layerConstractionList;
    private static ObservableList<String> layerDepthList;
    private static ObservableList<String> layerPositionList;
    private static Layer tempLayer;

    private static Integer layerPosition;
    private static Integer oldlayerPosition;
    private static ArrayList<LayerT> layerTableList;

    private RoadConstraction roadConstraction = RoadConstractionModel.getRoadConstraction();

    @FXML public void initialize(){
        layerList = FXCollections.observableArrayList();
        layerConstractionList = FXCollections.observableArrayList();
        layerDepthList = FXCollections.observableArrayList();
        layerPositionList = FXCollections.observableArrayList();

        layerList.addAll(roadConstraction.layerTableStringList());

        layerBox.setEditable(false);
        layerConstractionBox.setEditable(false);
        layerDepthBox.setEditable(true);
        layerPositionBox.setEditable(false);

        layerBox.getItems().setAll(layerList);
        layerType.setEditable(false);
        layerTableList = roadConstraction.layerTableList();
    }

    @FXML private void layerOnClick(){
        try {
            layerType.setText("");
            boxClear(layerConstractionBox);
            boxClear(layerDepthBox);
            boxClear(layerPositionBox);

            layerType.setText(layerTableList.get(layerBox.getSelectionModel().getSelectedIndex()).getType());
            layerConstractionBox.setPromptText(layerTableList.get(layerBox.getSelectionModel().getSelectedIndex()).getConstruction());
            layerConstractionBox.getItems().setAll(Lists.getLayersList().get(Lists.getRoadLayers().indexOf(roadConstraction.roadLayersCheck(layerType.getText()))));
            layerDepthBox.setPromptText(layerTableList.get(layerBox.getSelectionModel().getSelectedIndex()).getThickness().toString());
            layerDepthBox.getItems().setAll(depthCheck0());

            oldlayerPosition = layerTableList.get(layerBox.getSelectionModel().getSelectedIndex()).getId();
            layerPositionBox.setPromptText(oldlayerPosition.toString());
            layerPositionBox.getItems().setAll(roadConstraction.positionCheck(layerType.getText()));
            layerPosition = Integer.parseInt(layerPositionBox.getPromptText());

            tempLayer = layerChoose();
        }
        catch (RuntimeException e){}
    }

    @FXML private void layerConstractionOnClick(){
        try{
        boxClear(layerDepthBox);
        layerConstractionBox.setPromptText(Lists.getRoadLayers().get(layerBox.getSelectionModel().getSelectedIndex()).getLayers().get(layerConstractionBox.getSelectionModel().getSelectedIndex()).toString());
        layerDepthBox.getItems().setAll(depthCheck());
        layerPositionBox.getItems().setAll(roadConstraction.positionCheck(layerType.getText()));
        tempLayer = layerCheck();}
        catch (RuntimeException e){}
    }

    @FXML private void layerDepthOnClick(){
        try{
        layerDepthBox.setPromptText(layerTableList.get(layerBox.getSelectionModel().getSelectedIndex()).getThickness().toString());
        tempLayer.setThickness(Double.parseDouble(layerDepthBox.getSelectionModel().getSelectedItem())*0.01);
        }
        catch (RuntimeException e){}
    }

    @FXML private void layerPositionOnClick(){
        layerPositionBox.setPromptText(layerTableList.get(layerBox.getSelectionModel().getSelectedIndex()).getId().toString());
        layerPosition = Integer.parseInt(layerPositionBox.getSelectionModel().getSelectedItem());
    }

    @FXML private void moveUpOnClick(){
        layerPosition = Integer.parseInt(layerPositionBox.getPromptText());
            if(layerPosition>Integer.parseInt(roadConstraction.positionCheck(layerType.getText()).get(0))){
                layerPosition--;
                saveOnClick(true);
            }
    }

    @FXML private void moveDownOnClick(){
        layerPosition = Integer.parseInt(layerPositionBox.getPromptText());
        if(layerPosition<Integer.parseInt(roadConstraction.positionCheck(layerType.getText()).get(roadConstraction.positionCheck(layerType.getText()).size()-1))){
            layerPosition++;
            saveOnClick(true);
        }
    }

    @FXML private void saveOnClick(){
        if(oldlayerPosition == Integer.parseInt(layerPosition.toString())){
            saveOnClick(false);
        }
        else {
            saveOnClick(true);
        }
    }

    private void saveOnClick(boolean bool){
        try {
            if(layerDepthBox.getValue()==null&&layerDepthBox.promptTextProperty().getValue()==null) {
                Message.errorCatch(changeLayerPane, "Error", "Введіть товщину шару дорожнього одягу.");
            }
            else{
                if (tempLayer.getThickness() >= tempLayer.getMinThickness()*0.01 && tempLayer.getThickness() <= tempLayer.getMaxThickness()*0.01) {
                    int roadLayerskod = Lists.getRoadLayers().indexOf(roadConstraction.roadLayersCheck(layerType.getText()));
                    ArrayList<String> position = roadConstraction.positionCheck(layerType.getText());
                    int oldIndex = position.indexOf(oldlayerPosition.toString());
                    if (bool) {
                        roadConstraction.getRoadLayers().get(roadLayerskod).getLayers().remove(oldIndex);
                        int newIndex = roadConstraction.positionCheck(layerType.getText()).indexOf(layerPosition.toString());
                        if (roadLayerskod == 0) {
                            tempLayer.setThicknessVariationCoeficient(2.84 / (10.2 + tempLayer.getThickness()));
                            Bituminous tempBitum = (Bituminous) tempLayer;
                            tempBitum.setElasticModuleDeflection((double)tempBitum.getShortElasticModuls().get(0).getElasticModule());
                            tempBitum.setElasticModuleMovement(InputController.elasticModuleMovementChoose(tempBitum));
                            roadConstraction.getBituminous().add(newIndex, tempBitum);
                        } else {
                            tempLayer.setThicknessVariationCoeficient(2.84 / (5.6 + tempLayer.getThickness()));
                            if (roadLayerskod == 1) {
                                StrengthenedMaterial tempStrengthMaterial = (StrengthenedMaterial) tempLayer;
                                tempStrengthMaterial.setElasticModuleDeflection((double)tempStrengthMaterial.getElasticityModul());
                                tempStrengthMaterial.setElasticModuleMovement((double)tempStrengthMaterial.getElasticityModul());
                                roadConstraction.getStrengthenedMaterials().add(newIndex, (StrengthenedMaterial) tempStrengthMaterial);
                            } else if (roadLayerskod == 2) {
                                UnstrengthenedMaterial tempUnstrengthenedMaterial = (UnstrengthenedMaterial)tempLayer;
                                tempUnstrengthenedMaterial.setElasticModuleDeflection((double)tempUnstrengthenedMaterial.getElasticityModul());
                                tempUnstrengthenedMaterial.setElasticModuleMovement((double)tempUnstrengthenedMaterial.getElasticityModul());
                                roadConstraction.getUnstrengthenedMaterialsCover().add(newIndex, (UnstrengthenedMaterial) tempUnstrengthenedMaterial);
                            } else if (roadLayerskod == 3) {
                                UnstrengthenedMaterial tempUnstrengthenedMaterial = (UnstrengthenedMaterial)tempLayer;
                                tempUnstrengthenedMaterial.setElasticModuleDeflection((double)tempUnstrengthenedMaterial.getElasticityModul());
                                tempUnstrengthenedMaterial.setElasticModuleMovement((double)tempUnstrengthenedMaterial.getElasticityModul());
                                roadConstraction.getUnstrengthenedMaterialsBase().add(newIndex, (UnstrengthenedMaterial) tempUnstrengthenedMaterial);
                            } else if (roadLayerskod == 4) {
                                roadConstraction.getSands().add(newIndex, (Sand) tempLayer);
                            }
                        }

                    } else {
                        if (roadLayerskod == 0) {
                            tempLayer.setThicknessVariationCoeficient(2.84 / (10.2 + tempLayer.getThickness()));
                            Bituminous tempBitum = (Bituminous) tempLayer;
                            tempBitum.setElasticModuleDeflection((double)tempBitum.getShortElasticModuls().get(0).getElasticModule());
                            tempBitum.setElasticModuleMovement(InputController.elasticModuleMovementChoose(tempBitum));
                            roadConstraction.getBituminous().set(oldIndex, tempBitum);
                        } else {
                            tempLayer.setThicknessVariationCoeficient(2.84 / (5.6 + tempLayer.getThickness()));
                            if (roadLayerskod == 1) {
                                StrengthenedMaterial tempStrengthMaterial = (StrengthenedMaterial) tempLayer;
                                tempStrengthMaterial.setElasticModuleDeflection((double)tempStrengthMaterial.getElasticityModul());
                                tempStrengthMaterial.setElasticModuleMovement((double)tempStrengthMaterial.getElasticityModul());
                                roadConstraction.getStrengthenedMaterials().set(oldIndex, tempStrengthMaterial);
                            } else if (roadLayerskod == 2) {
                                UnstrengthenedMaterial tempUnstrengthenedMaterial = (UnstrengthenedMaterial)tempLayer;
                                tempUnstrengthenedMaterial.setElasticModuleDeflection((double)tempUnstrengthenedMaterial.getElasticityModul());
                                tempUnstrengthenedMaterial.setElasticModuleMovement((double)tempUnstrengthenedMaterial.getElasticityModul());
                                roadConstraction.getUnstrengthenedMaterialsCover().set(oldIndex, tempUnstrengthenedMaterial);
                            } else if (roadLayerskod == 3) {
                                UnstrengthenedMaterial tempUnstrengthenedMaterial = (UnstrengthenedMaterial)tempLayer;
                                tempUnstrengthenedMaterial.setElasticModuleDeflection((double)tempUnstrengthenedMaterial.getElasticityModul());
                                tempUnstrengthenedMaterial.setElasticModuleMovement((double)tempUnstrengthenedMaterial.getElasticityModul());
                                roadConstraction.getUnstrengthenedMaterialsBase().set(oldIndex, tempUnstrengthenedMaterial);
                            } else if (roadLayerskod == 4) {
                                roadConstraction.getSands().set(oldIndex, (Sand) tempLayer);
                            }
                        }
                    }
                    roadConstraction.layers();
                    okOnClick();
                } else {
                    boxClear(layerDepthBox);
                    Message.errorCatch(changeLayerPane, "Error", "Введена товщина шару дорожнього одягу є " +
                            "недопустимою для даної конструкції шару дорожнього одягу.");
                }
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    @FXML private void cancelOnClick(){
        Stage dlg = (Stage)(changeLayerPane.getScene().getWindow());
        dlg.close();
    }

    private Layer layerCheck(){
        return roadConstraction.roadLayersCheck(layerType.getText()).getLayers().get(layerConstractionBox.getSelectionModel().getSelectedIndex());
    }

    private ArrayList<String> depthCheck(){
        return RoadConstractionModel.depthList(layerCheck().getMinThickness(), layerCheck().getMaxThickness());
    }

    private ArrayList<String> depthCheck0(){
        for(Layer el: roadConstraction.roadLayersCheck(layerType.getText()).getLayers()){
            if(new String(el.getName() + " " +el.getMaterial()).equals(layerConstractionBox.getPromptText())){
                Layer layer = roadConstraction.roadLayersCheck(layerType.getText()).getLayers().get(roadConstraction.roadLayersCheck(layerType.getText()).getLayers().indexOf(el));
                return RoadConstractionModel.depthList(layer.getMinThickness(), layer.getMaxThickness());
            }
        }
        return new ArrayList<>();
    }

    private Layer layerChoose(){
        Layer layer = new Layer();
        for(Layer layer1: roadConstraction.roadLayersCheck(layerType.getText()).getLayers()){
            if(new String(layer1.getName() + " " + layer1.getMaterial()).equals(layerConstractionBox.getPromptText())){
                layer = layer1;
            }
        }
        return layer;
    }

    private void boxClear(ComboBox<String> box){
        box.promptTextProperty().setValue(null);
        box.setValue(null);
    }

    public void okOnClick(){
        try{
            Stage dlg = (Stage)(changeLayerPane.getScene().getWindow());
            dlg.getOnCloseRequest().handle(new WindowEvent(dlg, WindowEvent.WINDOW_CLOSE_REQUEST));
            dlg.close();}
        catch (RuntimeException e){}
    }

}
