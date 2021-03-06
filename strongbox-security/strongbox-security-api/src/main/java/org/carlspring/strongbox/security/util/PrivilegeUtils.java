package org.carlspring.strongbox.security.util;

import org.carlspring.strongbox.security.Privilege;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author mtodorov
 */
public class PrivilegeUtils
{

    private PrivilegeUtils() 
    {
    }

    public static List<String> toStringList(Collection<Privilege> privileges)
    {
        List<String> privilegesAsStrings = new ArrayList<>();

        for (Privilege privilege : privileges)
        {
            privilegesAsStrings.add(privilege.getName());
        }

        return privilegesAsStrings;
    }

    public static List<Privilege> toList(Collection<Privilege> privileges)
    {
        List<Privilege> privilegesList = new ArrayList<>();
        privilegesList.addAll(privileges);
        
        return privilegesList;
    }
    
}
