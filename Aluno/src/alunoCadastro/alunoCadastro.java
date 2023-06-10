/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package alunoCadastro;

import dao.AlunoDAO;
import modelo.Aluno;

/**
 *
 * @author José Maria
 */
public class alunoCadastro {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        
        Aluno aluno01 = new Aluno("123.123.123-12", "Jonas", "07/06/1996", 92, 189);
        // cpf, String nome, String dataNascimento, double peso, double altura
        AlunoDAO alunoController = new AlunoDAO();
        //alunoController.adiciona(aluno01);
//        alunoController.exclui("123.123.123-13");
          //alunoController.atualizaNome("123.123.123-12", "Alita");
          //alunoController.atualizaAltura("123.123.123.12", 1.70);
          alunoController.atualizaPeso("123.123.123.12", 40.5);
    }
    
}
