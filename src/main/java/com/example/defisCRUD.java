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
@RequestMapping("/api/defis")
public class DefisCRUD {
    
    //@Autowired permet au Framework Spring de résoudre et injecter le Bean qui gère la connexion à la base de donnée
    @Autowired
    private DataSource dataSource;

    //GET READ ALL
    @GetMapping("/")
    public ArrayList<Defis> alldefis(HttpServletResponse response) {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement(); 
            ResultSet rs = stmt.executeQuery("SELECT * FROM Defis");
            
            ArrayList<Defis> L = new ArrayList<Defis>();
            while (rs.next()) { 
                Defis d = new Defis();
                d.setId(rs.getString("id"));
                d.setTitre(rs.getString("titre"));
                d.setDatedecreation(rs.getTimestamp("datedecreation"));
                d.setDescription(rs.getString("description"));
                d.setAuteur(rs.getString("auteur"));
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

    //GET READ 
    @GetMapping("/{defisId}")
    public Defis read(@PathVariable(value="defisId") String id, HttpServletResponse response) {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement(); 
            ResultSet rs = stmt.executeQuery("SELECT * FROM defis where id = '" + id + "'");
            
            Defis d = new Defis();
            while (rs.next()) { 
<<<<<<< HEAD
                d.id = rs.getString("id");
                d.titre   = rs.getString("titre");
                d.datedecreation = rs.getTimestamp("datedecreation");
                d.description = rs.getString("description");
                d.auteur = rs.getString("auteur");
            }
            //is non existant error 404
            if(d.getId() == null) {
                System.out.println("Defis does not exist : " + id );
                response.setStatus(404);
                return null;
            } else {
                return d; 
            }
=======
                d.setId(rs.getString("id"));
                d.setTitre(rs.getString("titre"));
                d.setDatedecreation(rs.getTimestamp("datedecreation"));
                d.setDescription(rs.getString("description"));
                d.setAuteur(rs.getString("auteur"));
            } 
            return d;

>>>>>>> dd9faff0d14a07afce06dcd82c4fc94f66bb0af6

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
<<<<<<< HEAD
            
            //une erreur 412 si l'identifiant du defis dans l'URL n'est pas le même que celui du defis dans le corp de la requête.
            if( !(id.equals(d.getId())) ) {
                response.setStatus(412);
                return null;
            }
             //une erreur 403 si une ressource existe déjà avec le même identifiant
            if(read(id,response) == null) {
               int rs = stmt.executeUpdate("INSERT INTO defis(id, titre, datedecreation, description, auteur)" 
                                        + "values ('"+ d.getId() + "', '" + d.getTitre() + 
                                            "', now(), '" + d.getDescription() + "', '" + d.getAuteur() + "')");
            Defis inseree = this.read(id, response);
            
            return inseree;
            }else {
                response.setStatus(403);
                return null;
            
=======

            //une erreur 412 si l'identifiant du défi dans l'URL n'est pas le même que celui du défi dans le corp de la requête.
            if( !(id.equals(d.getId()) )) {
                response.setStatus(412);
                return null;
            }
             //une erreur 403 si un défi existe déjà avec le même identifiant
            if(read(id,response) == null) {
                int rs = stmt.executeUpdate("INSERT INTO defis(id, titre, datedecreation, description, auteur)" 
                                                + "values ('"+ d.getId() + "', '" + d.getTitre() + 
                                                    "', now(), '" + d.getDescription() + "', '" + d.getAuteur() + "')");
                Defis inseree = this.read(id, response);
                return inseree;
            }else {
                response.setStatus(403);
                return null;
            
>>>>>>> dd9faff0d14a07afce06dcd82c4fc94f66bb0af6
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

    
    //PUT UPDATE 
    @PutMapping("/{defisId}")
    public Defis update(@PathVariable(value="defisId") String id, @RequestBody Defis d, HttpServletResponse response) {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement(); 
<<<<<<< HEAD
            
             //une erreur 412 si l'identifiant du defis dans l'URL n'est pas le même que celui du defis dans le corp de la requête.
            if( !(id.equals(d.id)) ) {
                response.setStatus(412);
                return null;
            }
           //une erreur 404 si l'identifiant de defis  ne correspond pas à un defis dans la base           
            if(!(read(id,response)== null)) {
                 int rs = stmt.executeUpdate("UPDATE defis set id='" + d.getId() + "', titre='" + d.getTitre() + "', datedecreation='" + d.getDatedecreation() + 
            "', description='" + d.getDescription() + "', auteur='" + d.getAuteur() + "' WHERE id = '" + id + "'");
            return d;
            
            }else {
                response.setStatus(404);
                return null;
            
            }
=======

            //une erreur 412 si l'identifiant du User dans l'URL n'est pas le même que celui du User dans le corp de la requête.
            if( !(id.equals(d.getId())) ) {
                response.setStatus(412);
                return null;
            }
            // Une erreur 404 si l'identifiant de l'utilisateur ne correspond pas à un utilisateur dans la base. 
            if(read(id,response) != null) {
                int rs = stmt.executeUpdate("UPDATE defis set id='" + d.getId() + "', titre='" + d.getTitre() + "', datedecreation='" + d.getDatedecreation() + 
            "', description='" + d.getDescription() + "', auteur='" + d.getAuteur() + "' WHERE id = '" + id + "'");
                return d;
            } else {
                response.setStatus(404);
                return null;
            }

>>>>>>> dd9faff0d14a07afce06dcd82c4fc94f66bb0af6
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

    //DELETE
    @DeleteMapping("/{defisId}")
    public void delete(@PathVariable(value="defisId") String id, HttpServletResponse response) {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement(); 
            
           //une erreur 404 si l'identifiant de defis  ne correspond pas à un defis dans la base           
            if(!(read(id,response)== null)) {
                int rs = stmt.executeUpdate("DELETE FROM defis WHERE id = '"+id+"'");
            
            }else {
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