package evoting.dao;

import evoting.dto.UserDetails;
import evoting.utills.DBConnection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistrationDao {
    
    private static PreparedStatement ps, ps1;
    static{
        
        try{
            ps = DBConnection.getConnection().prepareStatement("select * from user_details where adhar_no = ?");
            
//            change
            ps1 = DBConnection.getConnection().prepareStatement("insert into user_details values(?, ?, ?, ?, ?, ?, ?, ?, ?)");
        }
        
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    public static boolean searchUser(String userId) throws SQLException {
        ps.setString(1, userId);

//        java hume recommend karti hai ki jab koi object only ek he method ka use kare to us object ko hume as an
//        anonymos object ki tarah use karna chahiye
        return ps.executeQuery().next();
    }
    
    public static boolean registerUser(UserDetails user)throws SQLException{
        ps1.setString(1, user.getUserId());
        ps1.setString(2, user.getPassword());
        ps1.setString(3, user.getUserName());
        ps1.setString(4, user.getAddress());
        ps1.setString(5, user.getCity());
        ps1.setString(6, user.getEmailId());
        ps1.setLong(7, user.getMobile());
        ps1.setString(8, "voter");
        ps1.setString(9, user.getGender());
        return ps1.executeUpdate() != 0;
    }
}
