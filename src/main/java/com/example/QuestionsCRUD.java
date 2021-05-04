package com.example;

import java.sql.Connection; 
import java.sql.ResultSet; 
import java.sql.Statement; 
import java.util.ArrayList;

import javax.print.DocFlavor.STRING;
import javax.servlet.http.HttpServletResponse; 
import javax.sql.DataSource;
import java.sql.PreparedStatement;


import com.fasterxml.jackson.annotation.JacksonInject.Value;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.web.bind.annotation.CrossOrigin; 
import org.springframework.web.bind.annotation.DeleteMapping; 
import org.springframework.web.bind.annotation.GetMapping; 
import org.springframework.web.bind.annotation.PathVariable; 
import org.springframework.web.bind.annotation.PostMapping; 
import org.springframework.web.bind.annotation.PutMapping; 
import org.springframework.web.bind.annotation.RequestBody; 
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException; 

//controleur REST ( répondre à HTTP avec des données quelconques (pas nécessaires HTML) )
@RestController
//indique que le contrôleur accepte les requêtes provenant d'une source quelconque (et donc pas nécessairement le même serveur). 
@CrossOrigin
// Indique que les ressources HTTP qui seront déclarées dans la classe seront toutes préfixées par /api/users.
@RequestMapping("/api/defis")

public class QuestionsCRUD {
    //@Autowired permet au Framework Spring de résoudre et injecter le Bean qui gère la connexion à la base de donnée
    @Autowired
    private DataSource dataSource;

    
    //READ ALL -- GET
    @GetMapping("/questions")
    public ArrayList<Questions> allQuestions(HttpServletResponse response) {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement(); 
            ResultSet rs = stmt.executeQuery("SELECT * FROM Questions");
            
            ArrayList<Questions> L = new ArrayList<Questions>();
            while (rs.next()) { 
                Questions q = new Questions();
                q.setQuestionId(rs.getInt("questionsid"));
                q.setDefisId(rs.getString("defisid"));
                q.setQuestionNum(rs.getInt("questionnum"));
                q.setDescription(rs.getString("description"));
                q.setPoints(rs.getInt("points"));
                q.setSecret(rs.getString("secret"));
                L.add(q);
            } 
            return L;
        } catch (Exception e) {
            response.setStatus(500);

            try {
                response.getOutputStream().print( e.getMessage() );
            } catch (Exception e2) {
                System.err.println(e2.getMessage());
            }
            System.err.println(e.getMessage());
            return null;
        }
    }


    //READ -- GET 
    @GetMapping("/{defisid}/questions/{questionNum}")
    public Questions read(@PathVariable(value="defisid") String id,  @PathVariable(value="questionNum") int Qid, HttpServletResponse response) {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement(); 
            ResultSet rs = stmt.executeQuery("SELECT * FROM questions where defisid = '" + id + "' and questionNum = " + Qid );
            
            // Une erreur 404 si la question n'est pas dans la base pour ce défi
            if( ! (rs.next()) ) {
                System.out.println("Question" +Qid+"does not exist for Defi : " + id);
                response.setStatus(404);
                return null;
            } else {
                Questions q = new Questions();
                 do {
                    q.setQuestionId(rs.getInt("questionsid"));
                    q.setDefisId(rs.getString("defisid"));
                    q.setQuestionNum(rs.getInt("questionnum"));
                    q.setDescription(rs.getString("description"));
                    q.setPoints(rs.getInt("points"));
                    q.setSecret(rs.getString("secret"));
                } while (rs.next());
                return q; 
            }

        } catch (Exception e) {
            response.setStatus(500);

            try {
                response.getOutputStream().print( e.getMessage() );
            } catch (Exception e2) {
                System.err.println(e2.getMessage());
            }
            System.err.println(e.getMessage());
            return null;
        }
        
    }


    //CREATE -- POST : /api/defis/{defisid}/questions/{questionsId}
    @PostMapping("/{defisid}/questions/{questionNum}")
    public Questions create(@PathVariable(value="defisid") String id, @PathVariable(value="questionNum") int Qid, @RequestBody Questions q, HttpServletResponse response){
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement(); 
            
            //une erreur 412 si l'identifiant de la question   dans l'URL n'est pas le même que celui du la question dans le corp de la requête.
            if( !(Qid != q.getQuestionNum()) && !(id.equals(q.getDefisId())) ) {
                System.out.println("Request Body not equivalent to variable path : " + Qid + "!=" + q.getQuestionId());
                response.setStatus(412);
                return null;
            }
             //une erreur 403 si un existe déjà avec le même identifiant
            if(read(id, Qid, response) == null) {
                PreparedStatement p = connection.prepareStatement("INSERT INTO questions (defisid, questionnum, description, points, secret) values (?,?,?,?,?)");
                p.setString(1, q.getDefisId());
                p.setInt(2, q.getQuestionNum() );
                p.setString(3, q.getDescription() );
                p.setInt(4, q.getPoints() );
                p.setString(5, q.getSecret() );
                p.executeUpdate();
                Questions inseree = this.read(id, Qid, response);
                return inseree;
            }else {
                System.out.println("question already exist for " + id + " question n."+Qid);
                response.setStatus(403);
                return null;
            }
            
        } catch (Exception e) {
            response.setStatus(500);
            try {
                response.getOutputStream().print( e.getMessage() );
            } catch (Exception e2) {
                System.err.println(e2.getMessage());
            }
            System.err.println(e.getMessage());
            return null;
        }
    }

    
    //UPDATE -- PUT : /api/defis/{defisId}/questions/{questionId}
    @PutMapping("/{defisId}/questions/{questionNum}")
    public Questions update(@PathVariable(value="defisId") String id, @PathVariable(value="questionsNum") int Qid, @RequestBody Questions q, HttpServletResponse response) {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement(); 
           
            // Une erreur 404 si l'identifiant de la question ne correspond pas à  celui d'une question dans la base.
            if(read(id, Qid, response) == null) {
                System.out.println("Question does not exist : " + id );
                response.setStatus(404);
                return null;

            //une erreur 412 si l'identifiant de la question  dans l'URL n'est pas le même que celui de la question dans le corp de la requête.
            }else if( !(Qid != q.getQuestionNum()) && !(id.equals(q.getDefisId())) ) {
                System.out.println("Request Body not equivanlent to variable path : " + id + "!=" + q.getDefisId() + " and " + Qid +" != " +q.getQuestionNum());
                response.setStatus(412);
                return null;

            }else{
                 PreparedStatement p = connection.prepareStatement("UPDATE Questions SET description= ?, points= ?,secret= ?, WHERE defisid = '"+id+"' AND questionNum = "+Qid);
                    p.setString(1, q.getDescription() );
                    p.setInt(2, q.getPoints() );
                    p.setString(3, q.getSecret() );
                    p.executeUpdate();
                    Questions Q = this.read(id, Qid, response);
                    return Q;
            }   

        } catch (Exception e) {
            response.setStatus(500);

            try {
                response.getOutputStream().print( e.getMessage() );
            } catch (Exception e2) {
                System.err.println(e2.getMessage());
            }
            System.err.println(e.getMessage());
            return null;
        } 
    }

        
    //DELETE -- DELETE
    @DeleteMapping("/{defisId}/questions{questionNum}")
    public void delete(@PathVariable(value="defisId") String id, @PathVariable(value="questionNum") int Qid, HttpServletResponse response) {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement(); 
            int rs = stmt.executeUpdate("DELETE FROM questions WHERE defisid = '"+id+"' AND questionNum = " + Qid);

            // Une erreur 404 si l'identifiant de la question  ne correspond pas à une question  dans la base.
            if(rs == 0){
                System.out.println("question does not exist : " + id );
                response.setStatus(404);
            }
        } catch (Exception e) {
            response.setStatus(500);

            try {
                response.getOutputStream().print( e.getMessage() );
            } catch (Exception e2) {
                System.err.println(e2.getMessage());
            }
            System.err.println(e.getMessage());
        }
    }

}
    

