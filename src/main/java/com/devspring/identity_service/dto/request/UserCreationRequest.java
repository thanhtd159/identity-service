package com.devspring.identity_service.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @Size(min = 3, message = "INVALID_USERNAME")
    String username;
    @Size(min = 8, message = "INVALID_PASSWORD")
    String password;

    String firstName;
    String lastName;
    LocalDate dob;

    // public String getUsername() {
    // return username;
    // }

    // public void setUsername(String username) {
    // this.username = username;
    // }

    // public String getFirstName() {
    // return firstName;
    // }

    // public void setFirstName(String firstName) {
    // this.firstName = firstName;
    // }

    // public String getLastName() {
    // return lastName;
    // }

    // public void setLastName(String lastName) {
    // this.lastName = lastName;
    // }

    // public String getPassword() {
    // return password;
    // }

    // public void setPassword(String password) {
    // this.password = password;
    // }

    // public LocalDate getDob() {
    // return dob;
    // }

    // public void setDob(LocalDate dob) {
    // this.dob = dob;
    // }

}
