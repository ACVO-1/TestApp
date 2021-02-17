import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String CSV1 = "input1.csv";
        String CSV2 = "input2.csv";

        readFirstData(CSV1);
        Thread myThready = new Thread(() -> readSecondData(CSV2));
        myThready.start();

    }

    private static void readFirstData(String file) {
        try {
            FileReader fileReader = new FileReader(file);

            CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
            CSVReader csvReader = new CSVReaderBuilder(fileReader).withCSVParser(parser).build();

            List<String[]> allData = csvReader.readAll();

            StringBuilder idBuilder = new StringBuilder();
            StringBuilder versionBuilder = new StringBuilder();
            StringBuilder pathBuilder = new StringBuilder();

            File idFile = new File("id.txt");
            File versionFile = new File("version.txt");
            File pathFile = new File("path.txt");

            ArrayList<String> versionArray = new ArrayList<>();

            for (String[] row : allData) {

                if (!row[0].equals("id")) {
                    idBuilder.append(row[0]).append(";");
                } else idBuilder.append(row[0]).append(":").append("\n");

                versionArray.add(row[1]);
                versionArray = removeDuplicates(versionArray);

                if (!row[2].equals("path")) {
                    pathBuilder.append(row[2]).append(";");
                } else pathBuilder.append(row[2]).append(":").append("\n");
            }

            for (String versionData : versionArray) {
                if (!versionData.equals("version")) {
                    versionBuilder.append(versionData).append(";");
                } else versionBuilder.append(versionData).append(":").append("\n");
            }

            String id = idBuilder.toString();
            FileWriter idWriter = new FileWriter(idFile);
            idWriter.write(id);
            idWriter.close();

            String version = versionBuilder.toString();
            FileWriter versionWriter = new FileWriter(versionFile);
            versionWriter.write(version);
            versionWriter.close();

            String path = pathBuilder.toString();
            FileWriter pathWriter = new FileWriter(pathFile);
            pathWriter.write(path);
            pathWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void readSecondData(String file) {
        try {
            FileReader fileReader = new FileReader(file);
            FileReader idFileReader = new FileReader("id.txt");

            CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
            CSVReader csvReader = new CSVReaderBuilder(fileReader).withCSVParser(parser).build();

            List<String[]> allData = csvReader.readAll();

            StringBuilder idBuilder = new StringBuilder();
            StringBuilder nameBuilder = new StringBuilder();
            StringBuilder sexBuilder = new StringBuilder();

            File idFile = new File("id.txt");
            File nameFile = new File("name.txt");
            File sexFile = new File("sex.txt");

            ArrayList<String> idOldArray = new ArrayList<>();
            ArrayList<String> idArray = new ArrayList<>();
            ArrayList<String> sexArray = new ArrayList<>();

            try {
                BufferedReader reader = new BufferedReader(idFileReader);
                String line = reader.readLine();
                while (line != null) {

                    idOldArray.add(line);
                    line = reader.readLine();
                }

                String[] idOld = idOldArray.get(1).split(";");
                idOldArray.clear();
                idOldArray.addAll(Arrays.asList(idOld));

            } catch (IOException e) {
                e.printStackTrace();
            }

            for (String[] row : allData) {

                idArray.add(row[0]);

                sexArray.add(row[2]);
                sexArray = removeDuplicates(sexArray);

                if (!row[1].equals("name")) {
                    nameBuilder.append(row[1]).append(";");
                } else nameBuilder.append(row[1]).append(":").append("\n");
            }

            idArray.addAll(idOldArray);
            idArray = removeDuplicates(idArray);

            for (String idData : idArray) {
                if (!idData.equals("id")) {
                    idBuilder.append(idData).append(";");
                } else idBuilder.append(idData).append(":").append("\n");
            }

            for (String versionData : sexArray) {
                if (!versionData.equals("sex")) {
                    sexBuilder.append(versionData).append(";");
                } else sexBuilder.append(versionData).append(":").append("\n");
            }

            String id = idBuilder.toString();
            FileWriter idWriter = new FileWriter(idFile);
            idWriter.write(id);
            idWriter.close();

            String version = nameBuilder.toString();
            FileWriter versionWriter = new FileWriter(nameFile);
            versionWriter.write(version);
            versionWriter.close();

            String path = sexBuilder.toString();
            FileWriter pathWriter = new FileWriter(sexFile);
            pathWriter.write(path);
            pathWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<String> removeDuplicates(ArrayList<String> list) {
        ArrayList<String> newList = new ArrayList<>();

        for (String element : list) {

            if (!newList.contains(element)) {

                newList.add(element);
            }
        }
        return newList;
    }
}
