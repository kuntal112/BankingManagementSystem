package com.banking.entities;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

@Table(name="user",uniqueConstraints = {
		  @UniqueConstraint(name = "U_email", columnNames = { "email" } ),
		  @UniqueConstraint(name = "U_username", columnNames =  {"username"}),
		  @UniqueConstraint(name = "U_contactNum", columnNames =  {"contactNo"}),
		  @UniqueConstraint(name = "U_pan", columnNames =  {"pan"})
})

@Builder
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank(message = "Name should not be null")
	private String name;
	@NotBlank(message = "Username should not be null")
	private String username;
	@NotBlank(message = "Passsword should not be null")
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	@NotBlank(message = "Email should not be null")
	private String email;
	private String address;
	private String state;
	private String country;
	@NotBlank(message = "PAN is mandatory")
	private String pan;
	@NotBlank(message = "Phone Number is mandatory")
	private String contactNo;
	@Past(message = "DOB should be a past date")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@JsonFormat(pattern="yyyy-MM-dd")
    private Date dob;
	@NotBlank(message = "Select Account type")
	private String accType;

}
