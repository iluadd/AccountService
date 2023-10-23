package account.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "name", "lastname", "email" })
public class AccountId extends Account{

    private Long id;

    public AccountId(String name, String lastname, String email) {
        super(name, lastname, email);
        setId((long) getEmail().toLowerCase().hashCode());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
