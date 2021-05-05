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
@RequestMapping("/api/defis")
public class DefisCRUD {
    
    //@Autowired permet au Framework Spring de résoudre et injecter le Bean qui gère la connexion à la base de donnée
    @Autowired
    private DataSource dataSource;


    //READ ALL -- GET 
    @GetMapping("/")
    public ArrayList<Defis> alldefis(HttpServletResponse response) {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement(); 
            ResultSet rs = stmt.executeQuery("SELECT * FROM Defis");
            
            ArrayList<Defis> L = new ArrayList<Defis>();
            while (rs.next()) { 
                Defis d = new Defis();
                d.setId(rs.getString("defisid")); 
                d.setTitre(rs.getString("titre"));
                d.setNomType(rs.getString("nomtype")); 
                d.setDateCreation(rs.getTimestamp("datecreation"));
                d.setDateModification(rs.getTimestamp("datemodification"));
                d.setAuteur(rs.getString("auteur"));
                d.setCodeArret(rs.getString("codearret"));
                d.setPoints(rs.getInt("points"));
                d.setDuree(rs.getDouble("duree"));
                d.setPrologue(rs.getString("prologue"));
                d.setEpilogue(rs.getString("epilogue"));
                d.setCommentaire(rs.getString("commentaire"));
                L.add(d);
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
    @GetMapping("/{defisId}")
    public Defis read(@PathVariable(value="defisId") String id, HttpServletResponse response) {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement(); 
            ResultSet rs = stmt.executeQuery("SELECT * FROM defis where defisid = '" + id + "'");
            
            Defis d = new Defis();
            while (rs.next()) { 
                d.setId(rs.getString("defisid")); 
                d.setTitre(rs.getString("titre"));
                d.setNomType(rs.getString("nomtype")); 
                d.setDateCreation(rs.getTimestamp("datecreation"));
                d.setDateModification(rs.getTimestamp("datemodification"));
                d.setAuteur(rs.getString("auteur"));
                d.setCodeArret(rs.getString("codearret"));
                d.setPoints(rs.getInt("points"));
                d.setDuree(rs.getDouble("duree"));
                d.setPrologue(rs.getString("prologue"));
                d.setEpilogue(rs.getString("epilogue"));
                d.setCommentaire(rs.getString("commentaire"));
            }
            //is non existant error 404
            if(d.getId() == null) {
                System.out.println("Defis does not exist : " + id );
                response.setStatus(404);
                return null;
            } else {
                return d; 
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


    //CREATE -- POST /api/defis/{defisID}
    @PostMapping("/{defisId}")
    public Defis create(@PathVariable(value="defisId") String id, @RequestBody Defis d, HttpServletResponse response){
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement(); 
            
            //une erreur 412 si l'identifiant du defis dans l'URL n'est pas le même que celui du defis dans le corp de la requête.
            if( !(id.equals(d.getId())) ) {
                System.out.println("Request Body not equivanlent to variable path : " + id + "!=" + d.getId());
                response.setStatus(412);
                return null;
            }

            ResultSet rs = stmt.executeQuery("SELECT * FROM defis where defisid = '" + id + "'");

            //une erreur 403 si une ressource existe déjà avec le même identifiant
            if(!( rs.next() ) ) {
                PreparedStatement p = connection.prepareStatement("INSERT INTO defis values (?,?,?, now(), now(), ?,?,?,?,?,?,?)");
                p.setString(1, d.getId());
                p.setString(2, d.getTitre() );
                p.setString(3, d.getNomType() );
                p.setString(4, d.getAuteur() );
                p.setString(5, d.getCodeArret() );
                p.setInt(6, d.getPoints() );
                p.setDouble(7, d.getDuree() );
                p.setString(8, d.getPrologue() );
                p.setString(9, d.getEpilogue() );
                p.setString(10, d.getCommentaire() );
                p.executeUpdate();
            Defis inseree = this.read(id, response);
            
            return inseree;
            }else {
                System.out.println("Defis already exist: " + id );
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
    @PutMapping("/{defisId}")
    public Defis update(@PathVariable(value="defisId") String id, @RequestBody Defis d, HttpServletResponse response) {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement(); 
            
            //une erreur 412 si l'identifiant du defis dans l'URL n'est pas le même que celui du defis dans le corp de la requête.
            if( !(id.equals(d.getId())) ) {
                System.out.println("Request Body not equivanlent to variable path : " + id + "!=" + d.getId());
                response.setStatus(412);
                return null;
            }
            ResultSet rs = stmt.executeQuery("SELECT * FROM defis where defisid = '" + id + "'");

            if(( rs.next() ) ) {
               PreparedStatement p = connection.prepareStatement("UPDATE defis SET defisid = ?, titre= ?, nomType= ?, dateModification= now(),"+
                                                "auteur = ?, codeArret=?, points=?, duree=?, prologue=?, epilogue=?, commentaire=? WHERE defisid = '"+id+"'");

                p.setString(1, d.getId());
                p.setString(2, d.getTitre() );
                p.setString(3, d.getNomType() );
                p.setString(4, d.getAuteur() );
                p.setString(5, d.getCodeArret() );
                p.setInt(6, d.getPoints() );
                p.setDouble(7, d.getDuree() );
                p.setString(8, d.getPrologue() );
                p.setString(9, d.getEpilogue() );
                p.setString(10, d.getCommentaire() );
                p.executeUpdate();
                return d;
            }else {
                System.out.println("Defis does not exist : " + id );
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
    @DeleteMapping("/{defisId}")
    public void delete(@PathVariable(value="defisId") String id, HttpServletResponse response) {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement(); 
            if(!(read(id,response)== null)) {
                int rs = stmt.executeUpdate("DELETE FROM defis WHERE defisid = '"+id+"'");

            //une erreur 404 si l'identifiant de defis n'existe pas      
            }else {
                System.out.println("Defis does not exist : " + id );
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
