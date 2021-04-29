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
@RequestMapping("/api/indices")
public class IndicesCRUD {
    //@Autowired permet au Framework Spring de résoudre et injecter le Bean qui gère la connexion à la base de donnée
    @Autowired
    private DataSource dataSource;

    
    //READ ALL -- GET
    @GetMapping("/")
    public ArrayList<Chamis> allChamis(HttpServletResponse response) {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement(); 
            ResultSet rs = stmt.executeQuery("SELECT * FROM Indices");
            
            ArrayList<Indices> L = new ArrayList<Indices>();
            while (rs.next()) { 
                Indices u = new Indices();
                u.setLebali(rs.getString("lebali"));
                u.setIdDefis(rs.getString("iddefis"));
                u.setDescription(rs.getString("description"));
                u.setPoints(rs.getInt("points"));
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
    @GetMapping("/{indicesId}")
    public Indices read(@PathVariable(value="indicesId") String id, HttpServletResponse response) {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement(); 
            ResultSet rs = stmt.executeQuery("SELECT * FROM lesindices where lebeli = '" + id + "'");
            
            Indices u = new Indices();
            while (rs.next()) {
                u.setLabeli(rs.getString("lebeli"));
                u.setIdDefis(rs.getString("idDefis"));
                u.setDescription(rs.getInt("description"));
                u.setPoints(rs.getString("points"));
                
            }

            // Une erreur 404 si l'indice ne correspond pas à un defis dans la base.
            if(u.getIdDefis() == null) {
                System.out.println("Indice does not exist : " + id );
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


    //CREATE -- POST : /api/chamis/{chamisID}
    @PostMapping("/{indicesId}")
    public Indices create(@PathVariable(value="indicesId") String id, @RequestBody Indices u, HttpServletResponse response){
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement(); 
            
            //une erreur 412 si l'identifiant de defis  dans l'URL n'est pas le même que celui du l'indice dans le corp de la requête.
            if( !(id.equals(u.getIdDefis())) ) {
                System.out.println("Request Body not equivanlent to variable path : " + id + "!=" + u.getIdDefis());
                response.setStatus(412);
                return null;
            }
             //une erreur 403 si un cexiste déjà avec le même identifiant
            if(read(id,response) == null) {
                int rs = stmt.executeUpdate("INSERT INTO Indices values ('"+ u.getIdDefis() + "', '"+ u.getLabeli() + "'," + u.getDescription() + ", '" + u.getPoints() + "')");
                Indices inseree = this.read(id, response);
                return inseree;
            }else {
                System.out.println("indices already exist: " + id );
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

    
    //UPDATE -- PUT : /api/chamis/{chamisID}
    @PutMapping("/{indicesId}")
    public Indices update(@PathVariable(value="indicesId") String id, @RequestBody Indices u, HttpServletResponse response) {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement(); 
           
            // Une erreur 404 si l'identifiant de defis ne correspond pas à  celui d'un indice dans la base.
            if(u.getIdDefis() == null) {
                System.out.println("Indices does not exist : " + id );
                response.setStatus(404);
                return null;

            //une erreur 412 si l'identifiant du User dans l'URL n'est pas le même que celui du User dans le corp de la requête.
            }else if( !(id.equals(u.getIdDefis())) ) {
                System.out.println("Request Body not equivanlent to variable path : " + id + "!=" + u.getIdDefis());
                response.setStatus(412);
                return null;

            }else{
                int rs = stmt.executeUpdate("UPDATE Indices SET idDefis ='"+u.getIdDefis()+"',labeli='"+u.getLabeli()+"', description='"+u.getDescription()+"',points='"+u.getPoints()+"' WHERE idDefis = '"+id+"'");
                Indices inseree = this.read(id, response);
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
    @DeleteMapping("/{indicesId}")
    public void delete(@PathVariable(value="indicesId") String id, HttpServletResponse response) {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement(); 
            int rs = stmt.executeUpdate("DELETE FROM lesindices WHERE idDefis = '"+id+"'");

            // Une erreur 404 si l'identifiant de defis  ne correspond pas à un defis dans la base.
            if(rs == 0){
                System.out.println("indice does not exist : " + id );
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
    



