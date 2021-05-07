package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
@RequestMapping("/api/defis/blocstexte")
public class BlocsTexteCRUD {
    //@Autowired permet au Framework Spring de résoudre et injecter le Bean qui gère la connexion à la base de donnée
    @Autowired
    private DataSource dataSource;

    
    //READ ALL -- GET
    @GetMapping("/")
    public ArrayList<BlocsTexte> allBlocsTextes(HttpServletResponse response) {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement(); 
            ResultSet rs = stmt.executeQuery("SELECT * FROM BlocsTexte");
            
            ArrayList<BlocsTexte> L = new ArrayList<BlocsTexte>();
            while (rs.next()) { 
                BlocsTexte b = new BlocsTexte();
                b.setBlocsTexteId(rs.getInt("blocstexteid"));
                b.setQuestionsId(rs.getInt("questionsid"));
                b.setIndiceId(rs.getInt("indicesid"));
                b.setTexte(rs.getString("texte"));
                b.setDefisId(rs.getString("defisid"));
                L.add(b);
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
    @GetMapping("/{blocsTexteId}")
    public BlocsTexte read(@PathVariable(value="blocsTexteId") int id, HttpServletResponse response) {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement(); 
            ResultSet rs = stmt.executeQuery("SELECT * FROM blocsTexte where blocsTexteId = " +id);
            
            BlocsTexte b = new BlocsTexte();
            while (rs.next()) {
                b.setBlocsTexteId(rs.getInt("blocstexteid"));
                b.setQuestionsId(rs.getInt("questionsid"));
                b.setIndiceId(rs.getInt("indicesid"));
                b.setTexte(rs.getString("texte"));
                b.setDefisId(rs.getString("defisid"));
                
            }

            // Une erreur 404 si l'indice ne correspond pas à texte dans la base.
            if(b.getBlocsTexteId() == 0) {
                System.out.println("Texte does not exist : " + id );
                response.setStatus(404);
                return null;
            } else {
                return b; 
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


    //CREATE -- POST 
    @PostMapping("/{blocsTexteId}")
    public BlocsTexte create(@PathVariable(value="blocsTexteId") int id, @RequestBody BlocsTexte b, HttpServletResponse response){
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement(); 
            
            
            //une erreur 412 si l'identifiant de defis  dans l'URL n'est pas le même que celui du l'indice dans le corp de la requête.
            if( id != b.getBlocsTexteId() ) {
                System.out.println("Request Body not equivanlent to variable path : " + id + "!=" + b.getBlocsTexteId());
                response.setStatus(412);
                return null;
            }
            ResultSet rs = stmt.executeQuery("SELECT * FROM blocsTexte where blocsTexteId = " +id);
             //une erreur 403 si un cexiste déjà avec le même identifiant
            if(! (rs.next()) ) {
                PreparedStatement p = connection.prepareStatement("INSERT INTO BlocsTexte values (?,?,?,?,?)");
                p.setInt(1, b.getBlocsTexteId());
                p.setInt(2, b.getQuestionsId() );
                p.setInt(3, b.getIndiceId() );
                p.setString(4, b.getTexte() );
                p.setString(5, b.getDefisId() );
                p.executeUpdate();
                BlocsTexte inseree = this.read(id, response);
                return inseree;
            }else {
                System.out.println("Texte already exist: " + id );
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

    
    //UPDATE -- PUT 
    @PutMapping("/{blocsTexteId}")
    public BlocsTexte update(@PathVariable(value="blocsTexteId") int id, @RequestBody BlocsTexte b, HttpServletResponse response) {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement(); 
            // Une erreur 404 si l'identifiant de defis ne correspond pas à  celui d'un indice dans la base.
            if(b.getBlocsTexteId() == 0) {
                System.out.println("Texte does not exist : " + id );
                response.setStatus(404);
                return null;

            //une erreur 412 si l'identifiant du User dans l'URL n'est pas le même que celui du User dans le corp de la requête.
            }else if( id != b.getBlocsTexteId()) {
                System.out.println("Request Body not equivanlent to variable path : " + id + "!=" + b.getBlocsTexteId());
                response.setStatus(412);
                return null;

            }else{
                PreparedStatement p = connection.prepareStatement("UPDATE BlocsTexte SET blocstexteid =?,questionid=?, indiceid=?,texte=?,defisid=? WHERE blocsTexteId = '"+id+"'");
                p.setInt(1, b.getBlocsTexteId());
                p.setInt(2, b.getQuestionsId() );
                p.setInt(3, b.getIndiceId() );
                p.setString(4, b.getTexte() );
                p.setString(5, b.getDefisId() );
                p.executeUpdate();
                BlocsTexte inseree = this.read(id, response);
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
    @DeleteMapping("/{blocsTexteId}")
    public void delete(@PathVariable(value="blocsTexteId") int id, HttpServletResponse response) {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement(); 
            int rs = stmt.executeUpdate("DELETE FROM blocsTexte WHERE blocsTexteId = '"+id+"'");

            // Une erreur 404 si l'identifiant de defis  ne correspond pas à un defis dans la base.
            if(rs == 0){
                System.out.println("texte does not exist : " + id );
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