package application.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.controlsfx.control.Notifications;
import application.MainFXMLController;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.util.Duration;

public class MUtils {
	MainFXMLController main;

	public static boolean IsNumber(String number)
	{
		Pattern p = Pattern.compile("[\\d.]+");
		Matcher m = p.matcher(number);
		return m.matches();
	}
	public static boolean IsInteger(String number)
	{
		Pattern p = Pattern.compile("[\\d]+");
		Matcher m = p.matcher(number);
		
		return m.matches();
	}
	public static boolean IsString(String str)
	{
		Pattern p = Pattern.compile("^[\\p{L} .'-]+$");
		Matcher m = p.matcher(str);
		return m.matches();
	}
	
	public static boolean showConfirmMessage(String title , String contnent)
	{
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
		alert.setTitle(title);
		alert.setHeaderText(title);
		alert.setContentText(contnent);
		ButtonType okButton = new ButtonType("„Ê«›ﬁ");
		ButtonType cancelButton = new ButtonType("—ÃÊ⁄");
		alert.getButtonTypes().setAll(okButton , cancelButton);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == okButton)
			return true;
		else
			return false;		
	}
	public static void showErrorMessage(String title , String contnent)
	{
		Alert alert = new Alert(AlertType.ERROR);
		alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
		ButtonType okButton = new ButtonType("Õ”‰«");
		alert.getButtonTypes().setAll(okButton);
		alert.setTitle(title);
		alert.setHeaderText(title);
		alert.setContentText(contnent);
		alert.showAndWait();
	}
	public static boolean showWarningMessage(String title , String contnent)
	{
		Alert alert = new Alert(AlertType.WARNING);
		alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
		alert.setTitle(title);
		alert.setHeaderText(title);
		alert.setContentText(contnent);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK)
			return true;
		else
			return false;
	}
	
	public static void notification(String title,String text)
	{
		Notifications  not = Notifications.create();
		not.text(text);
		not.title(title);
		not.hideAfter(Duration.seconds(5));
		not.position(Pos.BOTTOM_RIGHT);
		not.graphic(null);
		not.showInformation();
	}
	
	private static int generator = 0;
	public static String generateNewPhotoName()
	{
		generator++;
		String newString = "src/application/temp/photo" + generator + ".jpg";
		return newString;
	}

	
	public static void setDuration(int duration){
		File dur = new File("src/application/utils/duration.bin");
		try {
			PrintWriter print = new PrintWriter(dur);
			print.write("" + duration);
			print.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
	}

	public static int getDuration(){
		File dur = new File("src/application/utils/duration.bin");
		try {
			Scanner scan = new Scanner(dur);
			int d = Integer.parseInt(scan.next());
			scan.close();
			return d;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static void databaseEncryption(){
		final File data = new File("src/Clinic.sqlite");
		ArrayList<Byte> bytes = new ArrayList<>();
		Scanner scan  = null;
		PrintWriter print  = null;
		try {
			scan = new Scanner(data);
			print = new PrintWriter(data);
			while(scan.hasNextLine())
			{
				bytes.add(scan.nextByte());
			}
			int counter = 0;
			while(counter == bytes.size()){
				byte b = bytes.get(counter).byteValue();
				b++;
				bytes.set(counter, b);
				counter++;
			}
			counter = 0;
			while(counter == bytes.size()){
				String line = bytes.get(counter).toString();
				print.print(line);
				counter++;
			}
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		finally{ scan.close(); print.close(); }
	}
	public static void databaseDecryption(){
		final File data = new File("src/Clinic.sqlite");
		ArrayList<Byte> bytes = new ArrayList<>();
		Scanner scan  = null;
		PrintWriter print  = null;
		try {
			scan = new Scanner(data);
			print = new PrintWriter(data);
			while(scan.hasNextLine())
			{
				bytes.add(scan.nextByte());
			}
			int counter = 0;
			while(counter == bytes.size()){
				byte b = bytes.get(counter).byteValue();
				b++;
				bytes.set(counter, b);
				counter++;
			}
			counter = 0;
			while(counter == bytes.size()){
				String line = bytes.get(counter).toString();
				print.print(line);
				counter++;
			}
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		finally{ scan.close(); print.close(); }
	}
	
}
