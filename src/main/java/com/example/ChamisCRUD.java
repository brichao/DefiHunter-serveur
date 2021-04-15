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
@RequestMapping("/api/chamis")
public class ChamisCRUD {
    
    //@Autowired permet au Framework Spring de résoudre et injecter le Bean qui gère la connexion à la base de donnée
    @Autowired
    private DataSource dataSource;

    //GET READ ALL
    @GetMapping("/")
    public ArrayList<Chamis> allChamis(HttpServletResponse response) {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement(); 
            ResultSet rs = stmt.executeQuery("SELECT * FROM chamis");
            
            ArrayList<Chamis> L = new ArrayList<Chamis>();
            while (rs.next()) { 
                Chamis u = new Chamis();
                u.setPseudo(rs.getString("pseudo"));
                u.setAge(rs.getInt("age"));
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


    //GET READ 
    @GetMapping("/{chamisId}")
    public Chamis read(@PathVariable(value="chamisId") String id, HttpServletResponse response) {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement(); 
            ResultSet rs = stmt.executeQuery("SELECT * FROM chamis where pseudo = '" + id + "'");
            
            Chamis u = new Chamis();
            while (rs.next()) { 
                u.setPseudo(rs.getString("pseudo"));
                u.setAge(rs.getInt("age"));
            } 
            
            if(u.getPseudo() == null) {
                System.out.println("Chamis does not exist : " + id );
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


    //CREATE -- POST /api/chamis/{chamisID}
    @PostMapping("/{chamisId}")
    public Chamis create(@PathVariable(value="chamisId") String id, @RequestBody Chamis u, HttpServletResponse response){
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement(); 
            
            //une erreur 412 si l'identifiant du User dans l'URL n'est pas le même que celui du User dans le corp de la requête.
            if( !(id.equals(u.getPseudo())) ) {
                response.setStatus(412);
                return null;
            }
             //une erreur 403 si un chamis existe déjà avec le même identifiant
            if(read(id,response) == null) {
                int rs = stmt.executeUpdate("INSERT INTO chamis(pseudo, age) values ('"+ u.getPseudo() + "', " + u.getAge() + ")");
                Chamis inseree = this.read(id, response);
                return inseree;
            }else {
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

    
    //PUT UPDATE 
    @PutMapping("/{chamisId}")
    public Chamis update(@PathVariable(value="chamisId") String id, @RequestBody Chamis u, HttpServletResponse response) {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement(); 

            //une erreur 412 si l'identifiant du User dans l'URL n'est pas le même que celui du User dans le corp de la requête.
            if( !(id.equals(u.getPseudo())) ) {
                response.setStatus(412);
                return null;
            }
            // Une erreur 404 si l'identifiant de l'utilisateur ne correspond pas à un utilisateur dans la base. 
            if(read(id,response) != null) {
                int rs = stmt.executeUpdate("UPDATE chamis SET pseudo ='"+u.getPseudo()+"', age="+u.getAge()+" WHERE login = '"+id+"'");
                return u;
            } else {
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
       


    //DELETE
    @DeleteMapping("/{chamisId}")
    public void delete(@PathVariable(value="chamisId") String id, HttpServletResponse response) {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement(); 
            int rs = stmt.executeUpdate("DELETE FROM chamis WHERE pseudo = '"+id+"'");
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
    

