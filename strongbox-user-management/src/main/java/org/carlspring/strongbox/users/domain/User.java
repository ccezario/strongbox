package org.carlspring.strongbox.users.domain;

import org.carlspring.strongbox.data.domain.GenericEntity;

import java.util.HashSet;
import java.util.Set;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.apache.commons.lang.StringUtils;

/**
 * An application user
 */
public class User
        extends GenericEntity
{

    private String username;

    private String password;

    private boolean enabled;

    private String salt;

    private Set<String> roles;
    
    private String securityTokenKey;

    public User()
    {
        roles = new HashSet<>();
    }

    public User(String id,
                String username,
                String password,
                boolean enabled,
                String salt,
                Set<String> roles)
    {
        this.databaseId = id;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.salt = salt;
        this.roles = roles;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(final String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(final String password)
    {
        this.password = password;
    }

    public boolean isEnabled()
    {
        return enabled;
    }

    public void setEnabled(final boolean enabled)
    {
        this.enabled = enabled;
    }

    public String getSalt()
    {
        return salt;
    }

    public void setSalt(final String salt)
    {
        this.salt = salt;
    }

    public Set<String> getRoles()
    {
        return roles;
    }

    public void setRoles(final Set<String> roles)
    {
        this.roles = roles;
    }

    public String getSecurityTokenKey()
    {
        return securityTokenKey;
    }

    public void setSecurityTokenKey(String securityTokenKey)
    {
        this.securityTokenKey = securityTokenKey;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        User user = (User) o;

        return enabled == user.enabled &&
               Objects.equal(databaseId, user.databaseId) &&
               Objects.equal(username, user.username) &&
               Objects.equal(password, user.password) &&
               Objects.equal(salt, user.salt) &&
               Objects.equal(roles, user.roles) &&
               Objects.equal(detachAll, user.detachAll);
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(databaseId, username, password, enabled, salt, roles, detachAll);
    }

    @Override
    public String toString()
    {
        return MoreObjects.toStringHelper(this)
                          .add("databaseId", getDatabaseId())
                          .add("username", getUsername())
                          .add("password", getPassword())
                          .add("enabled", isEnabled())
                          .add("salt", getSalt())
                          .add("roles", getRoles())
                          .add("securityToken", StringUtils.isEmpty(getSecurityTokenKey()) ? "[EMPTY]" : "[SEECRET]")
                          .add("detachAll", detachAll)
                          .add("version", version)
                          .toString();
    }

}
