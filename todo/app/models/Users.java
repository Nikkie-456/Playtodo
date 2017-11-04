package models;


import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Administrator on 10/31/2017.
 */

@Entity
@Table(name="tbl_users")

public class Users extends Model{

    @Id
    public Long id;

    //@Column(unique = true,nullable = false)
    public String username;

    public String name;

    public String email;

    public String password;

    public static Finder<Long,Users> find=new Finder<Long,Users>(Long.class,Users.class);

    public static Users findUserByUsername(String username){
        return find.where().eq("username",username).findUnique();
    }

}
