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
public class IndicesCRUD {
    //@Autowired permet au Framework Spring de résoudre et injecter le Bean qui gère la connexion à la base de donnée
    @Autowired
    private DataSource dataSource;

    
    //READ ALL -- GET
    @GetMapping("/indices")
    public ArrayList<Indices> allIndices(HttpServletResponse response) {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement(); 
            ResultSet rs = stmt.executeQuery("SELECT * FROM indices");
            
            ArrayList<Indices> L = new ArrayList<Indices>();
            while (rs.next()) { 
                Indices i = new Indices();
                i.setIndicesId(rs.getInt("indicesid"));
                i.setDefisId(rs.getString("defisid"));
                i.setIndiceNum(rs.getInt("indicenum"));
                i.setDescription(rs.getString("description"));
                i.setPoints(rs.getInt("points"));
                L.add(i);
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
    @GetMapping("/{defisId}/indices/{indicesNum}")
    public Indices read(@PathVariable(value="defisId") String id, @PathVariable(value="indicesNum") int indiceNum, HttpServletResponse response) {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement(); 
            ResultSet rs = stmt.executeQuery("SELECT * FROM indices where defisId = '" + id + "' and indiceNum = "+indiceNum );
            
            Indices i = new Indices();
            while (rs.next()) {
                i.setIndicesId(rs.getInt("indicesid"));
                i.setDefisId(rs.getString("defisid"));
                i.setIndiceNum(rs.getInt("indicenum"));
                i.setDescription(rs.getString("description"));
                i.setPoints(rs.getInt("points"));
                
            }

            // Une erreur 404 si l'indice ne correspond pas à un defis dans la base.
            if(i.getIndiceNum() == 0) {
                System.out.println("indice does not exist : " + indiceNum );
                response.setStatus(404);
                return null;
            } else {
                return i; 
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


    //CREATE -- POST : /api/{defisId}/indices/{indiceNum}
    @PostMapping("/{defisId}/indices/{indiceNum}")
    public Indices create(@PathVariable(value="defisId") String id, @PathVariable(value="indiceNum") int indiceNum, @RequestBody Indices i, HttpServletResponse response){
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement(); 
            
            //une erreur 412 si l'identifiant de defis  dans l'URL n'est pas le même que celui du l'indice dans le corp de la requête.
            if( indiceNum != i.getIndiceNum() ) {
                System.out.println("Request Body not equivanlent to variable path : " + id + "!=" + i.getDefisId());
                response.setStatus(412);
                return null;
            }
             //une erreur 403 si un existe déjà avec le même identifiant
            if(read(id, indiceNum, response) == null) {
                PreparedStatement p = connection.prepareStatement("INSERT INTO indices (defisid, indicenum, description, points) values (?,?,?,?)");
                p.setString(1, i.getDefisId());
                p.setInt(2, i.getIndiceNum() );
                p.setString(3, i.getDescription() );
                p.setInt(4, i.getIndicesId() );
                p.executeUpdate();

                Indices inseree = this.read(id,indiceNum, response);
                return inseree;
            }else {
                System.out.println("indices already exist for " + id + "indice n."+indiceNum);
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

    
    //UPDATE -- PUT : /api/defis/{defisId}/indices/{indiceNum}
    @PutMapping("/{defisId}/indices/{indiceNum}")
    public Indices update(@PathVariable(value="defisId") String id, @PathVariable(value="indiceNum") int indiceNum, @RequestBody Indices i, HttpServletResponse response) {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement(); 
           
            // Une erreur 404 si l'identifiant de defis ne correspond pas à  celui d'un indice dans la base.
            if(i.getDefisId() == null) {
                System.out.println("Indices does not exist : " + id );
                response.setStatus(404);
                return null;

            //une erreur 412 si l'identifiant du User dans l'URL n'est pas le même que celui du User dans le corp de la requête.
            }else if( !(id.equals(i.getDefisId())) ) {
                System.out.println("Request Body not equivanlent to variable path : " + id + "!=" + i.getDefisId());
                response.setStatus(412);
                return null;

            }else{
                PreparedStatement p = connection.prepareStatement("UPDATE Indices SET defisid =?, description =?,points=? WHERE defisid = '"+id+"' and indicenum = "+indiceNum);
                p.setString(1, i.getDefisId());
                p.setInt(2, i.getIndiceNum() );
                p.setString(3, i.getDescription() );
                p.setInt(4, i.getIndicesId() );
                p.executeUpdate();
                Indices inseree = this.read(id, indiceNum, response);
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
    @DeleteMapping("/{defisId}/indices/{indiceNum}")
    public void delete(@PathVariable(value="defisId") String id, @PathVariable(value="indiceNum") int indiceNum, HttpServletResponse response) {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement(); 
            int rs = stmt.executeUpdate("DELETE FROM indices WHERE defisid = '"+id+"' AND indicenum ="+ indiceNum);

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
    



