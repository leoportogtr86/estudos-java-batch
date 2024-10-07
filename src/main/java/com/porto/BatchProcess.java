package com.porto;

import com.porto.connection.DatabaseConnection;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BatchProcess {
    public static void main(String[] args) {
        String csvFile = "dados.csv";
        String line;
        String csvSeparator = ",";

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(csvFile))) {
            Connection connection = DatabaseConnection.getConnection();
            String sqlInsert = "INSERT INTO usuarios (id, nome, email) VALUES (?, ?, ?)";

            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(csvSeparator);

                try (PreparedStatement statement = connection.prepareStatement(sqlInsert)) {
                    statement.setInt(1, Integer.parseInt(data[0]));
                    statement.setString(2, data[1]);
                    statement.setString(3, data[2]);

                    int linhasAfetadas = statement.executeUpdate();
                    System.out.println("--- Executando a query ---");
                    System.out.println("Linhas afetadas: " + linhasAfetadas);
                }
            }
            connection.close();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}
