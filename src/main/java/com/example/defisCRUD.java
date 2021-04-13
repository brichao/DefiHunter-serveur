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
public class defisCRUD {
    
    //@Autowired permet au Framework Spring de résoudre et injecter le Bean qui gère la connexion à la base de donnée
    @Autowired
    private DataSource dataSource;

    //GET READ ALL
    @GetMapping("/")
    public ArrayList<defis> alldefis(HttpServletResponse response) {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement(); 
            ResultSet rs = stmt.executeQuery("SELECT * FROM defis");
            
            ArrayList<defis> L = new ArrayList<defis>();
            while (rs.next()) { 
                defis d = new defis();
                d.id = rs.getString("id");
                d.titre   = rs.getString("titre");
                d.datedecreation = rs.getTimestamp("datedecreation");
                d.description = rs.getString("description");
                d.auteur = rs.getString("auteur");
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
    public defis read(@PathVariable(value="defisId") String id, HttpServletResponse response) {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement(); 
            ResultSet rs = stmt.executeQuery("SELECT * FROM defis where id = '" + id + "'");
            
            defis d = new defis();
            while (rs.next()) { 
                d.id = rs.getString("id");
                d.titre   = rs.getString("titre");
                d.datedecreation = rs.getTimestamp("datedecreation");
                d.description = rs.getString("description");
                d.auteur = rs.getString("auteur");
            } 
            return d;


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
    public defis create(@PathVariable(value="defisId") String id, @RequestBody defis u, HttpServletResponse response){
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement(); 
            int rs = stmt.executeUpdate("INSERT INTO defis(id, titre, datedecreation, description, auteur)" 
                                        + "values ('"+ u.id + "', '" + u.titre + 
                                            "', now(), '" + u.description + "', '" + u.auteur + "')");
            defis inseree = this.read(id, response);
            
            return inseree;
        } catch (Exception e) {
            //Identifiant existe déjà
            /*if(){
                response.setStatus(403); 
            } else if(!id.equals(u.login)) { //Identifiant dans l'url n'est pas le même dans le corps de la requête
                response.setStatus(412);
            }*/
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
    public defis update(@PathVariable(value="defisId") String id, @RequestBody defis u, HttpServletResponse response) {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement(); 
            int rs = stmt.executeUpdate("UPDATE defis set id='" + u.id + "', titre='" + u.titre + "', datedecreation='" + u.datedecreation + 
            "', description='" + u.description + "', auteur='" + u.auteur + "' WHERE id = '" + id + "'");

            return u;
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
            int rs = stmt.executeUpdate("DELETE FROM defis WHERE id = '"+id+"'");
            if(rs == 0){
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
    

