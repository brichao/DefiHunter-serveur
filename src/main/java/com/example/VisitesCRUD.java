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
@RequestMapping("/api/visites")
public class VisitesCRUD {
    
    //@Autowired permet au Framework Spring de résoudre et injecter le Bean qui gère la connexion à la base de donnée
    @Autowired
    private DataSource dataSource;


    //READ ALL -- GET 
    @GetMapping("/")
    public ArrayList<Visites> allvisites(HttpServletResponse response) {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement(); 
            ResultSet rs = stmt.executeQuery("SELECT * FROM visites");
            
            ArrayList<Visites> L = new ArrayList<Visites>();
            while (rs.next()) { 
                Visites v = new Visites();
                v.setVisiteId(rs.getString("visitesid")); 
                v.setDefisId(rs.getString("defisid"));
                v.setVisiteur(rs.getString("visiteur")); 
                v.setDateVisite(rs.getTimestamp("datevisite"));
                v.setModeDP(rs.getString("modedp"));
                v.setScore(rs.getInt("score"));
                v.setTemps(rs.getInt("temps"));
                v.setStatus(rs.getString("status"));
                v.setCommentaire(rs.getString("commentaire"));
                L.add(v);
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
    @GetMapping("/{visitesId}")
    public Visites read(@PathVariable(value="visitesId") String id, HttpServletResponse response) {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement(); 
            ResultSet rs = stmt.executeQuery("SELECT * FROM visites where visitesid = '" + id + "'");
            
            Visites v = new Visites();
            while (rs.next()) { 
                v.setVisiteId(rs.getString("visitesid")); 
                v.setDefisId(rs.getString("defisid"));
                v.setVisiteur(rs.getString("visiteur")); 
                v.setDateVisite(rs.getTimestamp("datevisite"));
                v.setModeDP(rs.getString("modedp"));
                v.setNotation(rs.getInt("notation"));
                v.setScore(rs.getInt("score"));
                v.setTemps(rs.getInt("temps"));
                v.setStatus(rs.getString("status"));
                v.setCommentaire(rs.getString("commentaire"));
            }
            //is non existant error 404
            if(v.getVisiteId() == null) {
                System.out.println("visites does not exist : " + id );
                response.setStatus(404);
                return null;
            } else {
                return v; 
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


    //CREATE -- POST /api/visites/{visitesID}
    @PostMapping("/{visitesId}")
    public Visites create(@PathVariable(value="visitesId") String id, @RequestBody Visites v, HttpServletResponse response){
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement(); 
            
            //une erreur 412 si l'identifiant du visites dans l'URL n'est pas le même que celui du visites dans le corp de la requête.
            if( !(id.equals(v.getVisiteId())) ) {
                System.out.println("Request Body not equivanlent to variable path : " + id + "!=" + v.getVisiteId());
                response.setStatus(412);
                return null;
            }

            ResultSet rs = stmt.executeQuery("SELECT * FROM visites where visitesid = '" + id + "'");

            //une erreur 403 si une ressource existe déjà avec le même identifiant
            if(!( rs.next() ) ) {
                PreparedStatement p = connection.prepareStatement("INSERT INTO visites values (?,?,?, now(), ?, ?,?,?,?,?)");
                p.setString(1, v.getVisiteId());
                p.setString(2, v.getDefisId() );
                p.setString(3, v.getVisiteur() );
                p.setString(4, v.getModeDP() );
                p.setInt(5, v.getNotation() );
                p.setInt(6, v.getScore() );
                p.setInt(7, v.getTemps() );
                p.setString(8, v.getStatus() );
                p.setString(9, v.getCommentaire() );
                p.executeUpdate();
                Visites inseree = this.read(id, response);
            
            return inseree;
            }else {
                System.out.println("Visites already exist: " + id );
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
    @PutMapping("/{visitesId}")
    public Visites update(@PathVariable(value="visitesId") String id, @RequestBody Visites v, HttpServletResponse response) {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement(); 
            
            //une erreur 412 si l'identifiant du visites dans l'URL n'est pas le même que celui du visites dans le corp de la requête.
            if( !(id.equals(v.getVisiteId())) ) {
                System.out.println("Request Body not equivanlent to variable path : " + id + "!=" + v.getVisiteId());
                response.setStatus(412);
                return null;
            }
            ResultSet rs = stmt.executeQuery("SELECT * FROM visites where visitesid = '" + id + "'");

            if(( rs.next() ) ) {
               PreparedStatement p = connection.prepareStatement("UPDATE visites SET visitesid = ?, defisid= ?, visiteur= ?, "+
                                                "modedp = ?, notation=?, score=?, temps=?, status=?, commentaire=? WHERE visitesid = '"+id+"'");

                p.setString(1, v.getVisiteId());
                p.setString(2, v.getDefisId() );
                p.setString(3, v.getVisiteur() );
                p.setString(4, v.getModeDP() );
                p.setInt(5, v.getNotation() );
                p.setInt(6, v.getScore() );
                p.setInt(7, v.getTemps() );
                p.setString(8, v.getStatus() );
                p.setString(9, v.getCommentaire() );
                p.executeUpdate();
                Visites inseree = this.read(id, response);
                return inseree;
            }else {
                System.out.println("visites does not exist : " + id );
                response.setStatus(404);
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

    
    //DELETE -- DELETE
    @DeleteMapping("/{visitesId}")
    public void delete(@PathVariable(value="visitesId") String id, HttpServletResponse response) {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement(); 
            if(!(read(id,response)== null)) {
                int rs = stmt.executeUpdate("DELETE FROM visites WHERE visitesid = '"+id+"'");

            //une erreur 404 si l'identifiant de visites n'existe pas      
            }else {
                System.out.println("visites does not exist : " + id );
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
