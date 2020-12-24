package application;
	
import java.io.File;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;


public class Main extends Application {
	static int m1 = 0;
	@Override
	public void start(Stage primaryStage) {
		try {
			//BorderPane root = new BorderPane();
			BorderPane borp = new BorderPane();  // крестообразная панель
			Scene scene = new Scene(borp,400,400);
			
			Label lb_time = new Label ("00" +":" + "00"); // для таймера;
			GridPane grp = new GridPane();   // табличнное расположение
			FlowPane flp = new FlowPane(3,3); //горизонтальная панель (если не поменять)
			AnchorPane anp = new AnchorPane(); // якорная панель
			borp.setCenter(anp);
			borp.setTop(flp);
			borp.setRight(grp);
			borp.setBottom(lb_time);
			
			// Control; эл.управ.
			Label lb_dav = new Label("Давление");
			TextField tf_dav = new TextField();
			tf_dav.setMinWidth(20.0);
			tf_dav.setMaxWidth(50.0);
			Label lb_kol = new Label("кол-во бал.:");
			TextField tf_kol = new TextField();
			tf_kol.setMinWidth(20.0);
			tf_kol.setMaxWidth(30.0);
			Label lb_V = new Label("об. бал.:");
			RadioButton rd_7 = new RadioButton("7");
			RadioButton rd_4 = new RadioButton("4");
			ToggleGroup group = new ToggleGroup();// создание группы для radiobutton;
			
			rd_7.setToggleGroup(group);//запихали в group;
			rd_4.setToggleGroup(group);
			
			//
			
			TextArea txa_otchet = new TextArea("Отчет:\n");
			
			//
			
			Button bt_raschet = new Button("Расчет");
			
			/*
			 * Попытка задать лаготип и иконку
			 * File file = new File("/resources/emb.png");
			//String localUrl = file.toURI().toURL().toString();
			
			//InputStream inst =  getClass().getResourceAsStream("file:/resources/emb.png"); // включаем поток по чтению ресурса
			 */
			Image image1 = new Image("file:///C:/Users/ADM/eclipse-workspace/Gss_vozduh/resources/gss_emb.png"); //загруженное изображение
			ImageView imV = new ImageView(image1); // создается представление изображения
			imV.setFitHeight(50);
			imV.setFitWidth(50);
			//Image imageOk = new Image(getClass().getResourceAsStream("/resources/emb.png"));
			//Button buttonOk = new Button("OK", );
			//bt_raschet.setGraphic(new ImageView(imageOk));   //graphicProperty().setValue(imV); // метод добавления иконки в созданную кнопку
			
			Label lb_oc = new Label("обнар.цель");
			Button bt_date = new Button("");
			bt_date.setMinWidth(50.0);
			bt_date.setGraphic(imV);
			bt_date.setStyle("-fx-border-radius: 8 ;-fx-background-radius: 8; "
					+ "-fx-border-color: #00FFFF; -fx-border-width: 3px;");
			Label lb_davob = new Label("давл. при обнар.:");
			TextField tf_davob = new TextField();
			tf_davob.setMaxWidth(50.0);
			tf_davob.setMinWidth(20.0);
			Button bt_raschet2 = new Button("расчет после обнар.");
			
			//
			
			
			
			//размещение;
			flp.getChildren().addAll(lb_dav,tf_dav,lb_kol,tf_kol,lb_V,rd_7,rd_4);
			//
			
			anp.getChildren().add(txa_otchet);
			AnchorPane.setRightAnchor(txa_otchet, 5.0);
			AnchorPane.setLeftAnchor(txa_otchet, 5.0);
			AnchorPane.setTopAnchor(txa_otchet, 5.0);
			//
			
			grp.add(bt_raschet,1,0);
			grp.add(lb_oc, 0, 1);
			grp.add(bt_date, 1, 1);
			grp.add(lb_davob, 0, 2);
			grp.add(tf_davob, 1, 2);
			grp.add(bt_raschet2, 1, 3);
			
			//
			
			
			
			//обработка действий;
			
			Threadtask tt = new Threadtask ();
			
			Gss_raschet gss = new Gss_raschet();
			
			group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() { // обработка события RadioButton;

				@Override
				public void changed(ObservableValue<? extends Toggle> arg0, Toggle arg1, Toggle arg2) {
					if (group.getSelectedToggle() != null) {
						
		                   RadioButton button = (RadioButton) group.getSelectedToggle();
		                   //System.out.println(button.getText());
		                   gss.setOb_bal(Integer.parseInt(button.getText()));
		               }
					// TODO Auto-generated method stub
					
				}
				
			});
			
			bt_raschet.setOnAction(new EventHandler<ActionEvent>() {  // обработка события Button;
				
				
				public void handle (ActionEvent arg0) {
					
					gss.setBal(Integer.parseInt( tf_kol.getText() ));
					gss.setDav(Integer.parseInt( tf_dav.getText() ));
					
					txa_otchet.appendText("Давление выхода:  "+gss.dav_Vihoda()+" При давлении: "+gss.getDav()+"\n");
					txa_otchet.appendText("Т общ.:"+gss.t_obshee()+"\n");
					m1 = gss.dvig();
					txa_otchet.appendText("Т разведки:"+gss.dvig()+"\n");
					
					new Thread(tt).start();
					
				}

			});
			lb_time.textProperty().bind(tt.messageProperty());
			
			bt_raschet2.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent arg0) {
					tt.cancel();
					gss.dav_ob = Integer.parseInt(tf_davob.getText());
					
					txa_otchet.appendText("\n"+"Давление разведки: "+gss.dav_raz()+"\n");
					txa_otchet.appendText("Давление выхода: "+gss.dav_vihoda2()+"\n");
					txa_otchet.appendText("Т работ: "+gss.t_rabot()+"\n");
					m1 = gss.t_rabot();
					Threadtask tt1 = new Threadtask();
					new Thread(tt1).start();
					lb_time.textProperty().bind(tt1.messageProperty());
					// TODO Auto-generated method stub
					
				}
			});
			
			
			bt_date.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent arg0) {
					txa_otchet.appendText("\n"+"Обнаружена цель:"+"\n"+gss.gss_date()+"\n");
					// TODO Auto-generated method stub
					
				}
			});
			
			
			
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Расчет воздуха ГСС");
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.getIcons().add(image1);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
}
