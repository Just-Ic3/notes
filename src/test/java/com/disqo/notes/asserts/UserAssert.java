package com.disqo.notes.asserts;

import com.disqo.notes.entities.NoteUser;
import org.assertj.core.api.AbstractAssert;

public class UserAssert extends AbstractAssert<UserAssert, NoteUser> {
    UserAssert(NoteUser noteUser) {
        super(noteUser,UserAssert.class);
    }

    public static UserAssert assertThat(NoteUser actual) {
        return new UserAssert(actual);
    }

    public UserAssert hasCreatedOnDate() {
        isNotNull();
        if(actual.getCreatedOn() == null) {
            failWithMessage("Expected user to have a created on date, but it was null.");
        }
        return this;
    }

    public UserAssert hasLastUpdatedOnDate() {
        isNotNull();
        if(actual.getLastUpdatedOn() == null) {
            failWithMessage("Expected user to have a last updated on date, but it was null.");
        }
        return this;
    }

    public UserAssert createdOnDateEqualsLastUpdatedOnDate() {
        isNotNull();
        if(!actual.getCreatedOn().equals(actual.getLastUpdatedOn())) {
            failWithMessage("Expected user's created on and last updated on dates to be the same, but they were different.");
        }
        return this;
    }

    public UserAssert createdOnDateIsDifferentFromLastUpdatedOnDate() {
        isNotNull();
        if(actual.getCreatedOn().equals(actual.getLastUpdatedOn())) {
            failWithMessage("Expected user's created on and last updated on dates to be different, but they were the same.");
        }
        return this;
    }

    public UserAssert emailEquals(String email) {
        isNotNull();
        if(!actual.getEmail().equals(email)) {
            failWithMessage("Expected user's email to be '"+email+"' but it was '"+actual.getEmail()+"'.");
        }
        return this;
    }

    public UserAssert passwordEquals(String password) {
        isNotNull();
        if(!actual.getPassword().equals(password)) {
            failWithMessage("Expected user's email to be '"+password+"' but it was '"+actual.getPassword()+"'.");
        }
        return this;
    }
}
