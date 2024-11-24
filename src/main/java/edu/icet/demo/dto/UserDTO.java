package edu.icet.demo.dto;

import edu.icet.demo.constants.Status;
import edu.icet.demo.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private String id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private Status status;
    private Date joinedDate;
    private Date lastUpdatedDate;
    private Role role;
}
