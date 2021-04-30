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
@RequestMapping("/api/arrets")
public class ArretsCRUD {
    //@Autowired permet au Framework Spring de résoudre et injecter le Bean qui gère la connexion à la base de donnée
    @Autowired
    private DataSource dataSource;

    
    //READ ALL -- GET
    @GetMapping("/")
    public ArrayList<Arrets> allarrets(HttpServletResponse response) {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement(); 
            ResultSet rs = stmt.executeQuery("SELECT * FROM arrets");
            
            ArrayList<Arrets> L = new ArrayList<Arrets>();
            while (rs.next()) { 
                Arrets a = new Arrets();
                a.setCodeArret(rs.getString("codearret"));
                a.setNomArret(rs.getString("nomarret"));
                a.setStreetMap(rs.getString("streetmap"));
                L.add(a);
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
    @GetMapping("/{codearret}")
    public Arrets read(@PathVariable(value="codearret") String id, HttpServletResponse response) {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement(); 
            ResultSet rs = stmt.executeQuery("SELECT * FROM arrets where codearret = '" + id + "'");
            
            Arrets a = new Arrets();
            while (rs.next()) {
                a.setCodeArret(rs.getString("codearret"));
                a.setNomArret(rs.getString("nomarret"));
                a.setStreetMap(rs.getString("streetmap"));
                
            }

            // Une erreur 404 si l'arret ne correspond pas à un arret dans la base.
            if(a.getCodeArret() == null) {
                System.out.println("Arret does not exist : " + id );
                response.setStatus(404);
                return null;
            } else {
                return a; 
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
    @PostMapping("/{codearret}")
    public Arrets create(@PathVariable(value="codearret") String id, @RequestBody Arrets a, HttpServletResponse response){
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement(); 
            
            //une erreur 412 si l'identifiant de l'arret  dans l'URL n'est pas le même que celui de l'arret dans le corp de la requête.
            if( !(id.equals(a.getCodeArret())) ) {
                System.out.println("Request Body not equivanlent to variable path : " + id + "!=" + a.getCodeArret());
                response.setStatus(412);
                return null;
            }
             //une erreur 403 si un arret existe déjà avec le même identifiant
            if(read(id,response) == null) {
                PreparedStatement p = connection.prepareStatement("INSERT INTO Arrets values (?,?,?)");
                p.setString(1, a.getCodeArret());
                p.setString(2, a.getNomArret() );
                p.setString(3, a.getStreetMap() );
                p.executeUpdate();

                Arrets inseree = this.read(id, response);
                return inseree;
            }else {
                System.out.println("Arret already exist: " + id );
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
    @PutMapping("/{codearret}")
    public Arrets update(@PathVariable(value="codearret") String id, @RequestBody Arrets a, HttpServletResponse response) {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement(); 
           
            // Une erreur 404 si l'identifiant de l'arret ne correspond pas à  celui d'un arret dans la base.
            if(a.getCodeArret() == null) {
                System.out.println("Arret does not exist : " + id );
                response.setStatus(404);
                return null;

            //une erreur 412 si l'identifiant de arret dans l'URL n'est pas le même que celui de l'arret dans le corp de la requête.
            }else if( !(id.equals(a.getCodeArret())) ) {
                System.out.println("Request Body not equivanlent to variable path : " + id + "!=" + a.getCodeArret());
                response.setStatus(412);
                return null;

            }else{
                int rs = stmt.executeUpdate("UPDATE Arrets SET codeArret ='"+a.getCodeArret()+"', nomarret='"+a.getNomArret()+"', streetmap='"+a.getStreetMap()+"' WHERE codeArret = '"+id+"'");
                Arrets inseree = this.read(id, response);
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
    @DeleteMapping("/{codearret}")
    public void delete(@PathVariable(value="codearret") String id, HttpServletResponse response) {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement(); 
            int rs = stmt.executeUpdate("DELETE FROM arrets WHERE codearret = '"+id+"'");

            // Une erreur 404 si l'identifiant de arret  ne correspond pas à un arret dans la base.
            if(rs == 0){
                System.out.println("arret does not exist : " + id );
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
    



