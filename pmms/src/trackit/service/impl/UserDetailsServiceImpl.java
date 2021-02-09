/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.service.impl;

import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import trackit.businessobjects.User;
import trackit.dao.UserDao;
import trackit.util.FacesUtils;
import trackit.util.UserPermissions;

public class UserDetailsServiceImpl implements UserDetailsService {

    private UserDao userDao;

    public UserDetailsServiceImpl() {
        
    }

    public void setUserDao(UserDao dao) {
        userDao = dao;
    }

    public UserDao getUserDao() {
        return this.userDao;
    }

    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException, DataAccessException {
        List users = userDao.findUserByEmail(username);
        if (users == null || users.size() == 0)
            throw new UsernameNotFoundException("User not found: " + username);
        else {
            User user = (User) users.get(0);
            
//            FacesUtils.getSession().setAttribute("UserId", user.getId());
//            FacesUtils.getSession().setAttribute("AccountId", user.getAccountId());

            FacesUtils.getSessionBean().setCurrentUserId(
                        String.valueOf(user.getId()));

            FacesUtils.getSessionBean().setCurrentAccountId(
                        String.valueOf(user.getAccountId()));
            return makeUser(user);
        }
    }

    private org.springframework.security.core.userdetails.User makeUser(User user) {
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user
                .getPassword(), true, true, true, true,
                makeGrantedAuthorities(user));
    }

    private GrantedAuthority[] makeGrantedAuthorities(User user) {
        //GrantedAuthority[] result = new GrantedAuthority[user.getRoles().size()];

        List<String> permissions = UserPermissions.parseDBValue(user.getPermissions());
        GrantedAuthority[] result = null;

        if (permissions.contains("ROLE_SUPERVISOR")) {
            List<String> allPermissions = UserPermissions.getAllPermissions();
            result = new GrantedAuthority[allPermissions.size()];
            for (int i=0; i<allPermissions.size(); i++) {
                result[i] = new GrantedAuthorityImpl(allPermissions.get(i));
            }
        } else {
            result = new GrantedAuthority[permissions.size()];
            for (int i=0; i<result.length; i++) {
                result[i] = new GrantedAuthorityImpl(permissions.get(i));
            }
        }
        return result;
    }



}

