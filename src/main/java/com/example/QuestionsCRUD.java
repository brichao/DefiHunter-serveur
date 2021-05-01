package com.example;

import java.sql.Connection; 
import java.sql.ResultSet; 
import java.sql.Statement; 
import java.util.ArrayList;

import javax.print.DocFlavor.STRING;
import javax.servlet.http.HttpServletResponse; 
import javax.sql.DataSource;

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
    @GetMapping("/{defisid}/questions")
    public ArrayList<Questions> read(@PathVariable(value="defisid") String id, HttpServletResponse response) {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement(); 
            ResultSet rs = stmt.executeQuery("SELECT * FROM questions where defisid = '" + id + "'");
        
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

            // Une erreur 404 si le defi n'est pas dans la base 
            if(id == null) {
                System.out.println("Questions does not exist for Defi : " + id);
                response.setStatus(404);
                return null;
            } else {
                return L; 
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
    @PostMapping("/{defisid}/questions/{questionsId}")
    public ArrayList<Questions> create(@PathVariable(value="defisid") String id, @PathVariable(value="questionsId") int Qid, @RequestBody Questions q, HttpServletResponse response){
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement(); 
            
            //une erreur 412 si l'identifiant de la question   dans l'URL n'est pas le même que celui du la question dans le corp de la requête.
            if( !(Qid != q.getQuestionNum()) && !(id.equals(q.getDefisId())) ) {
                System.out.println("Request Body not equivanlent to variable path : " + Qid + "!=" + q.getQuestionId());
                response.setStatus(412);
                return null;
            }
             //une erreur 403 si un cexiste déjà avec le même identifiant
            if(read(id,response).isEmpty()) {
                int rs = stmt.executeUpdate("INSERT INTO Questions (defisid, questionnum, description, points, secret) values ('" 
                                        + q.getDefisId() + "',"+ q.getQuestionNum() + ", '" + q.getDescription() + ", '" + q.getPoints() + "' , '"+q.getSecret()+"' )");
                ArrayList<Questions> L = this.read(id, response);;
                return L;
            }else {
                System.out.println("Questions already exist: " + id );
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
    @PutMapping("/{defisId}/questions/{questionId}")
    public ArrayList<Questions> update(@PathVariable(value="defisId") String id, @PathVariable(value="questionsId") int Qid, @RequestBody Questions q, HttpServletResponse response) {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement(); 
           
            // Une erreur 404 si l'identifiant de la question ne correspond pas à  celui d'une question dans la base.
            if(q.getQuestionNum() == 0) {
                System.out.println("Question does not exist : " + id );
                response.setStatus(404);
                return null;

            //une erreur 412 si l'identifiant de la question  dans l'URL n'est pas le même que celui de la question dans le corp de la requête.
            }else if( !(Qid != q.getQuestionNum()) ) {
                System.out.println("Request Body not equivalent to variable path : " + Qid + "!=" + q.getQuestionNum());
                response.setStatus(412);
                return null;

            }else{
                int rs = stmt.executeUpdate("UPDATE Questions SET questionnum=" + q.getQuestionNum()
                            +", description='"+q.getDescription()+"', points='"+q.getPoints()+"', secret='"+q.getSecret()+"' WHERE defisId = '"+id+"' AND questionNum = "+Qid);
                ArrayList<Questions> L = this.read(id, response);
                return L;
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
    @DeleteMapping("/{defisId}/questions{questionsId}")
    public void delete(@PathVariable(value="defisId") String id, @PathVariable(value="questionsId") int Qid, HttpServletResponse response) {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement(); 
            int rs = stmt.executeUpdate("DELETE FROM questions WHERE questionnum = '"+Qid+"'");

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
    

