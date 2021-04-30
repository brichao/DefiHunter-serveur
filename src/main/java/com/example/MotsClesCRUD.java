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
public class MotsClesCRUD {
    //@Autowired permet au Framework Spring de résoudre et injecter le Bean qui gère la connexion à la base de donnée
    @Autowired
    private DataSource dataSource;

    
    //READ ALL -- GET
    @GetMapping("/motscles")
    public ArrayList<MotsCles> allMotCles(HttpServletResponse response) {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement(); 
            ResultSet rs = stmt.executeQuery("SELECT * FROM motsles");
            
            ArrayList<MotsCles> L = new ArrayList<MotsCles>();
            while (rs.next()) { 
                MotsCles u = new MotsCles();
                u.setDefisId(rs.getString("defisid"));
                u.setMotCle(rs.getString("motcle"));
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
    @GetMapping("/{defisId}/motscles")
    public MotsCles read(@PathVariable(value="defisId") String id, HttpServletResponse response) {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement(); 
            ResultSet rs = stmt.executeQuery("SELECT * FROM motscles where defisId = '" + id + "'");
            
            MotsCles u = new MotsCles();
            while (rs.next()) {
                u.setDefisId(rs.getString("defisid"));
                u.setMotCle(rs.getString("motcle"));
            }

            // Une erreur 404 si l'indice ne correspond pas à un defis dans la base.
            if(u.getDefisId() == null) {
                System.out.println("defisId does not exist : " + id );
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


    //CREATE -- POST : /api/defis/{defisId}
    @PostMapping("/{defisId}/motscles/{motCle}")
    public MotsCles create(@PathVariable(value="defisId") String id, @PathVariable(value="motCle") String motcle, @RequestBody MotsCles m, HttpServletResponse response){
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement(); 
            
            //une erreur 412 si l'identifiant de defis  dans l'URL n'est pas le même que celui du l'indice dans le corp de la requête.
            if( !(motcle.equals(m.getMotCle())) ) {
                System.out.println("Request Body not equivanlent to variable path : " + motcle + "!=" + m.getMotCle());
                response.setStatus(412);
                return null;
            }

            ResultSet rs = stmt.executeQuery("SELECT motcle FROM motscles WHERE motcle = '" + motcle +"'");
             //une erreur 403 si un cexiste déjà avec le même identifiant
            if(rs.next()) {
                stmt.executeUpdate("INSERT INTO indices values ( '" + m.getDefisId() + "', '" + m.getMotCle() + "' )");
                MotsCles inseree = this.read(id, response);
                return inseree;
            }else {
                System.out.println("motcle already exist: " + id );
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

    
    //UPDATE -- PUT : /api/defis/{defisId}/motscles/{motCle}
    @PutMapping("/{defisId}/motscles/{motCle}")
    public MotsCles update(@PathVariable(value="defisId") String id, @PathVariable(value="motcle") String motcle, @RequestBody MotsCles m, HttpServletResponse response) {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement(); 
           
            // Une erreur 404 si l'identifiant de defis ne correspond pas à  celui d'un indice dans la base.
            if(m.getMotCle() == null) {
                System.out.println("MotCles does not exist : " + motcle );
                response.setStatus(404);
                return null;

            //une erreur 412 si l'identifiant du User dans l'URL n'est pas le même que celui du User dans le corp de la requête.
            }else if( !(motcle.equals(m.getMotCle())) && !(id.equals(m.getDefisId())) ) {
                System.out.println("Request Body not equivanlent to variable path : " + id + "!=" + m.getDefisId() + motcle +"!=" + m.getMotCle() );
                response.setStatus(412);
                return null;

            }else{
                int rs = stmt.executeUpdate("UPDATE MotCles SET  defisId ='"+m.getDefisId()+"', motCle='"+m.getMotCle()+"' WHERE idDefis = '"+id+"'");
                MotsCles inseree = this.read(id, response);
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
    @DeleteMapping("/{defisId}/motscles/{motCle}")
    public void delete(@PathVariable(value="defisId") String id, @PathVariable(value="motCle") String motCle, HttpServletResponse response) {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement(); 
            int rs = stmt.executeUpdate("DELETE FROM motscles WHERE defisid = '"+id+"' AND motcle = '" + motCle +"'");

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
    



