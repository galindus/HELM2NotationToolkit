package org.helm.notation2.tools;

import org.helm.chemtoolkit.CTKException;
import org.helm.notation2.Chemistry;
import org.helm.notation2.exception.BuilderMoleculeException;
import org.helm.notation2.exception.ChemistryException;
import org.helm.notation2.exception.ParserException;
import org.jdom2.JDOMException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class AbzuCLI {

    public static void main(String[] args) throws Exception {
        // Verificar que se pasen los argumentos correctamente
        if(args.length < 2) {
            System.out.println("Usage: java -jar helm2smiles.jar <inputCSVPath> <outputCSVPath>");
            return;
        }

        // Leer el archivo CSV de entrada
        String inputCSVPath = args[0];
        List<String[]> rows = readCSV(inputCSVPath);

        // Procesar las filas para invertir la cadena de la primera columna
        List<String[]> processedRows = processRows(rows);

        // Escribir las filas procesadas en el archivo CSV de salida
        String outputCSVPath = args[1];
        writeCSV(outputCSVPath, processedRows);
    }

    private static List<String[]> readCSV(String filePath) throws Exception {
        List<String[]> rows = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                rows.add(values);
            }
        }
        return rows;
    }

    private static List<String[]> processRows(List<String[]> rows) throws ParserException, JDOMException, ChemistryException, CTKException, BuilderMoleculeException {
        List<String[]> processedRows = new ArrayList<>();
        boolean isFirstRow = true;
        for (String[] row : rows) {
            if (isFirstRow) {
                processedRows.add(new String[]{row[0], "SMILES"});
                isFirstRow = false;
            } else if (row.length > 0) {
                String smile = SMILES.getSMILESForAll(HELM2NotationUtils.readNotation(row[0]));
                processedRows.add(new String[]{row[0], smile});
            }
        }
        return processedRows;
    }

    private static void writeCSV(String filePath, List<String[]> rows) throws Exception {
        try (FileWriter writer = new FileWriter(filePath)) {
            for (String[] row : rows) {
                writer.write(String.join(",", row) + "\n");
            }
        }
    }
}
