/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import factory.ConnectionFactory;
import java.sql.*;
import java.sql.PreparedStatement;
import modelo.Aluno;

/**
 *
 * @author José Maria
 */
public class AlunoDAO {

    private Connection connection;

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
        } 
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        }
    public void exclui(String cpf) {
        String sql = "DELETE FROM aluno WHERE cpf = (?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, cpf);
            stmt.execute();
            stmt.close();
            System.out.println("Aluno de cpf "+cpf+" excluído com sucesso!");
        } 
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    public void buscaCpf(String cpf) {
        String sql = "SELECT FROM aluno WHERE cpf = (?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, cpf);
            stmt.execute();
            stmt.close();
            System.out.println("Cpf: " + cpf);
        } 
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
        
    public void buscaNome(String nome) {
        String sql = "SELECT FROM aluno WHERE nome = (?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.execute();
            stmt.close();
            System.out.println("Aluno: "+ nome);
        } 
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    public void atualizaNome(String cpf, String nome) {
        String sql = "UPDATE aluno SET nome = (?) WHERE cpf = (?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setString(2, cpf);
            stmt.execute();
            stmt.close();
            System.out.println("Aluno de cpf " + cpf + "teve nome alterado para "+ nome);
        } 
        catch (SQLException ex) {
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
            System.out.println("Aluno de cpf " + cpf + "teve peso alterado para "+ peso);
        } 
        catch (SQLException ex) {
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
            System.out.println("Aluno de cpf " + cpf + " teve altura alterada para "+ altura);
        } 
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
}
    public double calculaImc(String nome) {
        Double peso = 0.0;
        Double altura = 0.0;
        String sql = "SELECT peso, altura FROM aluno WHERE nome = (?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.execute();
            stmt.close();
            try (ResultSet resultSet = stmt.executeQuery()){
                if (resultSet.next()) {
                    peso = resultSet.getDouble("peso");
                    altura = resultSet.getDouble("altura");
                }
            }
        } 
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return peso/Math.sqrt(altura);
    }
}
