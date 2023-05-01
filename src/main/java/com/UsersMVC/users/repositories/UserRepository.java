package com.UsersMVC.users.repositories;


import com.UsersMVC.users.models.User;

import java.util.List;

public interface UserRepository  {
     List<User> index() ;

     User show(int id);

    void save(User person);

     void update(int id, User person);
     void delete(int id);


    public User findByUsername(String name) ;

}
