package view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import model.DataStruct;
import outliers.StatisticTest;
import outliers.StatisticTestImp;

public class MainWindowController implements Initializable {
	private DataStruct data;
	private DataStruct processedData;
	private StatisticTest test;
	private volatile File file;
	private int numberOfRows;
	@FXML
	private TextArea messages;
	@FXML
	private TextField numberOfRowsToRemove;
	@FXML
	private Button saveButton;

	// Event Listener on Button.onAction
	@FXML
	public void handleLoad(ActionEvent event) {
		chooseFile();
	}

	// Event Listener on Button.onAction
	@FXML
	public void handleSave(ActionEvent event) {
		saveFile();
	}

	public void handleActionTextField(String newValue) {
		try {
			numberOfRows = Integer.parseInt(newValue);
		} catch (Exception ex) {
			showErrorMessage("Wpisana wartość musi być liczbą. ");
		}
	}

	private void chooseFile() {
		try {
			numberOfRows = Integer.parseInt(numberOfRowsToRemove.getText());
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("OtwÃ³rz plik");
			fileChooser.getExtensionFilters()
					.addAll(new FileChooser.ExtensionFilter("Pliki *.csv rozdzielone średnikiem", "*.csv"));
			addMessageToTextArea("Wczytywanie ...");
			file = fileChooser.showOpenDialog(null);
			data = new DataStruct();
			processedData = new DataStruct();
			test = new StatisticTestImp();
			if (file == null) {
				Platform.runLater(() -> {
					addMessageToTextArea("Wczytywanie zostało anulowane. ");
				});

			} else {
				new Thread(() -> {
					try {
						data.setData(test.readCsvFile(file.getAbsolutePath()));
						processedData.setData(test.removeOutliersFromFile(numberOfRows, data.getData()));
						Platform.runLater(() -> {
							addMessageToTextArea("Plik '" + file.getName() + "' został wczytany. ");
							saveButton.setDisable(false);
						});
					} catch (IOException e) {
						Platform.runLater(() -> {
							showErrorMessage(e.getMessage());
							addMessageToTextArea(e.getMessage());
						});
					}
				}).start();
			}
		} catch (NumberFormatException ex) {
			addMessageToTextArea("Wpisano niepoprawną ilość wierszy do usuniącia. ");
			addMessageToTextArea("Wczytywanie zostaÅ‚o anulowane. ");
		}
	}

	private void saveFile() {
		addMessageToTextArea("Zapisywanie danych. ");
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Zapisz jako...");
		File fileToSave = fileChooser.showSaveDialog(null);
		if (fileToSave != null) {
			addMessageToTextArea("Zapisywanie ... ");
			new Thread(() -> {
				try {
					test.saveFile(processedData.getData(), fileToSave.getAbsolutePath() + ".csv");
					Platform.runLater(() -> {
						addMessageToTextArea("Plik '" + fileToSave.getName() + ".csv' został zapisany. ");
					});
				} catch (IOException ex) {
					Platform.runLater(() -> {
						addMessageToTextArea(ex.getMessage());
						showErrorMessage(ex.getMessage());
					});
				}
			}).start();
		} else {
			addMessageToTextArea("Zapisywanie zostało anulowane. ");
		}
	}

	private void showErrorMessage(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Błąd");
		alert.setHeaderText(message);
		alert.showAndWait();
	}

	private void addMessageToTextArea(String message) {
		messages.setText(messages.getText() + message + "\n");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		numberOfRowsToRemove.textProperty().addListener((e) -> {
			handleActionTextField(numberOfRowsToRemove.getText());
		});
		numberOfRows = Integer.parseInt(numberOfRowsToRemove.getText());
	}
}
