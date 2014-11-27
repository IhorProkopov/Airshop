package com.epam.prokopov.shop.service;


import com.epam.prokopov.shop.model.User;
import com.epam.prokopov.shop.model.UserRoles;

import java.util.List;
import java.util.Map;

/**
 * Check if user has right of access to specified page.
 */
public class SecurityManager {

    private Map<String, List<UserRoles>> securityMap;

    /**
     * Parses security file by specified path.
     * @param path Path to security file.
     */
    public SecurityManager(String path) {
        DOMParser domParser = new DOMParser(path);
        this.securityMap = domParser.parse();
    }

    /**
     * Checks if page has restrictions at all.
     * @param urlPattern Page's url.
     * @return Return true if page has restrictions or false otherwise.
     */
    public boolean hasRestrictions(String urlPattern){
        return securityMap.containsKey(urlPattern);
    }

    /**
     * Check if user has right access to specified page.
     * @param urlPattern Page's url.
     * @param user User, who tries to open page
     * @return Return true if user has right access or page hasn't restrictions or false if user hasn't right access.
     */
    public boolean checkAcess(String urlPattern, User user) {
        return !securityMap.containsKey(urlPattern) || securityMap.get(urlPattern).contains(user.getRole());
    }

}
