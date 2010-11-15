package org.adaptiveplatform.codegenerator.sampleclasses;

import java.util.ArrayList;
import java.util.List;

import org.adaptiveplatform.codegenerator.api.RemoteObject;


@RemoteObject
public class SampleDto {
    private static final long serialVersionUID = -4226054307901887002L;

    private Long id;
    private String name;
    private String email;
    private List<String> roles = new ArrayList<String>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserDto [id=" + id + ", name=" + name + ", email=" + email + ", roles=" + roles + "]";
    }
}
