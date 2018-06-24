package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Main extends Application {

    HBox addBox;
    HBox tablePaneContainer;
    VBox vBox, addSubjectBox, addTeacherBox, addRoomBox, addPeriodBox;
    TableView<ClassSession> mondayTable, tuesdayTable, wednesdayTable, thursdayTable, fridayTable;
    TableColumn monCol, tueCol, wedCol, thuCol, friCol;
    Button addTeacher, addSubject, addPeriod, addRoom, generate;
    Label addTeachersLabel, addRoomsLabel, addSubjectsLabel, addPeriodsLabel;
    Text errorMessage = new Text();

    Subject mostconsSubject;
    Room justTakenRoom;

    ObservableList<ClassSession> monday = FXCollections.observableArrayList();
    ObservableList<ClassSession> tuesday = FXCollections.observableArrayList();
    ObservableList<ClassSession> wednesday = FXCollections.observableArrayList();
    ObservableList<ClassSession> thursday = FXCollections.observableArrayList();
    ObservableList<ClassSession> friday = FXCollections.observableArrayList();

    int numberOfTurns= 0;

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    private File teachersFile;
    private File subjectsFile;
    private File roomsFile;
    private File periodsFile;

    private ArrayList<Subject> untreatedSubjects = new ArrayList<>();
    private ArrayList<Subject> treatedSubjects = new ArrayList<>();

    private ArrayList<Period> nontakenPeriods = new ArrayList<>();
    private ArrayList<Period> takenPeriods = new ArrayList<>();

    private ArrayList<Teacher> teachers = new ArrayList<>();
    private ArrayList<ClassSession> sessions = new ArrayList<>();
    private ArrayList<Room> rooms = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws Exception{


        vBox = new VBox(5);
        tablePaneContainer = new HBox(0);
        addBox = new HBox(10);

        mondayTable = new TableView();
        tuesdayTable = new TableView();
        wednesdayTable = new TableView();
        thursdayTable = new TableView();
        fridayTable = new TableView();

        mondayTable.setEditable(true);
        tuesdayTable.setEditable(true);
        wednesdayTable.setEditable(true);
        thursdayTable.setEditable(true);
        fridayTable.setEditable(true);

        monCol = new TableColumn("Monday");
        monCol.setMinWidth(100);
        monCol.setPrefWidth(200);
        monCol.setCellValueFactory(new PropertyValueFactory<ClassSession, String>("description"));

        tueCol = new TableColumn("Tuesday");
        tueCol.setMinWidth(100);
        tueCol.setPrefWidth(200);
        tueCol.setCellValueFactory(new PropertyValueFactory<ClassSession, String>("description"));

        wedCol = new TableColumn("Wednesday");
        wedCol.setMinWidth(100);
        wedCol.setPrefWidth(200);
        wedCol.setCellValueFactory(new PropertyValueFactory<ClassSession, String>("description"));

        thuCol = new TableColumn("Thursday");
        thuCol.setMinWidth(100);
        thuCol.setPrefWidth(200);
        thuCol.setCellValueFactory(new PropertyValueFactory<ClassSession, String>("description"));

        friCol = new TableColumn("Friday");
        friCol.setMinWidth(100);
        friCol.setPrefWidth(200);
        friCol.setCellValueFactory(new PropertyValueFactory<ClassSession, String>("description"));

        addTeachersLabel = new Label();
        addRoomsLabel = new Label();
        addSubjectsLabel = new Label();
        addPeriodsLabel = new Label();

        addTeacherBox = new VBox(5);
        addRoomBox = new VBox(5);
        addSubjectBox = new VBox(5);
        addPeriodBox = new VBox(5);

        addTeacher = new Button("Add Teachers");
        addTeachersLabel.setLabelFor(addTeacher);
        addTeacherBox.getChildren().addAll(addTeacher, addTeachersLabel);

        addSubject = new Button("Add Courses");
        addSubjectsLabel.setLabelFor(addSubject);
        addSubjectBox.getChildren().addAll(addSubject, addSubjectsLabel);

        addRoom = new Button("Add Rooms");
        addRoomsLabel.setLabelFor(addRoom);
        addRoomBox.getChildren().addAll(addRoom, addRoomsLabel);

        addPeriod = new Button("Add Periods");
        addPeriodsLabel.setLabelFor(addPeriod);
        addPeriodBox.getChildren().addAll(addPeriod, addPeriodsLabel);

        generate = new Button("Generate Timetable");

        errorMessage.setFont(Font.font(20));
        errorMessage.setFill(Color.RED);

        vBox.setPrefSize(1200, 800);
        vBox.setMinSize(400, 100);
        vBox.setStyle("-fx-padding: 10");

        addBox.setStyle("-fx-padding: 10");
        addBox.prefWidthProperty().bind(vBox.widthProperty());
        addBox.prefHeightProperty().bind(vBox.heightProperty().divide(10));

        mondayTable.setItems(monday);
        tuesdayTable.setItems(tuesday);
        wednesdayTable.setItems(wednesday);
        thursdayTable.setItems(thursday);
        fridayTable.setItems(friday);

        mondayTable.getColumns().add(monCol);
        tuesdayTable.getColumns().add(tueCol);
        wednesdayTable.getColumns().add(wedCol);
        thursdayTable.getColumns().add(thuCol);
        fridayTable.getColumns().add(friCol);

        mondayTable.getSortOrder().add(monCol);
        tuesdayTable.getSortOrder().add(tueCol);
        wednesdayTable.getSortOrder().add(wedCol);
        thursdayTable.getSortOrder().add(thuCol);
        fridayTable.getSortOrder().add(friCol);



        tablePaneContainer.getChildren().addAll(mondayTable, tuesdayTable, wednesdayTable,
                thursdayTable, fridayTable);

        addBox.getChildren().addAll(addTeacherBox, addSubjectBox, addPeriodBox, addRoomBox);

        vBox.getChildren().addAll(addBox, errorMessage, generate, tablePaneContainer);

        final FileChooser fileChooser = new FileChooser();

        addTeacher.setOnAction(event -> {
            configureFileChooser(fileChooser);
            fileChooser.setTitle("Add file containing the list of teachers");
            teachersFile = fileChooser.showOpenDialog(new Stage());
            addTeachersLabel.setText(teachersFile.getName());
        });


        addSubject.setOnAction(event -> {
            configureFileChooser(fileChooser);
            fileChooser.setTitle("Add file containing the list of Subjects");
            subjectsFile = fileChooser.showOpenDialog(new Stage());
            addSubjectsLabel.setText(subjectsFile.getName());
        });


        addRoom.setOnAction(event -> {
            configureFileChooser(fileChooser);
            fileChooser.setTitle("Add file containing the list of Rooms");
            roomsFile = fileChooser.showOpenDialog(new Stage());
            addRoomsLabel.setText(roomsFile.getName());
        });


        addPeriod.setOnAction(event -> {
            configureFileChooser(fileChooser);
            fileChooser.setTitle("Add file containing the list of Periods");
            periodsFile = fileChooser.showOpenDialog(new Stage());
            addPeriodsLabel.setText(periodsFile.getName());
        });

        generate.setOnAction(event -> {

            untreatedSubjects.clear();
            treatedSubjects.clear();
            nontakenPeriods.clear();
            takenPeriods.clear();
            sessions.clear();
            rooms.clear();
            teachers.clear();

            readTeachersFile();
            readSubjectsFile();
            readPeriodsFile();
            readRoomsFile();

            findMostConsSubject();
            generateTimetable(mostconsSubject);

        });

        primaryStage.setTitle("Time Table Generator");
        primaryStage.setScene(new Scene(vBox, 1200, 800));
        primaryStage.show();
    }

    private static void configureFileChooser(final FileChooser fileChooser) {
        fileChooser.getExtensionFilters().
                add(new FileChooser.ExtensionFilter("text", "*.txt"));
    }


    private void generateTimetable(Subject subject) {

        try {
            Subject currentSubject = subject;
            Teacher currentTeacher = subject.getTeacher();

            treatedSubjects.add(currentSubject);
            untreatedSubjects.remove(currentSubject);

            for (Period availablePeriod : currentSubject.getAvailablePeriods()) {

                updateRooms(availablePeriod, subject);

                currentTeacher.getUnavailable().add(new UnavailableTime(availablePeriod.getDay(),
                        availablePeriod.getStartTime(), availablePeriod.getEndTime()));

                int j = 0;

                for (Room room : currentSubject.getAvailableRooms()) {

                    sessions.add(new ClassSession(currentSubject, availablePeriod, room));
                    takenPeriods.add(availablePeriod);
                    nontakenPeriods.remove(availablePeriod);

                    findMostConsSubject();

                    if (untreatedSubjects.isEmpty())
                        return;

                    generateTimetable(mostconsSubject);

                    if (untreatedSubjects.isEmpty())
                        return;

                    sessions.remove(sessions.size() - 1);
                    nontakenPeriods.add(availablePeriod);
                    takenPeriods.remove(availablePeriod);
                }
                currentTeacher.getUnavailable().remove(currentTeacher.getUnavailable().size() - 1);
            }

            treatedSubjects.remove(currentSubject);
            untreatedSubjects.add(currentSubject);
        }catch (Exception e){

        }
    }
    private void findMostConsSubject(){
        try {
            mostconsSubject = untreatedSubjects.get(0);
            initialisePeriods();

            for (Subject subject: untreatedSubjects) {
                if (subject.getAvailablePeriods().size() < mostconsSubject.getAvailablePeriods().size())
                    mostconsSubject = subject;
            }

        }catch(IndexOutOfBoundsException exc){

            numberOfTurns++;
            ArrayList<Subject> temp = new ArrayList<>();
            for(Subject subject: treatedSubjects){
                if(subject.getFrequency() > numberOfTurns) {
                    temp.add(subject);
                }
            }

            for(Subject subject: temp){
                untreatedSubjects.add(subject);
                treatedSubjects.remove(subject);
            }

            if(untreatedSubjects.isEmpty()) {
                fillTable();

            }

            else {
                findMostConsSubject();
            }
        }
    }

    private void initialisePeriods(){

        for (Subject subject: untreatedSubjects){

            for (Period period: nontakenPeriods){

                if(!subject.getLevel().equalsIgnoreCase(period.getLevel()) ||
                        subject.getAvailablePeriods().contains(period))
                    continue;

                int size = subject.getTeacher().getUnavailable().size() - 1;
                int i = 0;
                for(UnavailableTime unavailableTime: subject.getTeacher().getUnavailable()){

                    //Checking if unavailable time intersects with period
                    if(unavailableTime.getDay().equals(period.getDay())){
                        if(unavailableTime.getStart().isBefore(period.getEndTime())  &&
                                unavailableTime.getEnd().isAfter(period.getStartTime()))
                            break;
                        else if(period.getStartTime().isBefore(unavailableTime.getEnd()) &&
                                period.getEndTime().isAfter(unavailableTime.getStart()))
                            break;
                    }

                    if(i == size) {
                        subject.getAvailablePeriods().add(period);
                    }
                    i++;
                }
            }

            for(Period period: takenPeriods){
                subject.getAvailablePeriods().remove(period);
            }

            ArrayList<Period> temp = new ArrayList<>();

            for(Period period: subject.getAvailablePeriods()){
                for(UnavailableTime unavailableTime: subject.getTeacher().getUnavailable()){
                    if(unavailableTime.getDay().equals(period.getDay())){
                        if(unavailableTime.getStart().isBefore(period.getEndTime())  &&
                                unavailableTime.getEnd().isAfter(period.getStartTime())) {
                            temp.add(period);
                            break;
                        }
                        else if(period.getStartTime().isBefore(unavailableTime.getEnd()) &&
                                period.getEndTime().isAfter(unavailableTime.getStart())) {
                            temp.add(period);
                            break;
                        }
                    }
                }
            }

            for(Period period: temp){
                subject.getAvailablePeriods().remove(period);
            }

        }
    }

    private void updateRooms(Period period, Subject subject){

        subject.getAvailableRooms().clear();
        int size = sessions.size();

        for(Room room: rooms){

            int i = 0;

            if(room.getCapacity() >= subject.getStudentNumber()) {
                for (ClassSession session : sessions) {
                    if (session.getRoom().equals(room)) {
                        if(session.getPeriod().getDay().equals(period.getDay())) {
                            if (session.getPeriod().getStartTime().isBefore(period.getEndTime()) &&
                                    session.getPeriod().getEndTime().isAfter(period.getStartTime())) {
                                break;
                            }
                            else if (period.getStartTime().isBefore(session.getPeriod().getEndTime()) &&
                                    period.getEndTime().isAfter(session.getPeriod().getStartTime())) {
                                break;
                            }
                        }
                    }
                    i++;

                }

                if(i == size){
                    subject.getAvailableRooms().add(room);
                    break;
                }
            }
        }
    }

    private void readTeachersFile(){
        Scanner reader;
        ArrayList<String> line = new ArrayList<>();

        try{
            reader = new Scanner(teachersFile);
        }catch (FileNotFoundException e){
            errorMessage.setText("Could not find Teachers file");
            return;
        }catch (NullPointerException e){
            return;
        }

        try {
            while (true){
                line.add(reader.nextLine());
            }
        }catch (Exception e){

            for(String string: line) {
                String[] parts = string.split(" ");

                String name = parts[0];
                ArrayList<String> unavailable = new ArrayList<>();


                int i = 0;
                for(String temp: parts){
                    if(i != 0){
                        unavailable.add(temp);
                    }
                    i++;
                }

                fillTeachersList(name, unavailable);
            }
        }
        reader.close();
    }

    private void fillTeachersList(String name, ArrayList<String> unavailable){

        Teacher newTeacher = new Teacher(name);
        teachers.add(newTeacher);

        for(String string: unavailable) {
            String[]  unavailableTime = string.split("\\.");

            try {
                newTeacher.getUnavailable().add(new UnavailableTime(DayOfWeek.valueOf(unavailableTime[0].toUpperCase()),
                        LocalTime.parse(formatTime(unavailableTime[1]), dateTimeFormatter),
                        LocalTime.parse(formatTime(unavailableTime[2]), dateTimeFormatter)));

            }catch (IndexOutOfBoundsException e){

                try {
                    newTeacher.getUnavailable().add(new UnavailableTime(DayOfWeek.valueOf(unavailableTime[0]),
                            LocalTime.parse(formatTime(unavailableTime[1]), dateTimeFormatter)));
                }catch (IndexOutOfBoundsException ex) {
                    try {
                        newTeacher.getUnavailable().add(new UnavailableTime(DayOfWeek.valueOf(unavailableTime[0])));
                    }catch (Exception exc){
                        errorMessage.setText("Error in Teachers' file");

                    }
                }
            }
        }

    }

    private void readSubjectsFile(){

        Scanner reader;
        try{
            reader = new Scanner(subjectsFile);
        }catch (FileNotFoundException e){
            errorMessage.setText("Could not find Subjects' file");

            return;
        }catch (NullPointerException e){
            return;
        }

        try {
            while (reader.hasNext()) {
                String name = reader.next();
                String level = reader.next();
                String teacher = reader.next();
                String studentNumber = reader.next();
                String frequency = reader.next();

                fillSubjectsList(name, level, teacher, studentNumber, frequency);
            }
        }catch (Exception e){
            errorMessage.setText("Error in Subjects' file");
        }

        reader.close();
    }

    private void fillSubjectsList(String name, String level, String teacher, String studentNumber,String frequency){

        try {
            for (Teacher teacher1 : teachers) {
                if (teacher.equalsIgnoreCase(teacher1.getName())) {
                    untreatedSubjects.add(new Subject(name, level, teacher1, Integer.parseInt(studentNumber),
                            Integer.parseInt(frequency)));
                    return;
                }

            }
            errorMessage.setText("The teacher specified in a subject does not match any teacher");

        }catch (Exception e){
            errorMessage.setText("Error in subject's file");

        }
    }

    private void readPeriodsFile(){

        Scanner reader;

        try{
            reader = new Scanner(periodsFile);
        }catch (FileNotFoundException e){
            errorMessage.setText("Could not find Periods' file");
            return;
        }catch (NullPointerException e){
            return;
        }

        try {
            while (reader.hasNext()) {
                String day = reader.next();
                String start = reader.next();
                String end = reader.next();
                String level = reader.next();

                fillPeriodsList(day, start, end, level);
            }
        }catch (Exception x){
            errorMessage.setText("error in Periods' file");

        }

        reader.close();
    }

    private void fillPeriodsList(String day, String start, String end, String level) {

        try {
            nontakenPeriods.add(new Period(DayOfWeek.valueOf(day.toUpperCase()), LocalTime.parse(formatTime(start), dateTimeFormatter),
                    LocalTime.parse(formatTime(end), dateTimeFormatter), level));
        }catch (Exception e){
            errorMessage.setText("Error in periods file");

        }
    }

    private void readRoomsFile(){

        Scanner reader;

        try{
            reader = new Scanner(roomsFile);
        }catch (FileNotFoundException e){
            errorMessage.setText("Could not find rooms' file");
            return;
        } catch (NullPointerException e){
            return;
        }

        try {
            while (reader.hasNext()) {
                String roomName = reader.next();
                String capacity = reader.next();

                fillRoomsList(roomName, capacity);
            }
        }catch (Exception e){
            errorMessage.setText("error in Rooms' file");


        }

        reader.close();
    }

    private void fillRoomsList(String roomName, String capacity) {
        try {
            rooms.add(new Room(roomName, Integer.parseInt(capacity)));
        }catch (Exception e){
            errorMessage.setText("Error in rooms file");
        }
    }


  private String formatTime(String time){
      if(time.length() != 5)
          time = "0"+time;
      return time;
  }

  private void fillTable(){


      for (ClassSession session : sessions) {

          switch (session.getPeriod().getDay()){
              case MONDAY:
                  monday.add(session);
                  break;
              case TUESDAY:
                  tuesday.add(session);
                  break;
              case WEDNESDAY:
                  wednesday.add(session);
                  break;
              case THURSDAY:
                  thursday.add(session);
                  break;
              case FRIDAY:
                  thursday.add(session);
                  break;
          }
      }

      mondayTable.sort();
      tuesdayTable.sort();
      wednesdayTable.sort();
      thursdayTable.sort();
      fridayTable.sort();
      errorMessage.setText("");

  }

    public static void main(String[] args) {
        launch(args);
    }
}