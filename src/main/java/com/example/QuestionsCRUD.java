package com.example;

import java.sql.Connection; 
import java.sql.ResultSet; 
import java.sql.Statement; 
import java.util.ArrayList; 
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
@RequestMapping("/api/defis/questions")

public class QuestionsCRUD {
    //@Autowired permet au Framework Spring de résoudre et injecter le Bean qui gère la connexion à la base de donnée
    @Autowired
    private DataSource dataSource;

    
    //READ ALL -- GET
    @GetMapping("/")
    public ArrayList<Questions> allIndices(HttpServletResponse response) {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement(); 
            ResultSet rs = stmt.executeQuery("SELECT * FROM Questions");
            
            ArrayList<Questions> L = new ArrayList<Questions>();
            while (rs.next()) { 
                Questions u = new Questions();
                u.setQuestionId(rs.getInt("questionId"));
                u.setDefisId(rs.getString("defisId"));
                u.setDescription(rs.getString("description"));
                u.setPoints(rs.getInt("points"));
                u.setSecret(rs.getString("secret"));
                L.add(u);
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
    @GetMapping("/{questionId}")
    public Indices read(@PathVariable(value="questionId") int id, HttpServletResponse response) {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement(); 
            ResultSet rs = stmt.executeQuery("SELECT * FROM questions where questionsId = " + id + "");
            
            Questions u = new Questions();
            while (rs.next()) {
                u.setQuestionId(rs.getInt("questionId"));
                u.setDefisId(rs.getString("defisId"));
                u.setDescription(rs.getString("description"));
                u.setPoints(rs.getInt("points"));
                u.setSecret(rs.getString("secret"));
                
            }

            // Une erreur 404 si la question  ne correspond pas à une question  dans la base.
            if(u.getQuestionId() == null) {
                System.out.println("question does not exist : " +id );
                response.setStatus(404);
                return null;
            } else {
                return u; 
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


    //CREATE -- POST : /api/defis/{indicesId}
    @PostMapping("/{questionsId}")
    public Indices create(@PathVariable(value="questionsId") int id, @RequestBody Questions u, HttpServletResponse response){
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement(); 
            
            //une erreur 412 si l'identifiant de la question   dans l'URL n'est pas le même que celui du la question dans le corp de la requête.
            if( !(id.equals(u.getQuestionId())) ) {
                System.out.println("Request Body not equivanlent to variable path : " + id + "!=" + u.getQuestionId());
                response.setStatus(412);
                return null;
            }
             //une erreur 403 si un cexiste déjà avec le même identifiant
            if(read(id,response) == null) {
                int rs = stmt.executeUpdate("INSERT INTO Indices values ("+ u.getQuestionId() + ", '" + u.getDefisId() + "', '" + u.getDescription() + ", '" + u.getPoints() + "' , '"+u.getSecret()+"' )");
                Questions inseree = this.read(id, response);
                return inseree;
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

    
    //UPDATE -- PUT : /api/defis/{indicesId}
    @PutMapping("/{questionId}")
    public Indices update(@PathVariable(value="questionsId") int id, @RequestBody Questions u, HttpServletResponse response) {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement(); 
           
            // Une erreur 404 si l'identifiant de la question ne correspond pas à  celui d'une question dans la base.
            if(u.getQuestionId() == null) {
                System.out.println("Question does not exist : " + id );
                response.setStatus(404);
                return null;

            //une erreur 412 si l'identifiant de la question  dans l'URL n'est pas le même que celui de la question dans le corp de la requête.
            }else if( !(id.equals(u.getQuestionId())) ) {
                System.out.println("Request Body not equivanlent to variable path : " + id + "!=" + u.getQuestionId());
                response.setStatus(412);
                return null;

            }else{
                int rs = stmt.executeUpdate("UPDATE Indices SET questionId="+u.getQuestionId()+", defisid ='"+u.getDefisId()+"', description='"+u.getDescription()+"',points='"+u.getPoints()+"',,secret='"+u.getSecret()+"' WHERE idDefis = '"+id+"'");
                Questions inseree = this.read(id, response);
                return inseree;
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
    @DeleteMapping("/{questionsId}")
    public void delete(@PathVariable(value="questionsId") int id, HttpServletResponse response) {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement(); 
            int rs = stmt.executeUpdate("DELETE FROM questions WHERE questionId = '"+id+"'");

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
    

