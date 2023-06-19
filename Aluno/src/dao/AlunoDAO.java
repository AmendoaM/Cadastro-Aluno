/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import factory.ConnectionFactory;
import java.sql.*;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.Aluno;
import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author José Maria
 */
public class AlunoDAO {

    private Connection connection;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    
    public AlunoDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public void adiciona(Aluno aluno) {
        String sql = "INSERT INTO aluno VALUES(?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, aluno.getCpf());
            stmt.setString(2, aluno.getNome());
            stmt.setString(3, aluno.getDataNascimento());
            stmt.setDouble(4, aluno.getPeso());
            stmt.setDouble(5, aluno.getAltura());
            stmt.execute();
            stmt.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void excluiAluno(String cpf) {
        String sql = "DELETE FROM aluno WHERE cpf = (?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, cpf);
            stmt.execute();
            stmt.close();
            JOptionPane.showMessageDialog(null, "Aluno de cpf " + cpf + "  excluido com sucesso.");
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public ArrayList<Aluno> buscaNome(String nome) {
        ArrayList<Aluno> alunos = new ArrayList<>();
        String sql = "SELECT * FROM aluno WHERE nome LIKE ?";
        try ( PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + nome + "%");
            try ( ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    String cpf = resultSet.getString("cpf");
                    String nome2 = resultSet.getString("nome");
                    String dn = resultSet.getString("dataNascimento");
                    double peso = resultSet.getDouble("peso");
                    int altura = resultSet.getInt("altura");
                    Aluno novo = new Aluno(cpf, nome2, dn, peso, altura);
                    alunos.add(novo);
                   
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return alunos;
    }

    public void atualizaNome(String cpf, String nome) {
        String sql = "UPDATE aluno SET nome = (?) WHERE cpf = (?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setString(2, cpf);
            stmt.execute();
            stmt.close();
          
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

    }

    public void atualizaPeso(String cpf, double peso) {
        String sql = "UPDATE aluno SET peso = (?) WHERE cpf = (?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setDouble(1, peso);
            stmt.setString(2, cpf);
            stmt.execute();
            stmt.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void atualizaAltura(String cpf, int altura) {
        String sql = "UPDATE aluno SET altura = (?) WHERE cpf = (?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setDouble(1, altura);
            stmt.setString(2, cpf);
            stmt.execute();
            stmt.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public double calculaImc(String cpf) {        
        Double peso = 0.0;
        int altura = 0;
        double imc = 0.0;
        String sql = "SELECT peso, altura FROM aluno WHERE cpf = (?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, cpf);
            try ( ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    peso = resultSet.getDouble("peso");
                    altura = resultSet.getInt("altura");
                      double quadradoAltura = ((double)altura / 100) * ((double)altura / 100);
                      imc = peso / quadradoAltura;
                      imc = Math.round((imc * 100) / 100);
                     
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        
        return imc;
      
    }

    public void atualizaDados(String cpf, String nome, String dn, double peso, int altura) {
        String sql = "UPDATE aluno SET nome = ?, dataNascimento = ?, peso = ?, altura = ?  WHERE cpf = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setString(2, dn);
            stmt.setDouble(3, peso);
            stmt.setInt(4, altura);
            stmt.setString(5, cpf);
            stmt.execute();
            stmt.close();
            JOptionPane.showMessageDialog(null, "Dados do aluno de cpf " + cpf + " alterados com sucesso.");
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    //IMC
    public String explicaImc(double imc) {
        if (imc < 18.5) {
            return " Abaixo do peso.";
        } else if (imc < 24.9) {
            return " Peso normal.";
        } else if (imc < 29.9) {
            return " Sobrepeso.";
        } else if (imc < 34.9) {
            return " Obesidade nivel I.";
        } else if (imc < 39.9) {
            return " Obesidade nivel II.";
        } else {
            return " Obesidade nivel III.";
        }
    }

    public boolean dadosAluno(Aluno aluno) {
        String path = System.getProperty("user.home") + "/Desktop";
        String data = String.valueOf(LocalDate.now());
        LocalDateTime now = LocalDateTime.now();
        String caminho = path + "/Relatório do aluno " + aluno.getNome() + " " + data + ".txt";
        String texto = 
                 "Nome: " + aluno.getNome() + "\n"
                + "Cpf: " + aluno.getCpf()+ "\n"
                + "Data: " + dtf.format(now) + "\n"
                + "IMC: " + calculaImc(aluno.getCpf()) + "\n"
                + "A condição do aluno é de: " + explicaImc(calculaImc(aluno.getCpf())) +"\n";
               
               
                

        try {
            FileWriter arq = new FileWriter(caminho);
            PrintWriter gravarArq = new PrintWriter(arq);
            gravarArq.println(texto);
            gravarArq.close();
            JOptionPane.showMessageDialog(null, "Relatório salvo na área de trabalho");
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }

    }
}
