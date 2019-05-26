package com.disqo.notes.asserts;

import com.disqo.notes.entities.Note;
import com.disqo.notes.entities.NoteUser;
import org.assertj.core.api.AbstractAssert;

public class NoteAssert extends AbstractAssert<NoteAssert, Note> {
    NoteAssert(Note note) {
        super(note, NoteAssert.class);
    }

    public static NoteAssert assertThat(Note actual) {
        return new NoteAssert(actual);
    }

    public NoteAssert hasCreatedDate() {
        isNotNull();
        if(actual.getCreatedOn() == null) {
            failWithMessage("Expected note to have a created on date, but it was null.");
        }
        return this;
    }

    public NoteAssert hasLastUpdatedDate() {
        isNotNull();
        if(actual.getLastUpdatedOn() == null) {
            failWithMessage("Expected note to have a last updated on date, but it was null.");
        }
        return this;
    }

    public NoteAssert createdDateEqualsLastUpdatedDate() {
        isNotNull();
        if(!actual.getCreatedOn().equals(actual.getLastUpdatedOn())) {
            failWithMessage("Expected note's created on date and last updated on date to be the same, but they were different.");
        }
        return this;
    }

    public NoteAssert createdDateIsDifferentFromLastUpdatedDate() {
        isNotNull();
        if(actual.getCreatedOn().equals(actual.getLastUpdatedOn())) {
            failWithMessage("Expected note's created on date and last updated on date to be different, they were the same.");
        }
        return this;
    }

    public NoteAssert titleEquals(String title) {
        isNotNull();
        if(!actual.getTitle().equals(title)) {
            failWithMessage("Expected title to equal '"+title+"' but it was '"+actual.getTitle()+"'.");
        }
        return this;
    }

    public NoteAssert noteFieldEquals(String note) {
        isNotNull();
        if(!actual.getNote().equals(note)) {
            failWithMessage("Expected note to equal '"+note+"' but it was '"+actual.getNote()+"'.");
        }
        return this;
    }

    public NoteAssert hasUser() {
        isNotNull();
        if(actual.getNoteUser() == null) {
            failWithMessage("Expected note to have user, but it was null.");
        }
        return this;
    }

    public NoteAssert userEquals(NoteUser noteUser) {
        isNotNull();
        if(!actual.getNoteUser().equals(noteUser)) {
            failWithMessage("Expected note to have same user, but it was different.");
        }
        return this;
    }
}
